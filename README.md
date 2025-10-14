# IAM Platform

This repository contains microservices for Identity and Access Management (IAM), including authentication, authorization, and user management. Each service is built with Spring Boot and communicates via REST APIs.

## Services

### Auth Service
- Location: `auth-service/`
- Provides OAuth2 authentication and authorization
- Manages tokens and client registrations
- See `auth-service/README.md` for details

### User Management Service
- Location: `user-management/`
- Handles user CRUD operations
- Provides RESTful endpoints for user management
- See `user-management/README.md` for details

## Prerequisites
- Java 17+
- Maven
- Docker (for running PostgreSQL)

## Getting Started

1. **Clone the repository:**
   ```sh
   git clone <repo-url>
   cd iam-platform
   ```
2. **Start PostgreSQL containers using Docker Compose:**
   - The repository includes a `compose.yaml` file that defines two PostgreSQL services:
     - `auth-storage` for the Auth Service (port 5433)
     - `user-storage` for the User Management Service (port 5432)
   - Start both databases with:
     ```sh
     docker compose up -d
     ```
3. **Configure each service:**
   - Edit the `application.yml` files in each service as needed to match the database settings in `compose.yaml`.

4. **Run the services:**
   - Auth Service:
     ```sh
     cd auth-service
     ./mvnw spring-boot:run
     ```
   - User Management Service:
     ```sh
     cd user-management
     ./mvnw spring-boot:run
     ```

## Project Structure
- `auth-service/` - Authentication and authorization microservice
- `user-management/` - User management microservice

## License
This project is licensed under the MIT License.
