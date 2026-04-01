# Enterprise ERP Core

## Overview

Enterprise ERP Core is a robust, modular, and scalable Enterprise Resource Planning system built with **Spring Boot 3** and **Java 17+**. It adopts **Modular Monolith Architecture** combined with **Domain-Driven Design (DDD)** and **Hexagonal Architecture** (Ports and Adapters) to ensure maintainability, testability, and loose coupling between business logic and infrastructure.

This project is designed to be **production-ready**, incorporating advanced patterns such as Event-Driven Architecture, Async Processing, and comprehensive Observability (Logging, Metrics, Tracing).

---

## Key Features

- **Modular Architecture**: Strictly separated modules (Auth, Catalog, Inventory, Sales, Procurement) with defined boundaries.
- **Hexagonal & DDD**: Pure domain logic isolated from frameworks and databases.
- **Security**: Role-Based Access Control (RBAC) using Spring Security and JWT.
- **Event-Driven**: Async communication between modules using Spring Events (decoupling transaction boundaries).
- **Observability**: Integrated with Prometheus, Grafana, and Zipkin/Tempo for full system visibility.
- **Auditability**: comprehensive audit trails for critical entities.

---

## Application Flow

This project follows a Modular Monolith structure where each domain is encapsulated within its own module, following the **Hexagonal Architecture** principles.

```text
                     ┌───────────────────────┐
                     │     User / Browser    │
                     └───────────┬───────────┘
                                 │
                 ┌───────────────┴───────────────┐
                 │             HTTPS             │
                 ▼                               ▼
       ┌──────────────────┐            ┌──────────────────┐
       │ Next.js Frontend │            │  Mobile Client   │
       └─────────┬────────┘            └─────────┬────────┘
                 │            HTTPS              │
                 └───────────────┬───────────────┘
                                 │
                     ┌───────────▼───────────┐
                     │     API Gateway /     │
                     │    Spring Security    │
                     └───────────┬───────────┘
                                 │
           ┌─────────────────────┼─────────────────────┐
           │                     │                     │
           │ /api/auth           │ /api/catalog        │ /api/sales
           ▼                     ▼                     ▼
    ┌─────────────┐       ┌─────────────┐       ┌─────────────┐
    │ Auth Module │       │ Catalog Mod │       │ Sales Mod   │
    │ (Users/Roles)│       │ (Products)  │       │ (Orders)    │
    └──────┬──────┘       └──────┬──────┘       └──────┬──────┘
           │                     │                     │
           │               ┌─────▼─────┐               │
           └──────────────►│ Inventory │◄──────────────┘
                           │ (Stocks)  │
                           └─────┬─────┘
                                 │
                     ┌───────────▼───────────┐
                     │  PostgreSQL Database  │
                     │    (ERP Core Dev)     │
                     └───────────────────────┘
```

---

## Technology Stack

*   **Language**: Java 21
*   **Framework**: Spring Boot 3.x
- **Database**: PostgreSQL
- **Persistence**: Spring Data JPA (Hibernate)
- **Build Tool**: Gradle
- **Containerization**: Docker & Docker Compose

---

## Documentation

For detailed information, please refer to the following documentation:

- [**Installation & Setup Guide**](docs/INSTALLATION.md) - How to run the project locally.
- [**Deployment Guide**](docs/DEPLOYMENT.md) - How to build and deploy to production.
- [**Database Design & Schema**](docs/DATABASE_DESIGN.md) - ERD and Table mappings.
- [**Project Structure & Architecture**](docs/PROJECT_STRUCTURE.md) - Explanation of Hexagonal/DDD structure.

---

## License

This project is licensed under the MIT License.
