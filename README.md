# Java Spring Boot Fraud Detection System

[Türkçe Belge İçin Tıklayın (Read in Turkish)](README.tr.md)

This microservice acts as a real-time fraud detection engine. It consumes transaction events from an Apache Kafka topic, processes them through a custom business rule engine, and persists the analysis results to a PostgreSQL database.

## Architecture & Technologies
- **Java 17 & Spring Boot 3:** Core framework.
- **Apache Kafka:** Event streaming and asynchronous processing.
- **Spring Data JPA & PostgreSQL:** Relational data persistence and account status tracking.
- **Docker Compose:** Containerized infrastructure deployment.

## Business Rules
An account is flagged as fraudulent if:
1. It attempts to process a transaction exceeding the maximum allowed amount.
2. It attempts to process more than the allowed number of transactions within a single minute (velocity check).

Once an account is flagged, all subsequent transactions from that account are automatically rejected.

## Installation & Running

1. Start Infrastructure:
   ```bash
   docker-compose up -d
   ```
2. Build and Run the Application:
   ```bash
   ./mvnw spring-boot:run
   ```

## Testing
Send a mock transaction to the internal test endpoint to publish a message to Kafka:
```bash
curl -X POST http://localhost:8082/api/test/publish \
     -H "Content-Type: application/json" \
     -d '{"accountId": "ACC-100", "amount": 150.00}'
```
Send the same command 4 times rapidly to trigger the velocity rule and observe the database flag the account as fraudulent.
