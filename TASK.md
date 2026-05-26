# ERP Core - Project Roadmap & Task List

This file summarizes the development lifecycle of the Enterprise ERP project. Tasks are derived from the [DATABASE_DESIGN.md](docs/DATABASE_DESIGN.md) and [PROJECT_STRUCTURE.md](docs/PROJECT_STRUCTURE.md) specifications.

---

## Phase 0: Foundations & Shared Kernel

Standardization of common utilities and base classes required globally across modules.

- [x] **Infrastructure Configuration**
  - [x] Initialize `Project Structure` (`src/main/java/com/learn/erp_core/`)
  - [x] Implement `BaseEntity` (`shared/entity/BaseEntity.java`)
  - [x] Implement `Global Exception Handling` mechanism (`shared/exception/GlobalExceptionHandler.java`)
- [x] **Security & Authentication Infrastructure**
  - [x] Configure Spring Security with JWT (`auth/adapter/out/security/WebSecurityConfig.java`, etc.)
  - [x] Implement `PasswordHashing` service with BCrypt (`auth/adapter/out/security/BCryptPasswordAdapter.java`)

---

## Phase 1: User & Authentication Modules (`user`, `auth`)

Development of authentication, authorization, and user profile management.

- [x] **Domain Layer**
  - [x] Define `User`, `Role`, and `Permission` domain models (`user/domain/model/`)
  - [x] Define Repository port interfaces (`user/domain/repository/`)
- [/] **Application Layer**
  - [x] Implement User, Role, and Permission CRUD Use Cases (`user/application/service/`)
  - [/] Implement `RegisterUserUseCase` and `LoginUseCase`
    - [x] Implement `RegisterUseCase` (`auth/application/service/RegisterService.java`)
    - [ ] Implement `LoginUseCase` (`auth/application/service/LoginService.java`)
  - [ ] Implement `Refresh Token Rotation` mechanism (`auth/application/service/`)
  - [x] Develop User, Role, and Permission DTOs (`user/application/dto/`)
- [/] **Infrastructure Layer**
  - [x] Implement `UserJpaEntity`, `RoleEntity`, and `PermissionEntity` persistence mapping (`user/adapter/out/persistence/entity/`)
  - [x] Develop persistence adapter implementations (`user/adapter/out/persistence/repository/`)
  - [x] Implement User, Role, and Permission REST endpoints (`user/adapter/in/web/`)
  - [ ] Implement `AuthController` REST endpoints (`auth/adapter/in/web/AuthController.java`)

---

## Phase 2: Catalog Module (`com.learn.erp_core.catalog`)

Product management and category hierarchical structure development.

- [ ] **Domain Layer** (`catalog/domain/`)
  - [ ] Implement `Category` domain model (`catalog/domain/model/Category.java`)
  - [ ] Implement `Product` domain model (`catalog/domain/model/Product.java`)
  - [ ] Define Repository port interfaces (`catalog/application/port/out/CategoryRepositoryPort.java`, etc.)
- [ ] **Application Layer** (`catalog/application/`)
  - [ ] Implement `CategoryService` (`catalog/application/service/CategoryService.java`)
  - [ ] Implement `ProductService` (`catalog/application/service/ProductService.java`)
- [ ] **Infrastructure Layer** (`catalog/adapter/`)
  - [ ] Implement JPA persistence mapping (`catalog/adapter/out/persistence/entity/`)
  - [ ] Develop `CategoryController` and `ProductController` REST endpoints (`catalog/adapter/in/web/`)

---

## Phase 3: Inventory Module (`com.learn.erp_core.inventory`)

Warehouse management and stock tracking system.

- [ ] **Domain Layer** (`inventory/domain/`)
  - [ ] Define `Warehouse` domain model (`inventory/domain/model/Warehouse.java`)
  - [ ] Define `Stock` domain model (`inventory/domain/model/Stock.java`)
  - [ ] Define `StockMutation` domain model (`inventory/domain/model/StockMutation.java`)
- [ ] **Application Layer** (`inventory/application/`)
  - [ ] Implement `WarehouseService` (`inventory/application/service/WarehouseService.java`)
  - [ ] Implement `StockUpdateUseCase` (`inventory/application/port/in/StockUpdateUseCase.java`)
- [ ] **Infrastructure Layer** (`inventory/adapter/`)
  - [ ] Implement JPA persistence mapping (`inventory/adapter/out/persistence/entity/`)
  - [ ] Develop `WarehouseController` REST endpoints (`inventory/adapter/in/web/WarehouseController.java`)

---

## Phase 4: Partner Module (`com.learn.erp_core.partner`)

Management of external entities including Customers and Suppliers.

- [ ] **Domain Layer** (`partner/domain/`)
  - [ ] Define `Partner` domain model (`partner/domain/model/Partner.java`)
- [ ] **Application Layer** (`partner/application/`)
  - [ ] Implement `PartnerService` (`partner/application/service/PartnerService.java`)
- [ ] **Infrastructure Layer** (`partner/adapter/`)
  - [ ] Implement `PartnerJpaEntity` persistence mapping (`partner/adapter/out/persistence/entity/PartnerEntity.java`)
  - [ ] Develop `PartnerController` REST endpoints (`partner/adapter/in/web/PartnerController.java`)

---

## Phase 5: Sales & Procurement Modules (`com.learn.erp_core.sales`, `com.learn.erp_core.procurement`)

Transaction management for Purchase and Sales orders.

- [ ] **Domain Layer** (`sales/domain/`, `procurement/domain/`)
  - [ ] Define `Order` (Sales/Purchase) and `OrderItem` domain models (`sales/domain/model/Order.java`, etc.)
  - [ ] Implement state management for Status transitions (`sales/domain/model/OrderStatus.java`)
- [ ] **Application Layer** (`sales/application/`, `procurement/application/`)
  - [ ] Implement `CreateOrderUseCase` (`sales/application/port/in/CreateOrderUseCase.java`)
  - [ ] Implement `ProcessOrderUseCase` (`sales/application/port/in/ProcessOrderUseCase.java`)
- [ ] **Infrastructure Layer** (`sales/adapter/`, `procurement/adapter/`)
  - [ ] Implement Persistence layer for Orders and Items (`sales/adapter/out/persistence/entity/`)
  - [ ] Develop Sales and Procurement Controllers REST endpoints (`sales/adapter/in/web/SalesController.java`, etc.)

---

## Phase 6: DevOps & Deployment Infrastructure

- [x] Finalize `Dockerfile` and `docker-compose.yml` configurations (`Dockerfile`, `docker-compose.yml`)
  - [x] Implement layer caching for faster builds
  - [x] Configure `.dockerignore` for context optimization (`.dockerignore`)
- [x] Configure `CI/CD` deployment pipelines (`.github/workflows/`)
  - [x] Align workflows with optimized Docker process (`.github/workflows/cd-prod.yml`, etc.)
  - [x] Implement GitHub Actions (GHA) build caching
- [x] Implement `Security & Maintenance Automation`
  - [x] Configure `Dependabot` for dependency updates (`.github/dependabot.yml`)
  - [x] Implement `CodeQL` for static analysis (config in workflows)
  - [x] Implement `OWASP Dependency Check` scanning (config in workflows)
  - [x] Implement `Trivy` vulnerability scanner (config in workflows)
- [x] Implement `Health Check` and monitoring endpoints (`com/learn/erp_core/shared/adapter/in/web/HealthCheckController.java` or Spring Actuator)
