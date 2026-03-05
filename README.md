# Wine Store Microservices Application

## Overview
This project is a **microservices-based Wine Store backend application** built using **Spring Boot and Spring Cloud**. The system demonstrates how multiple services interact in a distributed architecture while remaining independently deployable and scalable.

The application simulates an e-commerce backend where different services handle product management, inventory tracking, and order processing.

---

## Architecture

The system follows a **microservices architecture**, where each service performs a specific responsibility.

Typical request flow:

Client → API Gateway → Microservices → Database

Key architectural components include:

- **API Gateway** – Entry point for all client requests
- **Service Discovery** – Allows services to dynamically discover each other
- **Microservices** – Independent services handling business logic
- **Databases** – Each service manages its own data

---

## Services

The application consists of multiple microservices such as:

- **Product Service** – Manages product catalog and wine details
- **Inventory Service** – Tracks product availability
- **Order Service** – Handles order creation and processing
- **API Gateway** – Routes external requests to appropriate services
- **Service Discovery** – Enables dynamic service registration and lookup

Each service runs independently and communicates through REST APIs.

---

## Technologies Used

### Backend
- Java
- Spring Boot
- Spring Cloud

### DevOps / Infrastructure
- Docker
- Linux
- AWS (for deployment)

### Tools
- Gradle
- Git
- Postman

---

## Project Structure
