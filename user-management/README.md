# User Management Service

This project is the user management microservice for the IAM Platform. It is built with Spring Boot and provides RESTful APIs for user operations, integrates with PostgreSQL, and uses JPA for persistence.

## Features
- User CRUD operations
- RESTful API endpoints
- Integration with PostgreSQL database
- JPA/Hibernate for ORM
- Configurable via `application.yml`

## Getting Started

### Prerequisites
- Java 17+
- Maven
- Docker (for running PostgreSQL)

### Database Setup
A PostgreSQL instance is required. You can start one using Docker:

```sh
docker run --name userdb -e POSTGRES_DB=userdb -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=secret -p 5432:5432 -d postgres:15
```

### Configuration
The main configuration file is `src/main/resources/application.yml`. It includes database, server, and JPA settings.

### Running the Application

```sh
./mvnw spring-boot:run
```

The service will start on port `8080` by default.

## API Endpoints
- `/users` - User management endpoints (CRUD)
- See the `resource` package for more details

## Project Structure
- `src/main/java/com/example/user_management/` - Main source code
- `src/main/resources/` - Configuration files
- `src/test/java/com/example/user_management/` - Tests

## Useful Commands
- Run tests: `./mvnw test`
- Build: `./mvnw clean package`

