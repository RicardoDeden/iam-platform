# Auth Service

This project is the authentication and authorization microservice for the IAM Platform. It is built with Spring Boot and provides OAuth2 authentication, user management, and integration with PostgreSQL.

## Features
- OAuth2 Authorization Server
- JWT token support
- User authentication and registration
- Integration with PostgreSQL database
- Database migrations managed by Flyway
- Configurable via `application.yml`

## Getting Started

### Prerequisites
- Java 17+
- Maven
- Docker (for running PostgreSQL)

### Database Setup
A PostgreSQL instance is required. You can start one using Docker:

```sh
docker run --name authdb -e POSTGRES_DB=authdb -e POSTGRES_USER=authuser -e POSTGRES_PASSWORD=authsecret -p 5433:5432 -d postgres:15
```

### Configuration
The main configuration file is `src/main/resources/application.yml`. It includes database, server, and logging settings.

### Running the Application

```sh
./mvnw spring-boot:run
```

The service will start on port `8081` by default.

### Database Migration
Flyway is used for database migrations. Migration scripts are located in `src/main/resources/db/migration`.

## API Endpoints
- `/oauth2/token` - Token endpoint
- `/oauth2/authorize` - Authorization endpoint
- Additional endpoints for user management (see controller package)

## Project Structure
- `src/main/java/com/example/auth_service/` - Main source code
- `src/main/resources/` - Configuration and migration scripts
- `src/test/java/com/example/auth_service/` - Tests

## Useful Commands
- Run tests: `./mvnw test`
- Build: `./mvnw clean package`
