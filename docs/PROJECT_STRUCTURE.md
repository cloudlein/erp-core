# Project Structure: Hexagonal Architecture (Ports & Adapters) with DDD

This document explains the folder structure and strict Architectural Boundaries for the ERP Core project.

## Philosophy

We use **Hexagonal Architecture** to separate _Domain Logic_ from _Infrastructure_.

1.  **Domain (Core)**: Contains pure Business Logic. Must NOT depend on Spring Boot, Database, or external IO libraries.
2.  **Application**: Contains Use Cases (Services) that orchestrate the Domain.
3.  **Infrastructure (Adapters)**: Technical implementation (Controller, Repository, External API).

---

## Global Folder Structure

Root package: `com.learn.erp_core`

This project is broken down by **Modules (Bounded Context)** first, then by Layer.

```text
src/main/java/com/learn/erp_core/
├── shared/                     # Shared Kernel (Value Objects, Utils used by all modules)
│   ├── domain/                 # BaseEntity, DomainEvent
│   └── infra/                  # Global Exception Handler, Security Utils
│
├── auth/                       # Module: Authentication
├── catalog/                    # Module: Product & Category
├── inventory/                  # Module: Warehouse & Stock
├── sales/                      # Module: Sales Order
└── procurement/                # Module: Purchase Order
```

---

## Per-Module Structure Details (Example: Auth Module)

Each module (e.g., `auth`) MUST follow this strict Hexagonal layer structure:

```text
com.learn.erp_core.auth/
├── domain/                     # LAYER 1: THE CORE (Pure Java, No Spring)
│   ├── model/                  # Aggregate Root, Entities, Value Objects
│   ├── repository/             # Output Port (Interface Only)
│   ├── service/                # Domain Service (Complex business logic)
│   └── event/                  # Domain Events
│
├── application/                # LAYER 2: ORCHESTRATION (Use Cases)
│   ├── port/                   # Input Port (Interface for Primary Adapter)
│   │   └── in/
│   ├── service/                # Use Case Implementation (Application Services)
│   ├── dto/                    # Data Transfer Objects (Command/Response)
│   └── usecase/                # Explicit Use Case definitions (optional but recommended)
│
└── adapter/                    # LAYER 3: INFRASTRUCTURE (Framework & IO)
    ├── in/                     # Primary Adapter (Inbound)
    │   ├── web/                # REST API Controller (e.g., AuthController)
    │   └── listener/           # Event Listener (Kafka/RabbitMQ/SpringEvent)
    │
    └── out/                    # Secondary Adapter (Outbound)
        ├── persistence/        # Database Implementation (User, Role tables)
        └── security/           # Integration with Spring Security, JWT, Password Encoder
```

---

## Dependency Rules

Dependency direction always points **inwards (Center)**.

1.  **Domain** MUST NOT depend on anything (Application, Adapter, Framework).
2.  **Application** may only depend on **Domain**.
3.  **Adapter** depends on **Application** and **Domain**.

### Forbidden

- Using annotations `@Entity`, `@Table`, `@Column` (JPA) inside `domain` package.
- Using `@RestController`, `@Service` (Spring) inside `domain` package.
- Returning `JpaEntity` directly to Controller (must convert to DTO).

### Allowed

- **Domain** uses POJO (Plain Old Java Object).
- **Adapter Persistence** maps `Domain Model` to `JpaEntity` on save, and vice versa on load.

---

## Example Flow (Create Product)

1.  **User** hits API `POST /products`.
2.  **ProductController** (`adapter.in.web`) receives request -> converts to `CreateProductCommand` (DTO).
3.  Controller calls `CreateProductUseCase.createProduct(...)` (Interface in `application`).
4.  **ProductApplicationService** (`application.service`) executes logic:
    - Creates **Product** object (`domain.model`).
    - Calls `ProductRepository.save(...)` (Interface in `domain`).
5.  **ProductPersistenceAdapter** (`adapter.out.persistence`) implements `save`:
    - Converts **Product** (Domain) to **ProductJpaEntity** (Database).
    - Saves using Spring Data JPA.
    - Returns result back up the chain.
