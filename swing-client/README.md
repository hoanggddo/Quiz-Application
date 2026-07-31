# Quiz Swing Client

The original Java Swing desktop UI, reconnected to call the Spring Boot
backend (`quiz-application-api`) over HTTP instead of reading local
`users.txt`/`leaderboard.txt` files.

## How It Connects to the API

```
QuizClientApp (Swing UI)
      |
      v
  LoginManager  ----HTTP---->  POST /api/auth/register
                ----HTTP---->  POST /api/auth/login
      |
      v
CategoryManager (local dialog, no network call)
      |
      v
  ApiClient     ----HTTP---->  GET  /api/questions?category=X
                ----HTTP---->  POST /api/questions/{id}/answer
                ----HTTP---->  POST /api/leaderboard
                ----HTTP---->  GET  /api/leaderboard
```

All network calls go through `ApiClient`, the Swing UI classes never touch
`HttpClient` or JSON directly.

## What Changed From the Original Swing App

| Original | This version | Why |
|---|---|---|
| `LoginManager` read/wrote `users.txt` in plaintext | Calls `/api/auth/*`, passwords hashed server-side with BCrypt | Security fix from the backend rebuild |
| `Question` had a hardcoded `correctIndex` field | Server grades answers via `/api/questions/{id}/answer`; client never sees the answer | Client-side answer storage is a cheat/security smell |
| `DifficultyManager` picked Easy/Medium/Hard | `CategoryManager` picks Vocabulary/Grammar/History & Culture | The API's data model organizes by category, not difficulty — no difficulty field exists server-side |
| `TextToSpeech` used the FreeTTS library | Removed | FreeTTS was vendored library clutter removed during the backend cleanup; would need to be re-added deliberately as a proper dependency if wanted back |
| Per-question images (`house.png`, etc.) | Removed | The API's `Question` entity has no image field currently |
| `LeaderboardManager` read/wrote `leaderboard.txt` | Calls `/api/leaderboard` | Centralizes scores in the same database as everything else |

## Running It

**1. Start the API first** (from the `quiz-application-api` project):

```bash
mvn spring-boot:run
```

Confirm it's up by visiting `http://localhost:8080/api/leaderboard` in a
browser — you should see `[]` or a list of scores, not a connection error.

**2. Then run this client**, from this folder:

```bash
mvn clean package
java -jar target/quiz-swing-client-1.0.0.jar
```

The client defaults to `http://localhost:8080`. To point it at a
different host (e.g. once the API is deployed somewhere), change the
`ApiClient` constructor call in `QuizClientApp.main()`.

## Known Gaps / What I'd Add Back Next

- **Hints**: the "Hint" button currently shows a placeholder message. Add
  a `hint` column to the API's `Question` entity and `QuestionResponse`
  DTO to restore this.
- **Text-to-speech**: intentionally not reintroduced yet (see table above).
- **Question images**: same — would need an `imageUrl` field added to the
  API side first.
- **Error handling**: if the API is unreachable, dialogs currently show a
  raw exception message. Fine for a student project, but a real app would
  want friendlier error states and retry logic.
