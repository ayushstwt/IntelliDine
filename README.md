# IntelliDine

> **IntelliDine** is an enterprise-grade, AI-powered food ordering and restaurant management ecosystem built on a reactive microservices architecture using Java 21 LTS, Spring Boot 3.5.0, Spring Cloud, Apache Kafka, and Azure OpenAI / Spring AI.

---

## Table of Contents

- [Architecture Overview](#architecture-overview)
- [Microservices Ecosystem](#microservices-ecosystem)
- [Technology Stack](#technology-stack)
- [AI Integration Architecture & Demonstration](#ai-integration-architecture--demonstration)
  - [AI Architecture & Request Flow](#ai-architecture--request-flow)
  - [RAG & Tool Calling Pipeline](#rag--tool-calling-pipeline)
  - [API Demonstration: Chat & Recommendations](#api-demonstration-chat--recommendations)
  - [Multi-Environment AI Model Strategy](#multi-environment-ai-model-strategy)
- [Multi-Environment Configuration](#multi-environment-configuration)
- [Prerequisites](#prerequisites)
- [Getting Started & Local Setup](#getting-started--local-setup)
  - [1. Configure Environment Variables](#1-configure-environment-variables)
  - [2. Start Infrastructure (Databases & Kafka)](#2-start-infrastructure-databases--kafka)
  - [3. Build & Run Microservices](#3-build--run-microservices)
- [Event-Driven Flow (Kafka)](#event-driven-flow-kafka)
- [Database Migrations](#database-migrations)
- [Security & Authentication](#security--authentication)
- [Project Structure](#project-structure)
- [License](#license)

---

## Architecture Overview

```mermaid
graph TD
    Client[Client / Web / Mobile App] --> Gateway[API Gateway :8765]
    
    Gateway --> Eureka[Eureka Registry :8761]
    Gateway --> UserService[User & Auth Service :8081]
    Gateway --> RestaurantService[Restaurant Service :8082]
    Gateway --> OrderService[Order Service :8083]
    Gateway --> PaymentService[Payment Service :8084]
    Gateway --> AIService[AI Assistant Service :9093]

    UserService --> DB1[(PostgreSQL :5432 - users_db)]
    RestaurantService --> DB2[(PostgreSQL :5433 - restaurants_db)]
    PaymentService --> DB3[(PostgreSQL :5434 - payments_db)]
    OrderService --> DB4[(PostgreSQL :5435 - orders_db)]

    PaymentService -- "Publishes Payment Events" --> Kafka[Kafka Cluster :19092, :29092, :39092]
    Kafka -- "Consumes Order Status Updates" --> OrderService
    AIService --> AzureOpenAI[Azure OpenAI / Spring AI]
```

---

## Microservices Ecosystem

| Microservice | Port | Description | Database / Store |
|---|---|---|---|
| **`registry-service`** | `8761` | Netflix Eureka Service Discovery & Registry server. | In-memory |
| **`api-gateway`** | `8765` | Reactive Spring Cloud Gateway (WebFlux) routing requests to downstream services. | — |
| **`user-service`** | `8081` | Authentication & User Management (JWT with RSA asymmetric keys, RBAC). | PostgreSQL (`users_db` :5432) |
| **`restaurant-service`** | `8082` | Restaurant profiles, categories, menus, and item catalog management. | PostgreSQL (`restaurants_db` :5433) |
| **`order-service`** | `8083` | Order lifecycle, scheduling, state machines, and Kafka consumer. | PostgreSQL (`orders_db` :5435) |
| **`payment-service`** | `8084` | Payment processing, transaction ledger, and Kafka event publisher. | PostgreSQL (`payments_db` :5434) |
| **`ai-service`** | `9093` | AI-driven dietary suggestions, dynamic recommendations, and chatbot assistants. | Azure OpenAI (`gpt-4o-mini` / `gpt-4o`) |
| **`common-library`** | — | Shared cross-cutting components (`ApiResponse<T>`, `PageResponse<T>`, `ErrorResponse`, `GlobalExceptionHandler`). | — |

---

## Technology Stack

- **Core Framework**: Java 21 LTS, Spring Boot 3.5.0, Spring Cloud 2025.0.0
- **Service Discovery & Gateway**: Netflix Eureka, Spring Cloud Gateway (Reactive WebFlux)
- **Database & Persistence**: PostgreSQL 17 (Independent database per microservice), Spring Data JPA / Hibernate, Flyway Migrations
- **Event Streaming & Messaging**: Apache Kafka (3-broker Confluent cluster) with Zookeeper
- **AI Integration**: Spring AI, Azure OpenAI (GPT-4o / GPT-4o-mini)
- **Security**: OAuth2 Resource Server, JWT (Nimbus with RSA asymmetric key pairs)
- **Containerization**: Docker & Docker Compose

---

## AI Integration Architecture & Demonstration

The `ai-service` provides natural language food recommendations, dietary assistance, and contextual search by integrating **Spring AI**, **Azure OpenAI**, **Retrieval-Augmented Generation (RAG)**, and **AI Tool Calling**.

### AI Architecture & Request Flow

```mermaid
sequenceDiagram
    autonumber
    actor User as User / Client
    participant GW as API Gateway (:8765)
    participant AI as AI Service (:9093)
    participant RAG as RestaurantRagService
    participant Tool as RestaurantCatalogTool
    participant LLM as Azure OpenAI (Spring AI)

    User->>GW: POST /ai/chat (JWT Bearer Token)
    GW->>AI: Forward request (lb://ai-service/ai/chat)
    AI->>RAG: retrieveSimilarContext(query)
    RAG-->>AI: Matched RAG Documents (Policies, dietary guidelines, scores)
    AI->>Tool: searchRestaurants(query)
    Tool-->>AI: Live catalog data (Restaurants, ratings, top dishes)
    AI->>LLM: Synthesize prompt (User query + RAG context + Tool outputs)
    LLM-->>AI: Formatted AI recommendation & reasoning
    AI-->>GW: AiChatResponse (reply, conversationId, sources, toolsUsed, token usage)
    GW-->>User: Standardized ApiResponse<AiChatResponse>
```

### RAG & Tool Calling Pipeline

1. **RAG Vector Search (`RestaurantRagService`)**:
   - Queries knowledge base for platform-specific policies (e.g., 30-minute delivery guarantees, certified dietary categories like Vegan, Gluten-Free, Halal).
   - Assigns relevance similarity scores (`score: 0.92`) to ground the model and eliminate hallucinations.

2. **Function Execution / Tool Calling (`RestaurantCatalogTool`)**:
   - Programmatically retrieves live restaurant catalog data including dish names, cuisines, customer ratings, and active restaurant partners.

3. **Model Synthesis (`AiAssistantService`)**:
   - Synthesizes user intent, RAG policy constraints, and live catalog search results into an actionable recommendation with conversation session tracking (`conversationId`).

### API Demonstration: Chat & Recommendations

#### Request

```http
POST http://localhost:8765/ai/chat
Content-Type: application/json
Authorization: Bearer <JWT_ACCESS_TOKEN>

{
  "userId": "user-101",
  "conversationId": "conv-8832-4192",
  "message": "Can you recommend top-rated spicy Indian dishes with fast delivery?"
}
```

#### Response

```json
{
  "success": true,
  "message": "AI response generated successfully",
  "data": {
    "reply": "Based on IntelliDine's restaurant network, here are the top recommendations for 'Can you recommend top-rated spicy Indian dishes with fast delivery?': Spicy Symphony (Rating: 4.8). Key dishes: Hyderabadi Chicken Biryani, Paneer Butter Masala, Garlic Naan. Delivery Policy: IntelliDine guarantees 30-minute delivery from top-rated restaurants with contactless packaging.",
    "conversationId": "conv-8832-4192",
    "sources": [
      "doc-faq-1",
      "doc-policy-2"
    ],
    "toolsUsed": [
      "RestaurantCatalogTool.searchRestaurants",
      "RestaurantRagService.retrieveSimilarContext"
    ],
    "tokensUsed": 185,
    "timestamp": "2026-09-02T10:15:30.124Z"
  },
  "path": "/ai/chat",
  "traceId": "c4b8109d-83b6-47b2-bb79-9f7cb63a921d"
}
```

### Multi-Environment AI Model Strategy

| Parameter | Local / Dev Environment | Staging Environment | Production Environment |
|---|---|---|---|
| **Model** | `gpt-4o-mini` | `gpt-4o` | `gpt-4o` |
| **Endpoint** | `https://intellidine-ai-local.openai.azure.com/` | `https://intellidine-ai-stag.openai.azure.com/` | `https://intellidine-prod.openai.azure.com/` |
| **Temperature** | `0.7` (Creative exploration) | `0.5` | `0.3` (Deterministic & accurate) |
| **Max Tokens** | `1000` | `1500` | `2000` |
| **Timeout** | `30000 ms` | `45000 ms` | `60000 ms` |

---

## Multi-Environment Configuration

IntelliDine supports 4 distinct execution environments:

| Environment | Spring Profile | Target | Database Strategy | AI Model |
|---|---|---|---|---|
| **Local** | `local` | Local Machine / Docker | Local PostgreSQL instances (`5432-5435`), `show-sql: true` | `gpt-4o-mini` |
| **Development** | `dev` | Shared Dev Cloud | Dev DB cluster (`dev-postgres.intellidine.local`) | `gpt-4o-mini` |
| **Staging** | `stag` | Pre-production Staging Cluster | Staging DB cluster with SSL, `ddl-auto: validate` | `gpt-4o` |
| **Production** | `prod` | High-Availability Cluster | Prod HA cluster with SSL & HikariCP pooling (50 max connections) | `gpt-4o` |

Environment templates:
- `.env.example` — Master environment template
- `.env.local` — Local machine & Docker configurations
- `.env.dev` — Development environment variables
- `.env.stag` — Staging environment variables
- `.env.prod` — Production environment variables

---

## Prerequisites

Ensure you have the following installed on your machine:
- **Java 21 LTS** or later (`java -version`)
- **Maven 3.9+** (`mvn -version`)
- **Docker & Docker Compose** (`docker compose version`)
- **Git**

---

## Getting Started & Local Setup

### 1. Configure Environment Variables

Copy `.env.example` to `.env.local` (or configure your shell environment):
```bash
cp .env.example .env.local
```

### 2. Start Infrastructure (Databases & Kafka)

From the project root, launch PostgreSQL instances and Kafka cluster:

```bash
# 1. Start PostgreSQL Databases (Ports: 5432, 5433, 5434, 5435)
docker-compose -f infrastructure/docker-compose-postgres.yml up -d

# 2. Start Zookeeper & Kafka 3-Broker Cluster
docker-compose -f infrastructure/kafka_cluster.yml up -d
```

### 3. Build & Run Microservices

Build all microservices and install `common-library`:
```bash
# Install common-library first
mvn -pl common-library clean install

# Build all microservices
mvn clean package -DskipTests
```

Start the services in the following order:

```bash
# 1. Service Registry (Eureka)
mvn -pl registry-service spring-boot:run

# 2. Core Business Services
mvn -pl user-service spring-boot:run
mvn -pl restaurant-service spring-boot:run
mvn -pl payment-service spring-boot:run
mvn -pl order-service spring-boot:run
mvn -pl ai-service spring-boot:run

# 3. API Gateway
mvn -pl api-gateway spring-boot:run
```

> **Note:** By default, services run under the `local` profile. To run with another profile, pass `-Dspring-boot.run.profiles=<profile>` (e.g., `-Dspring-boot.run.profiles=dev`).

---

## Event-Driven Flow (Kafka)

1. A customer places an order via the `order-service`.
2. The payment transaction is initiated via `payment-service`.
3. Upon payment completion/failure, `payment-service` publishes payment event messages to Kafka topic: `payment-events`.
4. `order-service` consumes events via `OrderServiceKafkaListener` and transitions the order status (`CONFIRMED`, `FAILED`, `CANCELLED`).
5. `OrderStatusSchedulerProcessing` schedules periodic checks to reconcile lingering orders.

---

## Database Migrations

Each microservice manages its own schema independently using **Flyway**:
- `user-service`: `db/migration/V1__create_roles_table.sql`, `V2__create_users_table.sql`, `V3__add_create_at_column.sql`
- `restaurant-service`: `db/migration/V1__create_restaurants_and_restaurantmenus_table.sql`

Migrations run automatically upon application startup.

---

## Security & Authentication

- Asymmetric RSA Public/Private Key authentication via Spring Security and Nimbus JOSE.
- Public/Private keys are located under `user-service/src/main/resources/certs/` or configured via `RSA_PRIVATE_KEY` / `RSA_PUBLIC_KEY` environment variables.
- Passwords are encrypted using BCrypt.

---

## Project Structure

```
IntelliDine/
├── ai-service/              # Spring AI & Azure OpenAI integrations
├── api-gateway/             # Spring Cloud Gateway (Reverse proxy & routing)
├── common-library/          # Shared DTOs, responses, and exception handlers
├── infrastructure/          # Docker compose files for Postgres & Kafka
├── order-service/           # Order placement, status tracking & schedulers
├── payment-service/         # Payment processing & Kafka event publisher
├── registry-service/        # Netflix Eureka Service Discovery server
├── restaurant-service/      # Restaurants & Menu catalog management
├── user-service/            # Authentication, JWT, and User management
├── .env.example             # Environment variables template
├── .gitignore               # Git ignore configuration
└── README.md                # Project documentation
```

---

## License

This project is licensed under the MIT License.
