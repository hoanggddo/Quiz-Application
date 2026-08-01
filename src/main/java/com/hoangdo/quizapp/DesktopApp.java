package com.hoangdo.quizapp;

import com.hoangdo.quizapp.client.ApiClient;
import com.hoangdo.quizapp.client.CategoryManager;
import com.hoangdo.quizapp.client.LoginManager;
import com.hoangdo.quizapp.client.QuizClientApp;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

/**
 * Single-process entry point for end users.
 *
 * Two modes, chosen automatically based on the QUIZ_SERVER_URL
 * environment variable (or -Dquiz.server.url system property):
 *
 *  - Not set (default): starts the Spring Boot API as an embedded server
 *    in this same JVM against a local H2 file, then opens the Swing UI.
 *    Fully self-contained -- good for solo/offline use, but each machine
 *    this runs on gets its own separate accounts and leaderboard.
 *
 *  - Set to a URL (e.g. https://your-app.onrender.com): skips starting a
 *    local server entirely and connects the Swing UI directly to that
 *    shared server instead. This is what gives you a real shared
 *    leaderboard across machines -- everyone points at the same server.
 *
 * QuizApplication.java remains the API-only entry point (no UI at all) --
 * that's what actually gets deployed to a hosting platform as the shared
 * server that this class's remote mode connects to.
 */
public class DesktopApp {

    public static void main(String[] args) {
        // Belt-and-suspenders alongside spring.main.headless=false in
        // application.properties -- set this directly before Spring Boot
        // even starts, since java.awt.headless must be false before any
        // AWT/Swing class is touched.
        System.setProperty("java.awt.headless", "false");

        String remoteUrl = System.getProperty("quiz.server.url", System.getenv("QUIZ_SERVER_URL"));
        boolean useRemoteServer = remoteUrl != null && !remoteUrl.isBlank();

        ConfigurableApplicationContext context = null;
        ApiClient apiClient;

        if (useRemoteServer) {
            // Shared mode: connect to the already-running central server.
            // No local server is started, so this machine's data isn't
            // isolated -- everyone using this mode shares one database.
            apiClient = new ApiClient(remoteUrl);
        } else {
            // Solo mode: starts the embedded Tomcat server + all REST
            // controllers in the background against the local H2 file.
            context = SpringApplication.run(QuizApplication.class, args);
            apiClient = new ApiClient("http://localhost:8080");
        }

        final ConfigurableApplicationContext finalContext = context;

        SwingUtilities.invokeLater(() -> {
            LoginManager loginManager = new LoginManager(apiClient);
            String username = loginManager.login();

            if (username == null) {
                if (finalContext != null) {
                    finalContext.close();
                }
                System.exit(0);
                return;
            }

            String category = CategoryManager.chooseCategory();
            new QuizClientApp(apiClient, username, category);
            // QuizClientApp's window uses EXIT_ON_CLOSE. In solo mode this
            // also shuts down the embedded server since it's the same
            // process; in remote mode it just closes the window.
        });
    }
}
