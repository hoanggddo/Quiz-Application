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

## What I'd Add Next

- JWT-based session tokens instead of stateless login (currently each
  request is independent; a real deployment would issue a token on login
  and require it on subsequent requests)
- Rate limiting on `/api/auth/login` to slow down brute-force attempts
- Migrate the original Swing app's full question bank into `data.sql`
- Integration tests using `@SpringBootTest` and Testcontainers, in addition
  to the current unit tests
