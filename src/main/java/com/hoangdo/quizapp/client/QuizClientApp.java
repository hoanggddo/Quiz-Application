package com.hoangdo.quizapp.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Adapted from the original VietnameseQuizApp. Same overall layout and
 * button flow as the original Swing app, with navigation now driven by
 * callbacks instead of System.exit():
 *
 *  - Submit: saves the score, then returns to category selection
 *    (does NOT close the whole app).
 *  - Reset: abandons the current attempt (no score saved) and returns
 *    to category selection.
 *  - Log Out: returns all the way to the login screen, so a different
 *    user (or the same one again) can sign in.
 *
 * DesktopApp owns the actual navigation loop; this class only calls the
 * two Runnable callbacks it's given and otherwise knows nothing about
 * what happens next.
 *
 * Other changes from the original app:
 *  - Questions are fetched from the API (by category) instead of being
 *    hardcoded in loadQuestions().
 *  - Answers are graded server-side via ApiClient.submitAnswer(), since
 *    the client is never given the correct answer up front.
 *  - Text-to-speech and per-question images are removed (see
 *    swing-client history in git log / earlier README notes).
 */
public class QuizClientApp extends JFrame implements ActionListener {

    private final ApiClient apiClient;
    private final Runnable onBackToMenu;
    private final Runnable onLogout;

    JRadioButton[] radiobutton = new JRadioButton[4];
    JLabel questionLabel, title;
    JButton nextBtn, submitBtn, resetBtn, hintBtn, historyBtn, logoutBtn;

    int current = 0;
    int score = 0;

    List<ApiClient.ClientQuestion> questions;
    String username;
    String category;

    public QuizClientApp(ApiClient apiClient, String username, String category,
                          Runnable onBackToMenu, Runnable onLogout) {
        this.apiClient = apiClient;
        this.username = username;
        this.category = category;
        this.onBackToMenu = onBackToMenu;
        this.onLogout = onLogout;

        setTitle("Vietnamese Quiz");
        setSize(1000, 700);
        // Closing via the window's own X button still exits the whole
        // app (this is the only path that does). Submit/Reset/Log Out
        // all use dispose() directly instead, which does NOT trigger
        // this and lets DesktopApp's loop continue.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        title = new JLabel("Vietnamese Quiz - " + category);
        title.setBounds(250, 30, 500, 40);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        add(title);

        questionLabel = new JLabel();
        questionLabel.setBounds(50, 100, 800, 30);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(questionLabel);

        ButtonGroup bg = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            radiobutton[i] = new JRadioButton();
            radiobutton[i].setBounds(100, 150 + (i * 40), 400, 30);
            add(radiobutton[i]);
            bg.add(radiobutton[i]);
        }

        // Row 1: quiz-progress actions
        nextBtn = new JButton("Next");
        nextBtn.setBounds(100, 400, 120, 40);
        nextBtn.addActionListener(this);
        add(nextBtn);

        submitBtn = new JButton("Submit");
        submitBtn.setBounds(250, 400, 120, 40);
        submitBtn.addActionListener(this);
        submitBtn.setVisible(false);
        add(submitBtn);

        resetBtn = new JButton("Reset");
        resetBtn.setBounds(400, 400, 120, 40);
        resetBtn.addActionListener(this);
        add(resetBtn);

        // Row 2: secondary actions
        hintBtn = new JButton("Hint");
        hintBtn.setBounds(100, 460, 120, 40);
        hintBtn.addActionListener(this);
        add(hintBtn);

        historyBtn = new JButton("View Leaderboard");
        historyBtn.setBounds(250, 460, 180, 40);
        historyBtn.addActionListener(this);
        add(historyBtn);

        logoutBtn = new JButton("Log Out");
        logoutBtn.setBounds(450, 460, 120, 40);
        logoutBtn.addActionListener(this);
        add(logoutBtn);

        if (!loadQuestions()) {
            dispose();
            if (onBackToMenu != null) {
                onBackToMenu.run();
            }
            return;
        }
        set();
        setVisible(true);
    }

    /** @return true if questions were loaded successfully */
    boolean loadQuestions() {
        try {
            questions = apiClient.getQuestions(category);
            if (questions.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No questions found for category: " + category);
                return false;
            }
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Could not reach the quiz server. Is it running at localhost:8080?\n" + e.getMessage());
            return false;
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == hintBtn) {
            String hint = questions.get(current).hint();
            if (hint == null || hint.isBlank()) {
                JOptionPane.showMessageDialog(this, "No hint available for this question.");
            } else {
                JOptionPane.showMessageDialog(this, hint, "Hint", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        if (e.getSource() == nextBtn) {
            handleAnswerAndAdvance();
        }

        if (e.getSource() == submitBtn) {
            handleAnswerAndAdvance();
            JOptionPane.showMessageDialog(this, "You scored: " + score + "/" + questions.size());
            saveScore();
            returnToMenu();
        }

        if (e.getSource() == resetBtn) {
            // Abandons the current attempt (no score saved) and goes
            // back to category selection, rather than restarting the
            // same category in place.
            returnToMenu();
        }

        if (e.getSource() == logoutBtn) {
            dispose();
            if (onLogout != null) {
                onLogout.run();
            }
        }

        if (e.getSource() == historyBtn) {
            try {
                String leaderboard = apiClient.getLeaderboardText();
                JOptionPane.showMessageDialog(this, leaderboard, "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Could not load leaderboard: " + ex.getMessage());
            }
        }
    }

    private void returnToMenu() {
        dispose();
        if (onBackToMenu != null) {
            onBackToMenu.run();
        }
    }

    private void handleAnswerAndAdvance() {
        // Guard against duplicate/late clicks (e.g. clicking Next multiple
        // times while a previous click's network call was still in
        // flight) from advancing current past the end of the list.
        if (current >= questions.size()) {
            return;
        }

        // Disable both buttons for the duration of the network call, so a
        // second click can't queue up another call before this one finishes.
        nextBtn.setEnabled(false);
        submitBtn.setEnabled(false);

        try {
            boolean correct = checkAnswerWithServer();

            if (correct) {
                score++;
                JOptionPane.showMessageDialog(this, "Correct!");
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect.");
            }

            current++;
            if (current == questions.size()) {
                nextBtn.setVisible(false);
                submitBtn.setVisible(true);
            } else {
                set();
            }
        } finally {
            nextBtn.setEnabled(true);
            submitBtn.setEnabled(true);
        }
    }

    void saveScore() {
        try {
            apiClient.submitScore(username, category, score, questions.size());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Could not save score to leaderboard: " + e.getMessage());
        }
    }

    /** Asks the server whether the currently selected radio button is correct. */
    boolean checkAnswerWithServer() {
        int selectedIndex = -1;
        for (int i = 0; i < 4; i++) {
            if (radiobutton[i].isSelected()) {
                selectedIndex = i;
                break;
            }
        }
        if (selectedIndex == -1) {
            return false; // nothing selected
        }

        String optionLetter = switch (selectedIndex) {
            case 0 -> "A";
            case 1 -> "B";
            case 2 -> "C";
            default -> "D";
        };

        try {
            return apiClient.submitAnswer(questions.get(current).id(), optionLetter);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Could not grade answer: " + e.getMessage());
            return false;
        }
    }

    void set() {
        ApiClient.ClientQuestion q = questions.get(current);
        questionLabel.setText(q.questionText());
        String[] options = q.optionsArray();
        for (int i = 0; i < 4; i++) {
            radiobutton[i].setText(options[i]);
            radiobutton[i].setVisible(true);
        }
    }
}
