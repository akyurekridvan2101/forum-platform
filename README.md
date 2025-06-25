## Forum Platform – Modular Java Backend Architecture

This is a scalable and maintainable backend architecture for a forum system, built using **Spring Boot** and divided into well-structured, domain-driven modules. It supports **PostgreSQL**, **MongoDB**, and **Redis**, and can be extended to include messaging systems like **RabbitMQ** or **Kafka**.

---

### Project Structure Overview

```
graphql
CopyEdit
forum-platform/
├── pom.xml                        # Root parent pom
├── docker/                        # Docker configurations (PostgreSQL, MongoDB, Redis)
├── docs/                          # Documentation files (ER diagrams, schema design)
├── forum-api/                    # API layer with REST controllers
├── forum-application/            # Application service layer (business logic)
├── forum-domain/                 # Core domain models (entities, enums, interfaces)
├── forum-dto/                    # External-facing data transfer objects (DTOs)
├── forum-mapper/                 # Mapping logic between DTOs and domain models
├── forum-persistence-pgsql/      # JPA repositories and entities for PostgreSQL
├── forum-persistence-mongo/      # MongoDB documents and repositories
├── forum-cache-redis/            # Redis caching configuration and access
├── forum-messaging/              # Messaging integrations (RabbitMQ, Kafka)

```

---

### Module Responsibilities

### 1. `forum-api`

- Handles all HTTP interactions.
- Contains REST controllers and exception handlers.
- Delegates requests to the service layer in `forum-application`.

### 2. `forum-application`

- Encapsulates core business logic.
- Coordinates access to persistence, caching, and messaging modules.
- Contains `@Service`annotated classes and orchestrates use cases.

### 3. `forum-domain`

- Pure domain model classes (e.g. entities, value objects, enums).
- No dependencies on Spring or infrastructure.
- Shared across all modules for consistency and type safety.

### 4. `forum-dto`

- Holds request/response classes exchanged with clients.
- Used only at the boundaries (API layer).
- Immutable classes, designed for safe external exposure.

### 5. `forum-mapper`

- Contains mappers to convert between `domain` and `dto` models.
- Typically uses MapStruct or manual mapping classes.
- Keeps API layer decoupled from internal models.

### 6. `forum-persistence-pgsql`

- JPA entities and Spring Data `JpaRepository` interfaces.
- PostgreSQL is used to store relational metadata like users, topics, reactions.
- Queries and schema design reflect normalized relational structures.

### 7. `forum-persistence-mongo`

- MongoDB `@Document` models and repositories.
- Suitable for nested, rich content such as comments or post bodies.
- Non-relational, flexible schema design.

### 8. `forum-cache-redis`

- Integrates Redis for caching and fast in-memory access.
- Used for features like view counters, user sessions, rate-limiting.

### 9. `forum-messaging`

- Manages communication with asynchronous systems (RabbitMQ, Kafka).
- Publishes and listens to domain events.
- Used for background processing or event-driven notifications.

### 10. `docker`

- Contains `docker-compose.yml` for setting up development containers.
- Services: PostgreSQL, MongoDB, Redis, optional RabbitMQ.

### 11. `docs`

- Database schemas, diagrams, architecture decisions, and design notes.

---

### Module Relationships and Dependencies

- `forum-api` depends on:
    - `forum-dto`
    - `forum-application`
- `forum-application` depends on:
    - `forum-domain`
    - `forum-dto`
    - `forum-mapper`
    - `forum-persistence-*`
    - `forum-cache-redis` (optional)
    - `forum-messaging` (optional)
- `forum-mapper` depends on:
    - `forum-dto`
    - `forum-domain`
- `forum-persistence-pgsql` and `forum-persistence-mongo` depend on:
    - `forum-domain`

---

### Contribution & Branching Guidelines

- Use `feature/<feature-name>` for new work.
- All changes should go through pull requests into `main`.
- Keep domain models clean and agnostic of infrastructure.
- PostgreSQL stores metadata, MongoDB stores content, Redis caches hot data.

---

### Example Use Case

- A `POST /comments` request hits the controller in `forum-api`.
- It passes a `CommentCreateRequest` DTO to a service in `forum-application`.
- The service uses mappers to convert DTO into a domain model.
- Then it delegates to:
    - `forum-persistence-pgsql` to store metadata (e.g., user ID, timestamp).
    - `forum-persistence-mongo` to store comment body and thread structure.
- Redis is updated with the new comment count.
- A RabbitMQ event is published for notification.
