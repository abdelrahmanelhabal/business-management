# Business Management System

A Spring Boot-based business and tea retail application for managing products, users, admins, and storefront operations. The project combines a customer-facing tea catalog with an administrative panel for managing business data efficiently.

## Overview

This application is designed for a tea brand and retail business where customers can:

- browse the homepage and product collection
- view business location details
- register as a user or admin
- place product orders through the storefront flow

For administrators, the platform includes functionality for:

- managing products
- handling users and admins
- updating store data through the dashboard
- maintaining business operations from a web interface

## Tech Stack

| Category | Technology | Why it is used |
| --- | --- | --- |
| Language | Java 17 | Main backend programming language used for the application logic. |
| Framework | Spring Boot 3.1 | Simplifies web app setup, dependency injection, and REST MVC development. |
| Web Layer | Spring MVC | Handles routing, controllers, and page rendering for the storefront and admin pages. |
| Template Engine | Thymeleaf | Renders dynamic HTML pages for the frontend views. |
| Data Access | Spring Data JPA | Manages database entities, repositories, and persistence operations. |
| Database | MySQL | Stores products, users, admins, and business-related data. |
| Build Tool | Maven | Builds and packages the Java application. |
| Containerization | Docker | Packages the app into a portable container for deployment and testing. |
| Orchestration | Docker Compose | Runs the app and MySQL together in a local multi-container setup. |
| CI/CD | GitHub Actions | Automates Docker image build and push on code changes. |

## Key Features

- ecommerce-style product catalog
- responsive storefront design
- admin dashboard and management pages
- user and admin registration flow
- location page with embedded map
- Dockerized deployment
- versioned image publishing workflow

## Screenshots

### Home Page

![Home page preview](docs/screenshots/home%20page.png)

### Collection Page

![Collection page preview](docs/screenshots/collection%20page.png)

### Location Page

![Location page preview](docs/screenshots/location%20page.png)

## Project Structure

```text
business-management-system/
├── .github/
│   └── workflows/
│       └── build.yml
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   │       ├── static/
│   │       └── templates/
│   └── test/
├── docs/
│   └── screenshots/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── README.md
├── .env.example
├── mvnw
├── mvnw.cmd
└── .gitignore
```

## Run Locally

### Prerequisites

- Java 17+
- Maven
- Docker
- Docker Compose
- MySQL or the provided Docker setup

### Option 1: Run with Maven

```bash
./mvnw spring-boot:run
```

Then open:

```text
http://localhost:2330
```

### Option 2: Run with Docker

```bash
docker build -t business-management-system .
docker run --rm -p 2330:2330 --env-file .env business-management-system
```

### Option 3: Run with Docker Compose

This is the recommended setup for local development because it starts both the application and MySQL in the same environment.

```bash
docker compose up --build
```

To stop the environment:

```bash
docker compose down
```

To remove the database volume as well:

```bash
docker compose down -v
```

The application is available at:

```text
http://localhost:2330
```

## Environment Variables

Create a `.env` file from `.env.example` and configure the database values before starting the application.

```env
SPRING_DATASOURCE_NAME=business_db
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/business_db
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=your_password
SERVER_PORT=2330
MYSQL_ROOT_PASSWORD=your_password
```

## Docker

The project includes a Dockerfile and Docker Compose configuration for building and running the application with MySQL.

### Build image manually

```bash
docker build -t business-management-system .
```

### Run image manually

```bash
docker run --rm -p 2330:2330 --env-file .env business-management-system
```

### Docker Compose behavior

The Compose file defines:

- `mysql`: a MySQL 8 service
- `app`: the Spring Boot application container

The application waits for MySQL health checks before starting, ensuring the database is ready.

## GitHub Actions Workflow

The workflow in `.github/workflows/build.yml` automates the release process whenever code is pushed to the `main` branch.

### What the workflow does

1. Checks out the repository code.
2. Reads the current version from the repository variable `APP_VERSION`.
3. Increments the version number.
4. Logs in to Docker Hub using repository secrets.
5. Builds the Docker image for the app.
6. Tags the image with both the new version and `latest`.
7. Pushes the image to Docker Hub.
8. Updates the `APP_VERSION` variable so the next release continues from the latest value.

### Example build step

```yaml
- name: Build Docker image
  run: |
    docker build \
      -t ${{ secrets.DOCKERHUB_USERNAME }}/business-management-system:${VERSION} \
      -t ${{ secrets.DOCKERHUB_USERNAME }}/business-management-system:latest \
      .
```

This ensures each successful `main` branch push creates a versioned production-ready Docker image.