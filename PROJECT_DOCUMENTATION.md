# IntelliDine — AI-Powered Microservices Food Ecosystem

This document provides complete documentation for the **IntelliDine** food-ordering microservices platform with multi-environment configuration (`local`, `dev`, `stag`, `prod`).

---

## 1. Technology Stack

| Concern | Technology |
|---|---|
| Language | Java 21 LTS |
| Framework | Spring Boot 3.5.0 |
| Microservices | Spring Cloud 2025.0.0 |
| Service Discovery | Netflix Eureka (`registry-service` :8761) |
| API Gateway | Spring Cloud Gateway (WebFlux) (:8765) |
| Relational Database | PostgreSQL 17 (Independent databases per microservice) |
| Async Messaging | Apache Kafka (Confluent CP 7.0.1) |
| Security | OAuth2 Resource Server + JWT (Nimbus / RSA asymmetric keys) |
| Database Migrations | Flyway (`flyway-database-postgresql`) |
| AI Integration | Spring AI & Azure OpenAI (`ai-service` :9093) |
| Common Standards | `common-library` (`ApiResponse<T>`, `ErrorResponse`, `PageResponse<T>`, `GlobalExceptionHandler`) |

---

## 2. Multi-Environment Architecture (`local`, `dev`, `stag`, `prod`)

IntelliDine supports 4 distinct environments via Spring Profiles and Environment Variables:

| Environment | Spring Profile | Target | Database Strategy | AI Model / Deployment |
|---|---|---|---|---|
| **Local** | `local` | Developer Machine / Docker Compose | Local PostgreSQL Docker instances (`5432-5435`), `show-sql: true` | Local mock/dev key (`gpt-4o-mini`) |
| **Dev** | `dev` | Shared Development Cloud / Kubernetes | Dev DB cluster (`dev-postgres.intellidine.local`), `show-sql: true` | Dev Azure OpenAI (`gpt-4o-mini`) |
| **Staging** | `stag` | Pre-production Staging Cluster | Staging DB cluster with SSL, `ddl-auto: validate`, `show-sql: false` | Staging Azure OpenAI (`gpt-4o`) |
| **Prod** | `prod` | High-Availability Production Cluster | Prod HA cluster with SSL & HikariCP pooling (50 max connections) | Prod Azure OpenAI (`gpt-4o`, low temperature) |

---

## 3. Environment Variables Reference

All database connections, ports, Eureka, Kafka, and AI settings are externalized:

### Core & Infrastructure
| Variable | Description | Default (Local) |
|---|---|---|
| `SPRING_PROFILES_ACTIVE` | Active profile (`local`, `dev`, `stag`, `prod`) | `local` |
| `EUREKA_SERVER_URL` / `DEFAULT_ZONE` | Eureka Service Registry endpoint | `http://localhost:8761/eureka/` |
| `KAFKA_BOOTSTRAP_SERVERS` | Kafka broker endpoints | `localhost:19092,localhost:29092,localhost:39092` |

### PostgreSQL Database Variables
| Variable | Description | Local Default |
|---|---|---|
| `DB_HOST` | Database host | `localhost` |
| `DB_USERNAME` | Database username | `postgres` |
| `DB_PASSWORD` | Database password | `postgrespassword` |
| `DB_POOL_MAX_SIZE` | HikariCP max pool connections | `10` (Local) / `50` (Prod) |
| `USER_DB_URL` | User Service DB connection URL | `jdbc:postgresql://localhost:5432/users_db` |
| `RESTAURANT_DB_URL` | Restaurant Service DB connection URL | `jdbc:postgresql://localhost:5433/restaurants_db` |
| `PAYMENT_DB_URL` | Payment Service DB connection URL | `jdbc:postgresql://localhost:5434/payments_db` |
| `ORDER_DB_URL` | Order Service DB connection URL | `jdbc:postgresql://localhost:5435/orders_db` |

### AI / Azure OpenAI Variables (`ai-service`)
| Variable | Description | Local / Dev Default | Production Default |
|---|---|---|---|
| `AZURE_OPENAI_ENDPOINT` | Azure OpenAI Resource Endpoint | `https://intellidine-ai-local.openai.azure.com/` | `https://intellidine-prod.openai.azure.com/` |
| `AZURE_OPENAI_API_KEY` | API Key for Azure OpenAI | Configured per environment | Production Key Vault |
| `AZURE_OPENAI_MODEL` | LLM Model Name | `gpt-4o-mini` | `gpt-4o` |
| `AZURE_OPENAI_DEPLOYMENT_NAME` | Deployment name on Azure | `gpt-4o-mini-local` | `gpt-4o-prod` |
| `AI_TEMPERATURE` | Model temperature (creativity) | `0.7` | `0.3` |
| `AI_MAX_TOKENS` | Max completion tokens | `1000` | `2000` |
| `AI_TIMEOUT_MS` | Request timeout in milliseconds | `30000` | `60000` |

---

## 4. How to Run with Different Environments

### A. Run Locally (Default)
```bash
# 1. Start Databases & Kafka
docker-compose -f infrastructure/docker-compose-postgres.yml up -d
docker-compose -f infrastructure/kafka_cluster.yml up -d

# 2. Run Services (automatically uses 'local' profile)
mvn -pl registry-service spring-boot:run
mvn -pl user-service spring-boot:run
mvn -pl restaurant-service spring-boot:run
mvn -pl payment-service spring-boot:run
mvn -pl order-service spring-boot:run
mvn -pl ai-service spring-boot:run
mvn -pl api-gateway spring-boot:run
```

### B. Run with Dev / Staging / Production Profile
Pass `-Dspring-boot.run.profiles=<profile>` or set the environment variable:

```bash
# Example: Running user-service with dev profile
mvn -pl user-service spring-boot:run -Dspring-boot.run.profiles=dev

# Example: Running ai-service with prod profile and environment variables
export SPRING_PROFILES_ACTIVE=prod
export AZURE_OPENAI_ENDPOINT="https://intellidine-prod.openai.azure.com/"
export AZURE_OPENAI_API_KEY="your-production-key"
mvn -pl ai-service spring-boot:run
```

### C. Using `.env` files
Pre-configured environment templates are located at root:
- `.env.local` — Local machine & Docker setup
- `.env.dev` — Development cloud environment
- `.env.stag` — Staging cluster with SSL & strict validation
- `.env.prod` — Production high-availability cluster
- `.env.example` — Template reference