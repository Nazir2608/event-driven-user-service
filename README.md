# Event-Driven User Service

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Architecture](https://img.shields.io/badge/Architecture-Event--Driven-blue)](#-architecture)

A high-performance Spring Boot microservice demonstrating **Event-Driven Architecture (EDA)**. This project showcases how to decouple core business logic from side effects using **Spring Application Events** and asynchronous processing.

---

## 🧩 The Challenge: Tight Coupling
In traditional designs, a single service method handles too many responsibilities.

### The "Before" (Anti-Pattern)
```java
public void register(User user) {
    userRepository.save(user); // Core Logic
    emailService.sendWelcomeEmail(user); // Side Effect
    auditService.logRegistration(user);  // Side Effect
    notificationService.notifyAdmin(user); // Side Effect
}

```

* **❌ Risks:** High latency, violation of SRP (Single Responsibility Principle), and difficult-to-test code.

---

## ✅ The Solution: Event-Driven Design

The `UserService` focuses solely on persistence and emits a `UserRegisteredEvent`.

### 🏗️ Workflow

1. **Client** hits `POST /users/register`.
2. **UserService** persists the User to the Database.
3. **ApplicationEventPublisher** broadcasts a `UserRegisteredEvent`.
4. **Listeners** (`@EventListener` / `@Async`) intercept the event independently.

---

## ⚡ Key Architectural Benefits

| Feature | Description |
| --- | --- |
| **Loose Coupling** | `UserService` has zero knowledge of email or audit logic. |
| **Non-Blocking** | Side effects run on separate threads for faster responses. |
| **Scalability** | Easily add new listeners without touching core code. |
| **Resilience** | Listener failures don't impact the primary transaction. |

---

## 📂 Project Structure

```text
com.nazir.userservice
├── config      → Async & TaskExecutor configurations
├── controller  → REST Endpoints (Web Layer)
├── entity      → JPA Data Models
├── event       → Immutable Domain Events
├── listener    → Asynchronous Event Handlers
├── repository  → Data Access Layer
└── service     → Core Business Logic & Event Publishing

```

---

## ▶️ Getting Started

### Installation

```bash
git clone [https://github.com/your-username/event-driven-user-service.git](https://github.com/your-username/event-driven-user-service.git)
cd event-driven-user-service
mvn clean install
mvn spring-boot:run

```

### 📮 API Documentation

**POST** `/users/register`

**Request:**

```json
{
  "name": "Nazir",
  "email": "nazir@example.com"
}

```

**Response (201 Created):**

```json
{
  "id": 1,
  "name": "Nazir",
  "status": "Registration processing..."
}

```