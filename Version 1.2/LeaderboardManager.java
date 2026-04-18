
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class LeaderboardManager {
    private static final String FILE_NAME = "leaderboard.txt";

    public static void saveScore(String username, int score, int total) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(username + ": " + score + "/" + total + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getLeaderboard() {
        try {
            return Files.readString(Path.of(FILE_NAME));
        } catch (IOException e) {
            return "No scores available yet.";
        }
    }
}
