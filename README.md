Absolutely Pau — here’s a **clean, concise, professional English version** of your README, keeping all sections but trimming the excess detail.  
This version reads smoothly, feels polished, and is perfect for a public repo.

---

# Blackjack RESTful API

A reactive RESTful API for playing Blackjack, built with **Spring Boot WebFlux**, following **Domain‑Driven Design (DDD)** and **Hexagonal Architecture**.

---

## 📋 Table of Contents

1. Exercise Description
2. Technologies Used
3. Requirements
4. Installation Guide
5. Execution Guide
6. Deployment
7. API Documentation
8. Architecture

---

## 🎯 Exercise Description

This project implements a complete Blackjack game API with:

### Player Management
- Create, retrieve, list, search, rename, and delete players
- Ranking sorted by win rate

### Game Management
- Create a new game
- Retrieve game state
- Play moves (`HIT`, `STAND`)
- Delete games

### Game Logic
- Automatic bust detection
- Dealer logic
- Winner determination
- Score updates and game statistics

### Additional Features
- Input validation
- Centralized error handling
- Fully reactive, non‑blocking design

---

## 🛠 Technologies Used

- **Spring Boot 3 + WebFlux**
- **Java 21**
- **MongoDB** (game storage)
- **MySQL + R2DBC** (player storage)
- **Project Reactor**
- **SpringDoc OpenAPI (Swagger)**
- **Docker & Docker Compose**
- **JUnit 5 + Mockito**

---

## 📦 Requirements

- JDK 21
- Maven 3.8+
- MongoDB 7.0
- MySQL 8.0
- (Optional) Docker + Docker Compose
- Git and IntelliJ IDEA recommended

---

## 📥 Installation Guide

1. Clone the repository
2. Verify Java and Maven installations
3. Install dependencies with `mvn clean install`
4. Set up MongoDB and MySQL (locally or via Docker)

---

## 🚀 Execution Guide

### Local Execution
Configure `application.yml` to point to `localhost`, then run:

```
mvn spring-boot:run
```

Swagger UI:  
`http://localhost:8080/swagger-ui.html`

### With Docker (Databases Only)
Start MongoDB and MySQL:

```
docker-compose up -d
```

Run the application locally using these containers.

### Dockerizing the Application (Optional)
Build the JAR, create the Docker image, and run the container with environment variables.

---

## 🌐 Deployment (Render)

- Push the repository to GitHub
- Create a Render Web Service
- Configure environment variables (MongoDB, MySQL, R2DBC)
- Render builds and deploys automatically

---

## 📚 API Documentation

Swagger UI:  
`/swagger-ui.html`

### Player Endpoints
- POST `/player`
- GET `/player/{id}`
- GET `/player`
- GET `/player/search`
- GET `/player/ranking`
- PUT `/player/{id}`
- DELETE `/player/{id}`

### Game Endpoints
- POST `/game/new`
- GET `/game/{id}`
- POST `/game/{id}/play`
- DELETE `/game/{id}/delete`

---

## 🏗 Architecture

The project follows **DDD + Hexagonal Architecture**:

- **Domain**: core business logic, entities, value objects, domain services
- **Application**: use cases
- **Infrastructure**: controllers, persistence adapters, configuration

---

## 🧪 Testing

Run all tests:

```
mvn test
```

Covers domain logic, use cases, controllers, and persistence adapters.

---