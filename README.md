# ChâTop Backend - Rental Management API

Spring Boot REST API for connecting tenants with property owners in tourist areas

---

## Table of Contents

1. [Project Overview](#project-overview)
2. [Features](#features)
3. [Technologies Used](#technologies-used)
4. [Prerequisites](#prerequisites)
5. [Installation](#installation)
6. [Configuration](#configuration)
7. [Running the Project](#running-the-project)
8. [Architecture](#architecture)
9. [API Endpoints](#api-endpoints)
10. [Security](#security)
11. [Tests](#tests)


---

## Project Overview

ChâTop Backend is a **REST API** built with **Spring Boot 3.2.0** that connects tenants with property owners in tourist areas. This API powers the Angular frontend and provides:

- User authentication with JWT tokens
- Rental property management (CRUD operations)
- Message system between tenants and owners
- Secure file upload for property images
- Complete API documentation with Swagger

---

## Features

### Authentication

- **User registration** with bcrypt password encryption
- **JWT-based login** with secure token generation
- **User profile retrieval** for authenticated users

### Rental Management

- **List all rentals** with complete property details
- **Get rental by ID** with owner information
- **Create new rentals** with image upload
- **Update existing rentals** (owner only)
- **Delete rentals** by ID

### Messaging System

- **Send messages** related to rental properties
- Link messages between users and specific rentals

### Cross-cutting Features

- **Swagger documentation** accessible without authentication
- **Error handling** with custom messages
- **CORS configured** for cross-origin development
- **Enhanced security**: credentials in environment variables
- **File upload** with validation and secure storage

---

## Technologies Used

| Technology              | Version  | Description                              |
|-------------------------|----------|------------------------------------------|
| **Java**                | 21       | Programming language                     |
| **Spring Boot**         | 3.2.0    | Backend framework                        |
| **Spring Security**     | 6.x      | Security and authentication              |
| **JWT (JJWT)**          | 0.12.3   | Authentication token management          |
| **Spring Data JPA**     | 3.2.0    | Data access layer                        |
| **MySQL**               | 8.0.33   | Relational database                      |
| **Lombok**              | 1.18.30  | Boilerplate code reduction               |
| **SpringDoc OpenAPI**   | 2.5.0    | Swagger / OpenAPI documentation          |
| **Spring DotEnv**       | 4.0.0    | Environment variables management         |
| **Maven**               | 3.x      | Build & dependency management            |

## Prerequisites

Before starting, make sure you have installed:

- **Java JDK**: version 21 or higher
- **Maven**: version 3.8 or higher
- **MySQL**: version 8.0 or higher
- **Git**: to clone the repository
```bash
# Check installed versions
java --version
mvn --version
mysql --version
```

---

## Installation
```bash
# Clone the repository
git clone https://github.com/jprodrigues644/chatopback.git
cd chatop-backend

# Install Maven dependencies
mvn clean install
```

---

## Configuration

### 1. MySQL Database Setup

Create a MySQL database:
```sql
CREATE DATABASE chatop_db;
CREATE USER 'chatop_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON chatop_db.* TO 'chatop_user'@'localhost';
FLUSH PRIVILEGES;
```

### 2. Environment Variables

Create a `.env` file in the project root:
```env
# Database Configuration
DB_URL=jdbc:mysql://localhost:3306/chatop_db
DB_USERNAME=chatop_user
DB_PASSWORD=your_password

# JWT Configuration
JWT_SECRET_KEY=your_jwt_secret_key_minimum_256_bits_length
JWT_EXPIRATION=3600000

# File Upload Configuration
UPLOAD_DIR=./uploads
MAX_FILE_SIZE=10MB
MAX_REQUEST_SIZE=10MB
```

### 3. Application Properties

The `src/main/resources/application.properties` file uses environment variables:
```properties
spring.application.name=chatopback

# Database
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# File Upload
app.file.upload-dir=${UPLOAD_DIR}
spring.servlet.multipart.max-file-size=${MAX_FILE_SIZE}
spring.servlet.multipart.max-request-size=${MAX_REQUEST_SIZE}

# JWT
app.jwt.secret=${JWT_SECRET_KEY}
app.jwt.expiration=${JWT_EXPIRATION}
```

---

## Running the Project
```bash
# With Maven
mvn spring-boot:run

# Or with Java
mvn clean package
java -jar target/chatopback-0.0.1-SNAPSHOT.jar
```

The application starts on **http://localhost:8080**

### Access Swagger Documentation

- **URL**: http://localhost:8080/swagger-ui/index.html#/
- **API Docs JSON**: http://localhost:8080/v3/api-docs

---

## Architecture

### Project Structure
```
src/main/java/com/op/chatopback/
├── config/                          # Application configuration
│   ├── SecurityConfig.java          # Spring Security + JWT configuration
│   ├── SwaggerConfig.java           # OpenAPI/Swagger configuration
│   ├── FileStorageConfig.java       # File storage configuration
│   └── WebConfig.java               # CORS and static resources configuration
├── controller/                      # REST Controllers
│   ├── AuthController.java          # Authentication (register, login, me)
│   ├── RentalController.java        # Rental management (CRUD)
│   ├── MessageController.java       # Messaging system
│   └── UserController.java          # User management
├── dto/                             # Data Transfer Objects
│   ├── AuthRequest.java
│   ├── AuthResponse.java
│   ├── RegisterRequest.java
│   ├── RentalRequest.java
│   ├── RentalResponse.java
│   ├── RentalsResponse.java
│   ├── MessageRequest.java
│   ├── ApiMessageResponse.java
│   └── UserDto.java
├── mapper/                          # Entity/DTO mappers
│   ├── RentalMapper.java
│   └── UserMapper.java
├── model/                           # JPA Entities
│   ├── User.java
│   ├── Rental.java
│   └── Message.java
├── repository/                      # Spring Data Repositories
│   ├── UserRepository.java
│   ├── RentalRepository.java
│   └── MessageRepository.java
├── security/                        # JWT Security
│   └── JwtFilter.java               # JWT authentication filter
├── service/                         # Business Services
│   ├── AuthService.java
│   ├── RentalService.java
│   ├── MessageService.java
│   ├── UserService.java
│   ├── JwtService.java
│   ├── FileStorageService.java
│   └── CustomUserDetailsService.java
└── util/                            # Utilities
    └── CustomUserDetails.java       # Custom UserDetails wrapper
```

### Architectural Principles

- **Layered architecture**: clear separation between controllers, services, and repositories
- **DTOs**: isolation of JPA entities from REST API
- **Dedicated services**: centralized and testable business logic
- **Security by default**: all routes protected except authentication
- **Stateless**: JWT usage, no server-side sessions
- **Mappers**: clean conversion between entities and DTOs

---

## API Endpoints

### Authentication

| Method | Endpoint              | Description                    | Auth Required |
|--------|-----------------------|--------------------------------|---------------|
| POST   | `/api/auth/register`  | Register a new user            | No            |
| POST   | `/api/auth/login`     | Login and get JWT token        | No            |
| GET    | `/api/auth/me`        | Get current user profile       | Yes           |

### Rentals

| Method | Endpoint              | Description                    | Auth Required |
|--------|-----------------------|--------------------------------|---------------|
| GET    | `/api/rentals`        | Get all rentals                | Yes           |
| GET    | `/api/rentals/{id}`   | Get rental by ID               | Yes           |
| POST   | `/api/rentals`        | Create new rental (with image) | Yes           |
| PUT    | `/api/rentals/{id}`   | Update rental (owner only)     | Yes           |
| DELETE | `/api/rentals/{id}`   | Delete rental                  | Yes           |

### Messages

| Method | Endpoint          | Description                    | Auth Required |
|--------|-------------------|--------------------------------|---------------|
| POST   | `/api/messages`   | Send a message about a rental  | Yes           |

### Users

| Method | Endpoint          | Description              | Auth Required |
|--------|-------------------|--------------------------|---------------|
| GET    | `/api/user/{id}`  | Get user details by ID   | Yes           |

### Static Resources

| Method | Endpoint                    | Description                | Auth Required |
|--------|-----------------------------|----------------------------|---------------|
| GET    | `/uploads/**`               | Access uploaded images     | No            |

---

## Security

### JWT Authentication

- **Algorithm**: HMAC with 256-bit minimum secret key
- **Token lifetime**: 1 hour (configurable)
- **Required header**: `Authorization: Bearer <token>`

### Implemented Best Practices

**Passwords encrypted** with BCrypt  
**Credentials externalized** in `.env` (never committed)  
**Routes protected** by default except authentication  
**CORS configured** for development  
**Swagger accessible** without authentication, but requires JWT to test authenticated calls  
**Input validation** on all endpoints  
**Error handling** with appropriate messages  
**File upload security** with size limits and validation



---

## Tests

### Integration Testing with Swagger

1. Start the application: `mvn spring-boot:run`
2. Open Swagger UI: http://localhost:8080/swagger-ui/index.html#/
3. Authenticate via `/api/auth/login`
4. Click **"Authorize"** and enter the JWT token
5. Test protected endpoints


---

## Database Schema

### Table Structure
```sql
-- Users table
CREATE TABLE `USERS` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `email` varchar(255),
  `name` varchar(255),
  `password` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

-- Rentals table
CREATE TABLE `RENTALS` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255),
  `surface` numeric,
  `price` numeric,
  `picture` varchar(255),
  `description` varchar(2000),
  `owner_id` integer NOT NULL,
  `created_at` timestamp,
  `updated_at` timestamp
);

-- Messages table
CREATE TABLE `MESSAGES` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `rental_id` integer,
  `user_id` integer,
  `message` varchar(2000),
  `created_at` timestamp,
  `updated_at` timestamp
);
CREATE UNIQUE INDEX `USERS_index` ON `USERS` (`email`);

ALTER TABLE `RENTALS` ADD FOREIGN KEY (`owner_id`) REFERENCES `USERS` (`id`);

ALTER TABLE `MESSAGES` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);

ALTER TABLE `MESSAGES` ADD FOREIGN KEY (`rental_id`) REFERENCES `RENTALS` (`id`);

```

---

## File Upload

### Configuration

- **Upload directory**: Configurable via `UPLOAD_DIR` environment variable
- **Max file size**: 10MB (configurable)
- **Allowed formats**: Images (JPG, PNG, etc.)
- **URL pattern**: `http://localhost:8080/uploads/rentals/{filename}`

### Storage Strategy

- Files stored locally in `/uploads/rentals/` directory
- Unique UUID prefix added to prevent filename conflicts
- URLs returned in API responses for frontend display

---


## Author

**João Rodrigues**  
[GitHub](https://github.com/jprodrigues644)

---

## Acknowledgments

- Spring Boot for the robust framework
- JWT for stateless authentication
- OpenAPI/Swagger for interactive documentation
- The Java open-source community

---

## Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [JWT.io](https://jwt.io/)
- [Swagger/OpenAPI Specification](https://swagger.io/specification/)# chatopback

