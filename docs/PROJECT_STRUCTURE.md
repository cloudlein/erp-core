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

## Per-Module Structure Details

Each module (e.g., `catalog`) MUST follow this strict Hexagonal layer structure:

```text
com.learn.erp_core.catalog/
├── domain/                     # LAYER 1: THE CORE (Pure Java, No Spring)
│   ├── model/                  # Aggregate Root, Entities, Value Objects
│   │   ├── Product.java
│   │   └── ProductId.java
│   ├── repository/             # Output Port (Interface Only)
│   │   └── ProductRepository.java
│   ├── service/                # Domain Service (Complex business logic)
│   │   └── ProductPricingService.java
│   └── event/                  # Domain Events
│       └── ProductCreatedEvent.java
│
├── application/                # LAYER 2: ORCHESTRATION (Use Cases)
│   ├── port/                   # Input Port (Interface for Primary Adapter)
│   │   └── in/
│   │       ├── CreateProductUseCase.java
│   │       └── GetProductUseCase.java
│   ├── service/                # Use Case Implementation
│   │   └── ProductApplicationService.java
│   └── dto/                    # Data Transfer Objects (Request/Response)
│       ├── CreateProductCommand.java
│       └── ProductResponse.java
│
└── adapter/                    # LAYER 3: INFRASTRUCTURE (Framework & IO)
    ├── in/                     # Primary Adapter (Inbound)
    │   ├── web/                # REST API Controller
    │   │   └── ProductController.java
    │   └── listener/           # Event Listener (Kafka/RabbitMQ/SpringEvent)
    │       └── StockLowListener.java
    │
    └── out/                    # Secondary Adapter (Outbound)
        ├── persistence/        # Database Implementation
        │   ├── ProductJpaEntity.java      # @Entity (Hibernate)
        │   ├── ProductJpaRepository.java  # Spring Data JPA Interface
        │   ├── ProductPersistenceAdapter.java # Implements Domain Repository
        │   └── ProductMapper.java         # Mapping Domain <-> Entity
        │
        └── external/           # External System (e.g., Payment Gateway)
            └── ThirdPartyCatalogClient.java
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
