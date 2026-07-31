package com.hoangdo.quizapp.client;

import javax.swing.*;

/**
 * Adapted from the original LoginManager. The dialog flow is unchanged --
 * the only difference is that authenticate/register now go over HTTP to
 * the backend instead of reading/writing users.txt directly.
 */
public class LoginManager {

    private final ApiClient apiClient;

    public LoginManager(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public String login() {
        while (true) {
            String[] options = {"Sign In", "Create Account", "Exit"};
            int choice = JOptionPane.showOptionDialog(null,
                    "Welcome! Please choose an option:",
                    "Login",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (choice == 0) { // Sign In
                String username = JOptionPane.showInputDialog("Enter username:");
                if (username == null) return null;

                JPasswordField pf = new JPasswordField();
                int okCxl = JOptionPane.showConfirmDialog(null, pf, "Enter Password:", JOptionPane.OK_CANCEL_OPTION);
                if (okCxl != JOptionPane.OK_OPTION) return null;

                String password = new String(pf.getPassword());

                try {
                    String error = apiClient.login(username, password);
                    if (error == null) {
                        JOptionPane.showMessageDialog(null, "Login successful. Welcome, " + username + "!");
                        return username;
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password.");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Could not reach the quiz server. Is it running at localhost:8080?\n" + e.getMessage());
                }

            } else if (choice == 1) { // Create Account
                String newUser = JOptionPane.showInputDialog("Choose a username:");
                if (newUser == null) return null;

                JPasswordField pf1 = new JPasswordField();
                JPasswordField pf2 = new JPasswordField();
                int ok1 = JOptionPane.showConfirmDialog(null, pf1, "Enter password:", JOptionPane.OK_CANCEL_OPTION);
                if (ok1 != JOptionPane.OK_OPTION) continue;
                int ok2 = JOptionPane.showConfirmDialog(null, pf2, "Confirm password:", JOptionPane.OK_CANCEL_OPTION);
                if (ok2 != JOptionPane.OK_OPTION) continue;

                String pass1 = new String(pf1.getPassword());
                String pass2 = new String(pf2.getPassword());

                if (!pass1.equals(pass2)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match. Try again.");
                    continue;
                }

                try {
                    String error = apiClient.register(newUser, pass1);
                    if (error == null) {
                        JOptionPane.showMessageDialog(null, "Account created successfully. Please sign in.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Could not create account: " + error);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Could not reach the quiz server. Is it running at localhost:8080?\n" + e.getMessage());
                }

            } else {
                return null;
            }
        }
    }
}
