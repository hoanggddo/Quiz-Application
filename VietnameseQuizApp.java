import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VietnameseQuizApp extends JFrame implements ActionListener {
    JRadioButton[] radiobutton = new JRadioButton[4];
    JLabel questionLabel, title, imageLabel;
    JButton nextBtn, submitBtn, resetBtn, hintBtn, flashcardBtn, historyBtn;

    int current = 0;
    int score = 0;
    boolean flashcardMode = false;
    boolean showingAnswer = false;

    ArrayList<Question> questions = new ArrayList<>();
    String username;
    DifficultyManager.Difficulty difficulty;

    public VietnameseQuizApp(String username, DifficultyManager.Difficulty difficulty) {
        this.username = username;
        this.difficulty = difficulty;

        setTitle("Vietnamese Quiz");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        title = new JLabel("Vietnamese Quiz - " + difficulty);
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

        hintBtn = new JButton("Hint");
        hintBtn.setBounds(550, 400, 120, 40);
        hintBtn.addActionListener(this);
        add(hintBtn);

        flashcardBtn = new JButton("Flashcard Mode");
        flashcardBtn.setBounds(700, 400, 160, 40);
        flashcardBtn.addActionListener(this);
        add(flashcardBtn);

        historyBtn = new JButton("View History");
        historyBtn.setBounds(700, 450, 160, 40);
        historyBtn.addActionListener(this);
        add(historyBtn);

        imageLabel = new JLabel();
        imageLabel.setBounds(700, 150, 200, 200);
        add(imageLabel);

        loadQuestions();
        set();
        setVisible(true);
    }

    void loadQuestions() {
        questions.add(new Question("Q1: What does the word nhà mean in Vietnamese?",
            new String[]{"House", "School", "Food", "Book"}, 0,
            "‘Nhà’ means house.", "Think of where you sleep.", "house.png"));

        questions.add(new Question("Q2: What does trường học mean?",
            new String[]{"Food", "House", "School", "Streams"}, 2,
            "‘Trường học’ means school.", "Where students go daily.", "school.jpg"));

        // Add more based on difficulty level or external file load
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == hintBtn) {
            JOptionPane.showMessageDialog(this, questions.get(current).hint);
        }

        if (e.getSource() == nextBtn) {
            if (flashcardMode) {
                showingAnswer = !showingAnswer;
                if (!showingAnswer) current++;
                if (current >= questions.size()) current = 0;
                setFlashcard();
                return;
            }

            if (checkAnswer()) {
                score++;
                JOptionPane.showMessageDialog(this, "Correct! " + questions.get(current).explanation);
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect. " + questions.get(current).explanation);
            }
            current++;
            if (current == questions.size()) {
                nextBtn.setVisible(false);
                submitBtn.setVisible(true);
            } else {
                set();
            }
        }

        if (e.getSource() == submitBtn) {
            if (checkAnswer()) score++;
            JOptionPane.showMessageDialog(this, "You scored: " + score + "/" + questions.size());
            saveScore();
            System.exit(0);
        }

        if (e.getSource() == resetBtn) {
            current = 0;
            score = 0;
            nextBtn.setVisible(true);
            submitBtn.setVisible(false);
            set();
        }

        if (e.getSource() == flashcardBtn) {
            flashcardMode = !flashcardMode;
            current = 0;
            showingAnswer = false;
            setFlashcard();
        }

        if (e.getSource() == historyBtn) {
            JOptionPane.showMessageDialog(this, LeaderboardManager.getLeaderboard(), "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    void saveScore() {
        LeaderboardManager.saveScore(username, score, questions.size());
    }

    boolean checkAnswer() {
        return radiobutton[questions.get(current).correctIndex].isSelected();
    }

    void set() {
        Question q = questions.get(current);
        questionLabel.setText(q.prompt);
        for (int i = 0; i < 4; i++) {
            radiobutton[i].setText(q.options[i]);
            radiobutton[i].setVisible(true);
        }
        TextToSpeech.speak(q.prompt);
        showingAnswer = false;
        loadImage(q.imagePath);
    }

    void setFlashcard() {
        Question q = questions.get(current);
        for (JRadioButton rb : radiobutton) rb.setVisible(false);
        questionLabel.setText(showingAnswer ? q.options[q.correctIndex] : q.prompt);
        if (!showingAnswer) TextToSpeech.speak(q.prompt);
        else TextToSpeech.speak(q.options[q.correctIndex]);
        loadImage(q.imagePath);
    }

    void loadImage(String imagePath) {
        if (imagePath != null) {
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } else {
            imageLabel.setIcon(null);
        }
    }

    public static void main(String[] args) {
        String username = LoginManager.login();
        DifficultyManager.Difficulty difficulty = DifficultyManager.chooseDifficulty();
        if (username != null) {
            new VietnameseQuizApp(username, difficulty);
        } else {
            System.exit(0);
        }
    }
}
