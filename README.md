# README - FLYER ✈️ (Flight Connections Search Application)

## Description

FLYER is a system that provides an API service for users to search available flight connections and make flight reservations. The system is built on a microservices architecture, with communication through events. Additionally, the application includes several microservices, including an API Gateway and Discovery Server, to assist in managing and scaling microservices.

## Used Technologies 🛠️

### Microservices

The application consists of several microservices, each being an independent component:

- **Tracking Service**: Responsible for tracking and managing flight reservations.

- **Reservation Service (DDD)**: Developed following Domain-Driven Design (DDD) principles to manage flight reservations.

- **API Gateway**: Acts as an API gateway, controlling access to individual microservices and routing requests to the appropriate services.

- **Discovery Server**: Acts as a service registration and discovery server, making it easier to scale and manage microservices.

### Communication Between Microservices 📡

- **Apache Kafka**: The system utilizes Apache Kafka for inter-microservice communication, using events to transmit information and synchronize data between services.

### Databases 📊

- **MongoDB**: The application uses MongoDB to store data related to flight reservations.

- **PostgreSQL**: PostgreSQL is used to store other flight-related data, such as flight information and routes.

### Testing 🧪

- **Testcontainers**: We use Testcontainers for testing purposes, which allows us to run Docker containers to test our microservices and databases.

### Authentication and Authorization 🔒

- **JSON Web Tokens (JWT)**: To ensure secure access to the application, JWT is used for user authentication and authorization.

### Data Management 🧾

- **Liquibase**: The application uses Liquibase to manage database schema and migrations, with pre-populated data for initial setup.

### Docker Compose 🐳

- **Docker Compose**: The project utilizes Docker Compose to run Docker containers containing microservices and necessary services like databases and Apache Kafka.

## Running Instructions 🏃‍♂️

1. **Dependencies**: Ensure you have the following dependencies installed:

   - Docker
   - Docker Compose

2. **Configuration**: Configure database access, Kafka settings, and other parameters in the configuration files of the microservices.

3. **Running Microservices**: Run Docker Compose, which will automatically start Docker containers containing microservices and other required services.

4. **Running API Gateway**: Start the API Gateway microservice and configure redirects to the appropriate microservices.

5. **Running Discovery Server**: Run the Discovery Server microservice to register other microservices.

## Usage Instructions 🚀

After successfully running the FLYER application, other systems or client applications can use the API service provided by FLYER to search for and reserve flight tickets.

## Author 📝

The FLYER application was created by Mateusz Milewczyk.

## Contact 📬

For inquiries or feedback regarding the FLYER application, please contact me via direct message.

Thank you for using API, and I wish you a pleasant experience with my flight services! ✈️🌍
