Event-Driven User Service

A production-ready Spring Boot backend project demonstrating event-driven architecture using Spring Application Events to build scalable and loosely coupled systems.

🧩 Problem This Project Solves

In traditional backend systems, services are tightly coupled.

Example:

public void register(User user) {
    userRepository.save(user);
    emailService.sendEmail(user);
    auditService.log(user);
    notificationService.notify(user);
}
❌ Problems:

Business logic tightly coupled to multiple services

Hard to maintain

Difficult to scale

Violates Single Responsibility Principle

Adding new functionality requires modifying existing code

Slows down response due to synchronous execution

✅ Solution: Event-Driven Architecture

This project solves the above problems using Spring Application Events.

Instead of directly calling dependent services, we:

Save the user

Publish a UserRegisteredEvent

Let independent listeners handle side effects asynchronously

🏗️ How It Works
POST /users/register
        ↓
UserController
        ↓
UserService
        ↓
Save User in Database
        ↓
Publish UserRegisteredEvent
        ↓
 ├── EmailListener (Async)
 ├── AuditListener (Async)
 └── NotificationListener (Async)
Key Idea

UserService does not know:

Who sends email

Who logs audit

Who calls external systems

It only publishes an event.

This ensures:

Loose coupling

Better scalability

Cleaner architecture

Easy extensibility

⚡ Why Event-Driven Design?

This architecture is used in:

E-commerce platforms

Banking systems

Microservices architecture

Kafka-based distributed systems

Domain-Driven Design systems

It allows:

Non-blocking background processing

Independent service evolution

High maintainability

Open/Closed Principle compliance

🛠️ Tech Stack

Java 17

Spring Boot 3

Spring Web

Spring Data JPA

H2 In-Memory Database

Lombok

Maven

📂 Project Structure
com.nazir.userservice
│
├── controller      → REST APIs
├── service         → Business logic
├── repository      → JPA repositories
├── entity          → Database entities
├── event           → Domain events
├── listener        → Event listeners
└── config          → Async configuration
🚀 Features

Publish domain events using ApplicationEventPublisher

Asynchronous event listeners using @Async

Clean layered architecture

Transaction-safe event triggering

Extensible and scalable design

Ready for microservice transformation

▶️ Getting Started
1️⃣ Clone the repository
git clone https://github.com/your-username/event-driven-user-service.git
cd event-driven-user-service
2️⃣ Build
mvn clean install
3️⃣ Run
mvn spring-boot:run

Application runs on:

http://localhost:8080
📮 API
Register User
POST /users/register

Request Body:

{
  "name": "Nazir",
  "email": "nazir@example.com"
}