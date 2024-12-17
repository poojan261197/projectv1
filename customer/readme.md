# Customer API - Spring Boot Application

This is a simple Spring Boot application that provides a CRUD API for managing customer data. It uses Spring Data JPA with an H2 in-memory database and exposes Prometheus metrics for monitoring. The API is designed for easy integration into containerized environments like Docker and Kubernetes.

## Prerequisites

Before you begin, ensure you have the following installed:

- **Java 17** or later (required for running the application)
- **Maven** (for building the project)
- **Docker** (for containerizing the application)
- **Kubernetes** (optional, for deploying to a Kubernetes cluster)

### System Requirements
- **Operating System**: Linux, macOS, or Windows (with WSL for Windows)
- **RAM**: 2GB minimum
- **CPU**: Any modern processor

## How to Get Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-repository/customer-api.git
cd customer-api
```


### 2. Dockerizing the Application

```dockerfile
docker build -t customer-api .
```

```dockerfile
docker run -p 8080:8080 customer-api
```
This will start the application inside a Docker container and expose it on http://localhost:8080.

### 3. Kubernetes Deployment

To deploy the application to Kubernetes, run kubernetes/deployment.yaml in codebase using following command
```
kubectl apply -f deployment.yaml
```
### 4. Prometheus and Monitoring
Prometheus Metrics: Available at http://localhost:8080/actuator/prometheus
Health and Info Endpoints: Available at http://localhost:8080/actuator/health and http://localhost:8080/actuator/info

