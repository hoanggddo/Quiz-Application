# Quiz Application API

A REST API backend for the Vietnamese Quiz Application, rebuilt from the
original Java Swing desktop app into a Spring Boot service with a proper
layered architecture and relational persistence.

## Why This Exists

The original version stored users in a plaintext `.txt` file and ran as a
single-user Swing desktop app. This version follows the architecture pattern
used across backend roles at DMV-area employers (defense contractors,
consulting firms, and financial tech companies): a layered Spring Boot
service backed by a relational database, with password hashing, input
validation, and automated tests.

## Architecture

```
controller/   -> REST endpoints (HTTP concerns only)
service/      -> business logic
repository/   -> Spring Data JPA interfaces (no hand-written SQL)
model/        -> JPA entities (users, questions, scores)
dto/          -> request/response shapes (entities are never exposed directly)
config/       -> BCryptPasswordEncoder bean
exception/    -> centralized error handling
```

## Tech Stack

- Java 17
- Spring Boot 3 (Web, Data JPA, Validation)
- H2 (file-based relational database for local/dev use)
- Spring Security Crypto (BCrypt password hashing)
- JUnit 5 + Mockito + AssertJ
- Maven
- Docker

## Running Locally

```bash
mvn clean install
mvn spring-boot:run
```

The API starts on `http://localhost:8080`. The H2 console is available at
`http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:file:./data/quizdb`)
for inspecting the database during development.

## Running Tests

```bash
mvn test
```

## Running with Docker

```bash
docker build -t quiz-application-api .
docker run -p 8080:8080 quiz-application-api
```

## API Endpoints

| Method | Endpoint                       | Description                          |
|--------|---------------------------------|---------------------------------------|
| POST   | `/api/auth/register`           | Create a new account                  |
| POST   | `/api/auth/login`               | Log in with username/password         |
| GET    | `/api/questions?category=X`     | Fetch questions for a category        |
| POST   | `/api/questions/{id}/answer`    | Submit an answer, graded server-side  |
| POST   | `/api/leaderboard`               | Submit a quiz score                   |
| GET    | `/api/leaderboard`                | Get the top 10 scores                 |

### Example: Register

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username": "hoang", "password": "correcthorsebattery"}'
```

### Example: Fetch questions

```bash
curl "http://localhost:8080/api/questions?category=Vocabulary"
```

## Migrating to PostgreSQL/MySQL for Production

This project uses H2 for simplicity, but the entity/repository layer is
database-agnostic. To move to PostgreSQL:

1. Replace the H2 dependency in `pom.xml` with `org.postgresql:postgresql`
2. Update `spring.datasource.url`, username, and password in
   `application.properties`
3. No changes needed to any entity, repository, service, or controller code

## Two Ways to Run This

**1. Combined desktop app (default)** — one process, one window, this is
what an end user runs:

```bash
mvn clean package
java -jar target/quiz-application-api-1.0.0.jar
```

This starts the embedded API server in the background and opens the
Swing UI automatically. No terminal commands to explain to anyone, no
separate server to start first.

**2. API only** (for development, testing with curl/Postman, or demoing
the backend architecture on its own — no Swing window opens):

```bash
mvn spring-boot:run -Dspring-boot.run.main-class=com.hoangdo.quizapp.QuizApplication
```

## Building a Standalone Installer (no Java installation required)

`jpackage` (bundled with the JDK since Java 14) can wrap the packaged jar
and a private Java runtime into a native installer, so an end user with
no Java installed at all can just download and run it.

```bash
mvn clean package

jpackage \
  --input target \
  --main-jar quiz-application-api-1.0.0.jar \
  --name "Vietnamese Quiz" \
  --type exe \
  --win-shortcut \
  --win-menu
```

Note this deliberately omits `--main-class` — the jar's manifest already
points to `DesktopApp` via the Maven plugin config in `pom.xml`, and
Spring Boot's repackaged jars use their own internal loader for nested
dependencies, so overriding the main class at the `jpackage` step instead
of at build time does not work correctly.

This produces a `Vietnamese Quiz-1.0.0.exe` installer. Running it installs
the app with a bundled Java runtime and adds a Start Menu / desktop
shortcut.

(On macOS: use `--type dmg` or `--type pkg` instead of `--type exe`.
`jpackage` must be run on the same OS you're targeting.)

**Honesty note:** I have not been able to actually run `jpackage` against
this project — no JDK/jpackage available in the environment I built this
in. The Maven configuration above follows Spring Boot's documented
packaging behavior, but treat the `jpackage` step specifically as
untested. If it fails, the fallback is distributing the plain jar from
step 1 with a one-line instruction: "install Java, then run
`java -jar quiz-application-api-1.0.0.jar`."

## What I'd Add Next

- JWT-based session tokens instead of stateless login (currently each
  request is independent; a real deployment would issue a token on login
  and require it on subsequent requests)
- Rate limiting on `/api/auth/login` to slow down brute-force attempts
- Migrate the original Swing app's full question bank into `data.sql`
- Integration tests using `@SpringBootTest` and Testcontainers, in addition
  to the current unit tests
