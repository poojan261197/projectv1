# CI/CD Pipeline for Customer API Application

## Overview
This document outlines the Continuous Integration (CI) and Continuous Deployment (CD) pipeline for the `customer-api` Spring Boot application. The pipeline automates building, testing, and deploying the application from development to production.

### Tools Used:
- **CI/CD Orchestration**: GitHub Actions / Jenkins / GitLab CI
- **Build Tool**: Maven
- **Static Code Analysis**: SonarQube / Checkstyle
- **Containerization**: Docker
- **Container Registry**: Docker Hub / AWS ECR / Google GCR
- **Orchestration**: Kubernetes (via Helm or kubectl)
- **Testing**: JUnit, Postman, Integration Tests

## CI Pipeline Stages

### 1. **Code Commit & Version Control**
- Developers commit code to the Git repository (e.g., GitHub).
- Pull requests (PRs) are reviewed before merging into the `main` or `dev` branch.

### 2. **Continuous Integration (CI)**

#### 2.1 **Build & Test**
- **Trigger**: On every push to `main` or `dev` branch.
- **Tasks**:
    - Run `mvn clean install` to compile and run unit tests.
    - Run static code analysis using SonarQube (if configured).

#### 2.2 **Dependency Management & Artifact Storage**
- **Task**: Resolve dependencies with `mvn dependency:go-offline`.
- **Artifact**: The build artifact (`.jar`) is stored in a Maven repository.

#### 2.3 **Static Code Analysis (Optional)**
- **Task**: Run static analysis tools (SonarQube, Checkstyle).
- **Gate**: If the code quality is below the threshold, the pipeline fails.

## CD Pipeline Stages

### 3. **Build & Deploy Docker Image**

#### 3.1 **Build Docker Image**
- **Task**: If the build passes, use Docker to package the Spring Boot application.
- **Docker Command**:
  ```bash
  docker build -t customer-api:latest .
