package com.hoangdo.quizapp;

import com.hoangdo.quizapp.client.ApiClient;
import com.hoangdo.quizapp.client.CategoryManager;
import com.hoangdo.quizapp.client.LoginManager;
import com.hoangdo.quizapp.client.QuizClientApp;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

/**
 * Single-process entry point for end users: starts the Spring Boot API
 * as an embedded server in this same JVM, then opens the Swing UI once
 * it's ready. A customer only ever runs this one class/jar -- there is
 * no separate server to start, no terminal commands, no second process.
 *
 * QuizApplication.java remains the API-only entry point (used for local
 * development, testing with curl/Postman, and demoing the backend on its
 * own). This class is the consumer-facing combined mode.
 */
public class DesktopApp {

    public static void main(String[] args) {
        // Belt-and-suspenders alongside spring.main.headless=false in
        // application.properties -- set this directly before Spring Boot
        // even starts, since java.awt.headless must be false before any
        // AWT/Swing class is touched.
        System.setProperty("java.awt.headless", "false");

        // Starts the embedded Tomcat server + all REST controllers in the
        // background. This call returns once the server is up; it does
        // not block the rest of main() the way running `mvn spring-boot:run`
        // in a terminal appears to.
        ConfigurableApplicationContext context = SpringApplication.run(QuizApplication.class, args);

        ApiClient apiClient = new ApiClient("http://localhost:8080");

        SwingUtilities.invokeLater(() -> {
            LoginManager loginManager = new LoginManager(apiClient);
            String username = loginManager.login();

            if (username == null) {
                context.close();
                System.exit(0);
                return;
            }

            String category = CategoryManager.chooseCategory();
            new QuizClientApp(apiClient, username, category);
            // QuizClientApp's window uses EXIT_ON_CLOSE, which terminates
            // this entire process (embedded server included) when closed.
        });
    }
}
