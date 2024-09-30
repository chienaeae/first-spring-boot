# First Spring Boot

The project is a Spring Boot practice project designed to implement common Spring Boot features and JWT-based RBAC (Role-Based Access Control).

## Getting Started

### Prerequisites
- Java
- Maven
- Docker (to set up MySQL database)

### Setting Up MySQL Database

To set up a MySQL database using Docker, run the following command:

```bash
docker-compose -f ./dev-support/docker-compose.yaml up -d
```

### Running the Application

To run the application, use the following command:

```bash
mvn clean install // to build the project

mvn spring-boot:run // to run the application
```

## Folder Structure

### `config` Folder

`config` folder stores configuration classes and settings. These could include application configurations, beans, and other environment-specific configurations.

### `controller` Folder

`controller` folder contains the web controllers that handle HTTP requests, process user inputs, and return the appropriate responses. Typically part of the MVC (Model-View-Controller) structure.

### `dto` Folder

`dto` folder Holds DTO classes, which are used to transfer data between layers or services without exposing the actual entity models. DTOs are typically used to define input/output formats for APIs.

### `entity` Folder

`entity` folder contains entity classes that represent database tables. These classes are usually annotated with @Entity and define the structure of data persisted in the database.

### `exception` Folder

`exception` stores custom exception classes that handle specific error scenarios.

### `model` Folder

`model` folder holds domain models, which represent the business logic and core concepts of the application. These models are often used within the service layer and could include plain Java objects (POJOs) or business logic classes.

### `repository` Folder

`repository` folder contains repository interfaces or classes that interact with the database. Typically, these interfaces extend Spring Data’s JpaRepository or other data access frameworks for CRUD operations.

### `security` Folder

`security` folder stores security-related classes, such as configuration classes, filters, and authentication providers. These classes are used to secure the application and handle user authentication and authorization.

### `serialization` Folder

`serialization` folder contains classes that handle serialization and deserialization of objects, such as converting Java objects to JSON or XML formats.

### `service` Folder

`service` folder holds service classes that contain business logic and handle application-specific operations. These classes interact with repositories, perform data processing, and implement the core functionality of the application.

### `util` Folder

`util` folder stores utility classes that provide common functionalities or helper methods used across the application.

## Contact

Author: [PhillyChien](https://github.com/PhillyChien)

