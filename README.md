# Store Showcase

A cloud-native microservices demo application designed for **Red Hat Developer Hub (RHDH)** and **OpenShift**. This project demonstrates a complete end-to-end workflow including application scaffolding, GitOps-based deployments with ArgoCD, and CI/CD pipelines with Tekton.

## Architecture Overview

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              Store Showcase                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   ┌──────────────┐      ┌──────────────┐      ┌──────────────────┐         │
│   │   Frontend   │─────▶│Orders Backend│─────▶│   Store Backend  │         │
│   │   (React)    │      │  (Quarkus)   │      │    (Quarkus)     │         │
│   └──────────────┘      └──────┬───────┘      └────────┬─────────┘         │
│                                │                       │                    │
│                                │    Kafka Topic        │                    │
│                                │   (orders-out)        │                    │
│                                ▼                       ▼                    │
│                         ┌─────────────────────────────────┐                 │
│                         │         Apache Kafka            │                 │
│                         │        (Strimzi AMQ)            │                 │
│                         └─────────────────────────────────┘                 │
│                                                                             │
│                         ┌─────────────────────────────────┐                 │
│                         │         PostgreSQL              │                 │
│                         │        (Orders DB)              │                 │
│                         └─────────────────────────────────┘                 │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

## Components

### Application Services

| Component | Technology | Description |
|-----------|------------|-------------|
| **store-frontend** | React 19, TypeScript | Customer-facing web application |
| **orders-backend** | Quarkus 3.15, Java 23 | Handles order creation and publishes events to Kafka |
| **store-backend** | Quarkus 3.15, Java 23 | Consumes orders from Kafka and persists to PostgreSQL |
| **spring-store-project** | Spring Boot 2.7 | Alternative Spring-based backend implementation |

### Infrastructure

| Component | Technology | Description |
|-----------|------------|-------------|
| **Kafka** | Strimzi / AMQ Streams | Event streaming platform |
| **PostgreSQL** | PostgreSQL 14 | Relational database for order persistence |

### Platform & DevOps

| Component | Technology | Description |
|-----------|------------|-------------|
| **RHDH Template** | Backstage Scaffolder | Golden path template for application scaffolding |
| **GitOps** | ArgoCD + Helm | Declarative deployment management |
| **CI/CD Pipelines** | Tekton | Cloud-native CI/CD pipelines |
| **Container Registry** | OpenShift / Quay | Container image storage |

## Project Structure

```
store-showcase-in-progress/
├── local_dev_tool/                 # Local development helper scripts
│   ├── create_kafka.sh             # Spin up Kafka locally
│   └── create_postgres.sh          # Spin up PostgreSQL locally
├── pom.xml                         # Parent Maven POM
├── rhdh/
│   ├── template.yaml               # RHDH Backstage template definition
│   ├── manifest/
│   │   └── argocd/                 # ArgoCD & Helm configurations
│   │       ├── orders-config/      # Orders backend deployment
│   │       ├── store-config/       # Store backend deployment
│   │       ├── store-frontend-config/  # Frontend deployment
│   │       ├── store-common-config/    # Shared infrastructure (Kafka, DB)
│   │       └── spring-store-config/    # Spring variant deployment
│   └── skeleton/                   # Application skeletons
│       ├── orders-backend/         # Quarkus orders service
│       ├── store-backend/          # Quarkus store service
│       ├── store-frontend/         # React frontend
│       ├── spring-store-project/   # Spring Boot variant
│       ├── quarkus-pipeline/       # Tekton pipeline for Quarkus apps
│       └── node-pipeline/          # Tekton pipeline for Node.js apps
└── README.md
```

## Quick Start

### Prerequisites

- **Podman** or **Docker**
- **Java 23** (for Quarkus backends)
- **Node.js 18+** (for frontend)
- **Maven 3.9+**

### Local Development

1. **Start infrastructure services:**

```bash
# Start PostgreSQL
cd local_dev_tool
./create_postgres.sh

# Start Kafka (in a separate terminal)
./create_kafka.sh
```

2. **Run the Orders Backend:**

```bash
cd rhdh/skeleton/orders-backend
./mvnw quarkus:dev
```

3. **Run the Store Backend:**

```bash
cd rhdh/skeleton/store-backend
./mvnw quarkus:dev
```

4. **Run the Frontend:**

```bash
cd rhdh/skeleton/store-frontend
npm install
npm start
```

### Building for Production

```bash
# Build all modules
mvn clean package

# Build native executables (Quarkus)
cd rhdh/skeleton/orders-backend
./mvnw package -Pnative
```

## Deploying to OpenShift

### Using RHDH Template

1. Access Red Hat Developer Hub
2. Navigate to **Create** → **Templates**
3. Select **Store Showcase**
4. Fill in the required parameters:
    - Component names
    - Database configuration
    - Kafka configuration
    - Image registry settings
    - Git repository information

The template will:
- Scaffold the application repositories
- Create GitOps configuration repositories
- Set up ArgoCD applications
- Configure Tekton pipelines

### Manual Deployment

1. **Deploy infrastructure:**

```bash
oc apply -f rhdh/manifest/argocd/store-common-config/
```

2. **Deploy applications:**

```bash
oc apply -f rhdh/manifest/argocd/orders-config/argocd/
oc apply -f rhdh/manifest/argocd/store-config/argocd/
oc apply -f rhdh/manifest/argocd/store-frontend-config/argocd/
```

## Configuration

### Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `KAFKA_BOOTSTRAP_SERVERS` | `localhost:9092` | Kafka broker address |
| `QUARKUS_DATASOURCE_JDBC_URL` | `jdbc:postgresql://localhost:5432/ordersdb` | Database connection URL |
| `QUARKUS_DATASOURCE_USERNAME` | `ordersuser` | Database username |
| `QUARKUS_DATASOURCE_PASSWORD` | `orderspassword` | Database password |

### Kafka Topics

| Topic | Description |
|-------|-------------|
| `orders-out` | Order events published by orders-backend |

## Features

- **OpenTelemetry** - Distributed tracing enabled
- **Prometheus Metrics** - Micrometer metrics exposed at `/q/metrics`
- **Health Checks** - Liveness and readiness probes at `/q/health`
- **OpenAPI** - API documentation at `/q/openapi`
- **Native Compilation** - GraalVM native image support

## API Endpoints

### Orders Backend

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/orders` | List all orders |
| `POST` | `/orders` | Create a new order |
| `GET` | `/q/health` | Health check |
| `GET` | `/q/metrics` | Prometheus metrics |

### Order Payload Example

```json
{
  "description": "Wireless Mouse",
  "id": 1,
  "quantity": 5,
  "itemCategory": "electronics"
}
```

## License

This project is licensed under the Apache License 2.0.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

