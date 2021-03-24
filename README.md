# Introduction 
book-store:- API for an online bookstore, where the user can perform the following operations:

    CRUD operations on Books
    Checkout operation for single or multiple books which will return the total payable amount.

## Installation processes
This maven project can be used by any IDE just clone the project and open it as a maven project.
To install and run test cases use the following maven command-line
```
mvn clean install
```
To create code cover report [You can find this report in the target/site/jacoco directory]
```
mvn test jacoco:report
```
To Run the application
```
mvn  spring-boot:run
```
## Tools
```
- spring boot 
- Junit5 Mockito 
- jacoco
```
## API references
```
localhost:8080/swagger-ui.html
```