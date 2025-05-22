import javax.swing.*;
import java.io.*;
import java.util.*;

public class LoginManager {
    private static final String USERS_FILE = "users.txt";

    // Show login/register dialog
    public static String login() {
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
                if (username == null) return null; // Cancel pressed

                JPasswordField pf = new JPasswordField();
                int okCxl = JOptionPane.showConfirmDialog(null, pf, "Enter Password:", JOptionPane.OK_CANCEL_OPTION);
                if (okCxl != JOptionPane.OK_OPTION) return null;

                String password = new String(pf.getPassword());

                if (authenticate(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful. Welcome, " + username + "!");
                    return username;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.");
                }

            } else if (choice == 1) { // Create Account
                String newUser = JOptionPane.showInputDialog("Choose a username:");
                if (newUser == null) return null;

                if (userExists(newUser)) {
                    JOptionPane.showMessageDialog(null, "Username already exists. Try again.");
                    continue;
                }

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

                saveUser(newUser, pass1);
                JOptionPane.showMessageDialog(null, "Account created successfully. Please sign in.");
            } else {
                // Exit or closed dialog
                return null;
            }
        }
    }

    // Check if username exists
    private static boolean userExists(String username) {
        Map<String, String> users = loadUsers();
        return users.containsKey(username);
    }

    // Authenticate username and password
    private static boolean authenticate(String username, String password) {
        Map<String, String> users = loadUsers();
        return users.containsKey(username) && users.get(username).equals(password);
    }

    // Save new user to file
    private static void saveUser(String username, String password) {
        try (FileWriter fw = new FileWriter(USERS_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(username + ":" + password);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving user data: " + e.getMessage());
        }
    }

    // Load users from file into Map<username, password>
    private static Map<String, String> loadUsers() {
        Map<String, String> users = new HashMap<>();
        File file = new File(USERS_FILE);
        if (!file.exists()) return users;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading user data: " + e.getMessage());
        }
        return users;
    }
}
