# ERP Core - Project Roadmap & Task List

This file summarizes the development lifecycle of the Enterprise ERP project. Tasks are derived from the [DATABASE_DESIGN.md](docs/DATABASE_DESIGN.md) and [PROJECT_STRUCTURE.md](docs/PROJECT_STRUCTURE.md) specifications.

---

## Phase 0: Foundations & Shared Kernel

Standardization of common utilities and base classes required globally across modules.

- [x] **Infrastructure Configuration**
  - [x] Initialize `Project Structure` (Verification of directories: `shared`, `auth`, `catalog`, etc.)
  - [x] Implement `BaseEntity` (MappedSuperclass with audit attributes: `created_at`, `updated_at`, etc.)
  - [x] Implement `Global Exception Handling` mechanism
- [/] **Security & Authentication Infrastructure**
  - [ ] Configure Spring Security with JWT (JSON Web Token)
  - [ ] Implement `PasswordHashing` service with BCrypt

---

## Phase 1: User & Authentication Module (`com.learn.erp_core.user`)

Development of authentication, authorization, and user profile management.

- [x] **Domain Layer**
  - [x] Define `User`, `Role`, and `Permission` domain models
  - [x] Define `UserRepository` port interface
- [x] **Application Layer**
  - [x] Implement `RegisterUserUseCase` and `LoginUseCase`
  - [x] Develop User DTOs (Data Transfer Objects)
- [x] **Infrastructure Layer**
  - [x] Implement `UserJpaEntity` persistence mapping
  - [x] Develop `UserPersistenceAdapter` implementation
  - [x] Implement `AuthController` REST endpoints

---

## Phase 2: Catalog Module (`com.learn.erp_core.catalog`)

Product management and category hierarchical structure development.

- [ ] **Domain Layer**
  - [ ] Implement `Category` domain model (Hierarchical support)
  - [ ] Implement `Product` domain model (Including SKU, Pricing, and UOM)
  - [ ] Define Repository port interfaces
- [ ] **Application Layer**
  - [ ] Implement `CategoryService` (Standard Lifecycle)
  - [ ] Implement `ProductService` (Standard Lifecycle)
- [ ] **Infrastructure Layer**
  - [ ] Implement JPA persistence mapping for Category and Product
  - [ ] Develop `CategoryController` and `ProductController` REST endpoints

---

## Phase 3: Inventory Module (`com.learn.erp_core.inventory`)

Warehouse management and stock tracking system.

- [ ] **Domain Layer**
  - [ ] Define `Warehouse` domain model
  - [ ] Define `Stock` domain model (Inventory tracking per Product/Warehouse)
  - [ ] Define `StockMutation` domain model (Audit trail for inventory changes)
- [ ] **Application Layer**
  - [ ] Implement `WarehouseService`
  - [ ] Implement `StockUpdateUseCase` (Handling reservations and physical stock transactions)
- [ ] **Infrastructure Layer**
  - [ ] Implement JPA persistence mapping for Warehouse and Stock entities
  - [ ] Develop `WarehouseController` REST endpoints

---

## Phase 4: Partner Module (`com.learn.erp_core.partner`)

Management of external entities including Customers and Suppliers.

- [ ] **Domain Layer**
  - [ ] Define `Partner` domain model (Classification: CUSTOMER, SUPPLIER)
- [ ] **Application Layer**
  - [ ] Implement `PartnerService` (Standard Lifecycle)
- [ ] **Infrastructure Layer**
  - [ ] Implement `PartnerJpaEntity` persistence mapping
  - [ ] Develop `PartnerController` REST endpoints

---

## Phase 5: Sales & Procurement Modules (`com.learn.erp_core.sales`, `procurement`)

Transaction management for Purchase and Sales orders.

- [ ] **Domain Layer**
  - [ ] Define `Order` (Sales/Purchase) and `OrderItem` domain models
  - [ ] Implement state management for Status transitions (DRAFT, CONFIRMED, COMPLETED)
- [ ] **Application Layer**
  - [ ] Implement `CreateOrderUseCase`
  - [ ] Implement `ProcessOrderUseCase` (Integration with Inventory for automated stock deduction)
- [ ] **Infrastructure Layer**
  - [ ] Implement Persistence layer for Orders and Items
  - [ ] Develop Sales and Procurement Controllers REST endpoints

---

## Phase 6: DevOps & Deployment Infrastructure

- [x] Finalize `Dockerfile` and `docker-compose.yml` configurations
  - [x] Implement layer caching for faster builds
  - [x] Configure `.dockerignore` for context optimization
- [x] Configure `CI/CD` deployment pipelines (GitHub Actions)
  - [x] Align workflows with optimized Docker process
  - [x] Implement GitHub Actions (GHA) build caching
- [x] Implement `Security & Maintenance Automation`
  - [x] Configure `Dependabot` for dependency updates
  - [x] Implement `CodeQL` for static analysis
  - [x] Implement `OWASP Dependency Check` scanning
  - [x] Implement `Trivy` vulnerability scanner
- [x] Implement `Health Check` and monitoring endpoints
