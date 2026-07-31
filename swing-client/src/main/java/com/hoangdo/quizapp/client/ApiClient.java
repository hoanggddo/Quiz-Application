package com.hoangdo.quizapp.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Thin wrapper around the Quiz Application REST API. All network and JSON
 * handling lives here so the Swing UI classes never touch HttpClient or
 * Jackson directly -- same separation-of-concerns idea as the API's own
 * controller/service split.
 */
public class ApiClient {

    private final String baseUrl;
    private final HttpClient httpClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.httpClient = HttpClient.newHttpClient();
    }

    public ApiClient() {
        this("http://localhost:8080");
    }

    // ---- Auth ----

    /** @return null on success, or an error message string on failure */
    public String register(String username, String password) throws IOException, InterruptedException {
        Map<String, String> body = Map.of("username", username, "password", password);
        HttpResponse<String> response = post("/api/auth/register", body);

        if (response.statusCode() == 201) {
            return null;
        }
        return extractErrorMessage(response.body());
    }

    /** @return null on success, or an error message string on failure */
    public String login(String username, String password) throws IOException, InterruptedException {
        Map<String, String> body = Map.of("username", username, "password", password);
        HttpResponse<String> response = post("/api/auth/login", body);

        if (response.statusCode() == 200) {
            return null;
        }
        return extractErrorMessage(response.body());
    }

    // ---- Questions ----

    public List<ClientQuestion> getQuestions(String category) throws IOException, InterruptedException {
        String url = baseUrl + "/api/questions?category=" + java.net.URLEncoder.encode(category, "UTF-8");
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        List<ClientQuestion> result = new ArrayList<>();
        if (response.statusCode() == 200) {
            JsonNode arr = mapper.readTree(response.body());
            for (JsonNode node : arr) {
                result.add(new ClientQuestion(
                        node.get("id").asLong(),
                        node.get("category").asText(),
                        node.get("questionText").asText(),
                        node.get("optionA").asText(),
                        node.get("optionB").asText(),
                        node.get("optionC").asText(),
                        node.get("optionD").asText()
                ));
            }
        }
        return result;
    }

    /** Server-side grading -- the client never knows the correct answer ahead of time. */
    public boolean submitAnswer(Long questionId, String option) throws IOException, InterruptedException {
        String url = baseUrl + "/api/questions/" + questionId + "/answer?option=" + option;
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonNode node = mapper.readTree(response.body());
            return node.get("correct").asBoolean();
        }
        return false;
    }

    // ---- Leaderboard ----

    public void submitScore(String username, String category, int pointsScored, int totalQuestions)
            throws IOException, InterruptedException {
        Map<String, Object> body = Map.of(
                "username", username,
                "category", category,
                "pointsScored", pointsScored,
                "totalQuestions", totalQuestions
        );
        post("/api/leaderboard", body);
    }

    public String getLeaderboardText() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(baseUrl + "/api/leaderboard")).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return "No scores available yet.";
        }

        JsonNode arr = mapper.readTree(response.body());
        if (!arr.isArray() || arr.isEmpty()) {
            return "No scores available yet.";
        }

        StringBuilder sb = new StringBuilder();
        int rank = 1;
        for (JsonNode node : arr) {
            sb.append(rank++).append(". ")
              .append(node.get("username").asText()).append(" - ")
              .append(node.get("pointsScored").asInt()).append("/")
              .append(node.get("totalQuestions").asInt())
              .append(" (").append(node.get("category").asText()).append(")\n");
        }
        return sb.toString();
    }

    // ---- Internal helpers ----

    private HttpResponse<String> post(String path, Object bodyObject) throws IOException, InterruptedException {
        String json = mapper.writeValueAsString(bodyObject);
        HttpRequest request = HttpRequest.newBuilder(URI.create(baseUrl + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private String extractErrorMessage(String responseBody) {
        try {
            JsonNode node = mapper.readTree(responseBody);
            if (node.has("error")) {
                return node.get("error").asText();
            }
            // validation errors come back as a field->message map
            return node.toString();
        } catch (Exception e) {
            return "Unknown error: " + responseBody;
        }
    }

    /** Client-side view of a question. Deliberately has no "correct answer" field. */
    public record ClientQuestion(
            Long id, String category, String questionText,
            String optionA, String optionB, String optionC, String optionD
    ) {
        public String[] optionsArray() {
            return new String[]{optionA, optionB, optionC, optionD};
        }
    }
}
