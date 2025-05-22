
import javax.swing.*;

public class DifficultyManager {
    public enum Difficulty {
        EASY, MEDIUM, HARD
    }

    public static Difficulty chooseDifficulty() {
        String[] options = {"Easy", "Medium", "Hard"};
        int choice = JOptionPane.showOptionDialog(
            null,
            "Choose difficulty level:",
            "Difficulty Selection",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        switch (choice) {
            case 0: return Difficulty.EASY;
            case 1: return Difficulty.MEDIUM;
            case 2: return Difficulty.HARD;
            default: return Difficulty.EASY;
        }
    }
}
