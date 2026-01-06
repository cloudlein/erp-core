# Installation & Setup Guide

## Prerequisites

Ensure you have the following installed on your machine:

- Java Development Kit (JDK) 21 or higher
- Docker & Docker Compose
- Git

## 1. Clone the Repository

```bash
git clone https://github.com/your-username/erp-core.git
cd erp-core
```

## 2. Configure Environment

The project comes with pre-configured profiles.

- `dev`: For local development (uses local database).
- `staging`: For testing environments.
- `prod`: For production deployment.

Check `src/main/resources/application.yaml` for configuration details.

## 3. Start Infrastructure (Database)

Use Docker Compose to spin up PostgreSQL and other dependencies.

```bash
docker-compose up -d
```

_Note: Ensure port 5432 is free or configure `docker-compose.yml` accordingly._

## 4. Build and Run

**Using Gradle Wrapper (Recommended):**

```bash
# Linux / macOS
./gradlew bootRun --args='--spring.profiles.active=dev'

# Windows
./gradlew.bat bootRun --args='--spring.profiles.active=dev'
```

The application will start at `http://localhost:8080`.
