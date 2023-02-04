# BANK ACCOUNT KATA

Based on the provided specification, this application is a simple bank account system that enables users to deposit, withdraw funds, view and print their account statements.

We have implemented this application using the Hexagonal Architecture.

## Hexagonal Architecture with Spring Boot

This project showcases the implementation of Hexagonal Architecture using Spring Boot. Hexagonal Architecture, also referred to as Ports and Adapters, is a design pattern that promotes separation of concerns and modularity.

## Application inplementation 

The application is divided into 4 modules: application, domain, infrastructure, and Kata_Launcher.

The domain model contains the core business logic and entities, including Client, Account, and Transaction entities. The services in this module implement the business logic of the bank account.

We also have API and SPI classes:

API - This module gathers all the interfaces for querying the domain. These interfaces are implemented by the hexagon.
SPI (Service Provider Interface) - This module gathers all the interfaces required by the domain to retrieve information from external sources. These interfaces are defined in the hexagon and implemented by the right-side of the infrastructure.

The infrastructure model contains the mappers, adapters and external system integrations such as persistence and communication.

The application model houses the web-related components such as controllers and REST endpoints. This module contains the RestController of our application.

Finally, the Kata_Launcher module is responsible for bootstrapping all the modules required by the hexagonal architecture.




### Technology Stack

* Java 18
* Spring Boot 2.7.4
* Maven
* H2 Database


## Running the Application

* Clone the repository
* Navigate to the __Kata_Launcher__ module and go to the __main__ class of the project
* Run `mvn spring-boot:run`
* Access the application at `http://localhost:8080/swagger-ui/index.html`


## Test the Application

* Run `mvn test `to run the unit tests.

## Conclusion

In this project, the Hexagonal Architecture pattern has been implemented using Spring Boot. The design pattern helps to achieve separation of concerns and modularity, making the code more maintainable and testable.