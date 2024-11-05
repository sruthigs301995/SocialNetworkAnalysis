# Social Network Analysis Tool

This is a Spring Boot application for managing users, friendships, and performing network analysis on a social network. The application provides RESTful APIs for user management, friendship management, and network analysis functionalities.

## Features

- **User Management**: Add, remove, and list users.
- **Friendship Management**: Add, remove, and list friends for a user.
- **Network Analysis**: Find the shortest path between two users.

## Technologies Used

- Spring Boot
- Spring Data JPA
- H2 Database
- Lombok (optional)
- Maven

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven


### Setting Up the Project

1. **Clone the repository**:

    ```sh
    git clone https://github.com/your-username/social-network-analysis-tool.git
    cd social-network-analysis-tool
    ```

2. **Build the project**:

    ```sh
    mvn clean install
    ```

3. **Run the application**:

    ```sh
    mvn spring-boot:run
    ```

### Configuration

The application uses an in-memory H2 database. The configuration is specified in the `application.properties` file:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### Accessing the H2 Console

You can access the H2 Console at http://localhost:8080/h2-console with the following credentials:

- JDBC URL: jdbc:h2:mem:testdb
- User Name: sa
- Password: password

### API Endpoints

1. **User Management**:
   - Add User: POST /users
     ```
     {
         "name": "John Doe",
         "email": "john.doe@example.com"
     }
   - Remove User: DELETE /users/{userId}
   - List Users: GET /users
2. **Friendship Management**:
   - Add User: POST /users
     ```
     {
       "name": "John Doe",
       "email": "john.doe@example.com"
     }
   - Remove User: DELETE /users/{userId}
   - List Users: GET /users
3. **Network Analysis**:
   - Find Shortest Path: GET /network/shortest-path?userId1={userId1}&userId2={userId2}