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

## Deploying a Shared Server (so multiple people share one leaderboard)

By default, every person who runs this app gets their own private local
database -- accounts and scores don't carry over between machines. To
make it a real shared experience, deploy the API once to a hosting
platform, and point every client at that one server instead.

These steps use **Render** (free tier covers both the web service and a
Postgres database, and it deploys directly from your GitHub repo).

**1. Push this repo to GitHub** if you haven't already (see earlier
instructions in this conversation for the git commands).

**2. Create a free Postgres database on Render:**
- Go to https://dashboard.render.com → **New** → **PostgreSQL**
- Give it a name (e.g. `quiz-app-db`), choose the free plan, create it
- Once it's ready, open it and note down: **Hostname**, **Port**,
  **Database**, **Username**, **Password** (shown on the database's page)

**3. Create a web service for the API:**
- **New** → **Web Service** → connect your GitHub repo
- **Runtime**: Docker (it will use the `Dockerfile` already in this repo)
- **Instance type**: Free

**4. Set these environment variables on the web service** (Render's
dashboard → your service → **Environment**):

| Key | Value |
|---|---|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://<Hostname>:<Port>/<Database>` (build this from step 2's values) |
| `SPRING_DATASOURCE_USERNAME` | the Username from step 2 |
| `SPRING_DATASOURCE_PASSWORD` | the Password from step 2 |
| `SPRING_DATASOURCE_DRIVER_CLASS_NAME` | `org.postgresql.Driver` |
| `SPRING_H2_CONSOLE_ENABLED` | `false` |

**5. Deploy.** Render will build the Docker image and start it. Once
live, your API is reachable at something like
`https://quiz-application-api.onrender.com`.

**6. Verify it's actually working** before connecting any client:
```
curl https://your-app-name.onrender.com/api/leaderboard
```
Should return `[]` (empty leaderboard, not an error).

**Two things to expect on Render's free tier:**
- The service **sleeps after ~15 minutes of no traffic**, and the first
  request after that takes 30-60 seconds to wake it back up. This isn't
  a bug -- it's the free tier's tradeoff.
- The database has a **row/storage limit** on the free plan, fine for a
  student project, not for real production traffic.

**Honesty note:** I have not been able to actually deploy this myself
(no network access in the environment I built this in), so these steps
follow Render's documented process closely but are untested end-to-end.
If something doesn't match what you see on Render's dashboard exactly
(their UI does change), tell me what you're seeing and I'll adjust.

## Running in Shared Mode (once deployed)

```
java -Dquiz.server.url=https://your-app-name.onrender.com -jar target\quiz-application-api-1.0.0.jar
```

This skips starting a local server entirely and connects straight to
your deployed one. Anyone who runs this same command (with the same URL)
shares the same accounts and leaderboard, regardless of what machine
they're on.

Without that flag, it falls back to the original solo mode (local
embedded server, local H2 file, private to that machine) -- see below.

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
