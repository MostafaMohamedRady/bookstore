# Introduction 
book-store:- API for an online bookstore, where the user can perform the following operations:

    CRUD operations on Books
    Checkout operation for single or multiple books which will return the total payable amount.

## Installation processes
This maven project can be used by any IDE just clone the project and open it as a maven project.
To install and run test cases use the following maven command-line
```
mvn test
```
To create code cover report [You can find this report in the target/site/jacoco directory]
```
mvn test jacoco:report
```
To Run the application
```
mvn  spring-boot:run
```
To Run the application using DOCKER
```
docker build --tag=bookstore:latest .
docker run -p8080:8080 bookstore:latest
```
## Tools
```
- spring boot 
- Junit5 Mockito 
- jacoco
```
## API references
swagger:-
```
localhost:8080/swagger-ui.html
```
Add Book :-
```
URL: localhost:8080/v1/book/add

Request
{
  "author": "author",
  "description": "description",
  "isbn": "10-9-10",
  "name": "name",
  "price": 100,
  "type": "fiction"
}
Response
{
    "status": "SUCCESS",
    "message": "Successfully add book details in DB.",
    "data": {
        "name": "name",
        "description": "description",
        "author": "author",
        "type": "fiction",
        "price": 100.0,
        "isbn": "10-9-10"
    }
}
```
Update Book :-
```
URL: localhost:8080/v1/book/10-9-10/update

Request
{
  "author": "author",
  "description": "description",
  "isbn": "10-9-10",
  "name": "name",
  "price": 200,
  "type": "fiction"
}
Response
{
    "status": "SUCCESS",
    "message": "Successfully update book details in DB.",
    "data": {
        "name": "name",
        "description": "description",
        "author": "author",
        "type": "fiction",
        "price": 200.0,
        "isbn": "10-9-10"
    }
}
```
Get Book :-
```
URL: localhost:8080/v1/book/10-9-10

Response
{
    "status": "SUCCESS",
    "data": {
        "name": "name",
        "description": "description",
        "author": "author",
        "type": "fiction",
        "price": 100.0,
        "isbn": "10-9-10"
    }
}
```
Book List:-
```
URL: localhost:8080/v1/book/list

Response
{
    "status": "SUCCESS",
    "data": [
        {
            "name": "name",
            "description": "description",
            "author": "author",
            "type": "fiction",
            "price": 100.0,
            "isbn": "10-9-10"
        },
        {
            "name": "name",
            "description": "description",
            "author": "author",
            "type": "fiction",
            "price": 100.0,
            "isbn": "10-9-10"
        }
    ]
}
```
Add Promo :-
```
URL: localhost:8080/v1/promo/add
Request
{
  "code": "PRO2020",
  "discount": {
    "fiction": 10
  }
}
Response
{
    "status": "SUCCESS",
    "message": "Successfully promo book details in DB."
}
```
Promo List:-
```
URL: http://localhost:8080/v1/promo/list

Response
{
    "status": "SUCCESS",
    "message": "Successfully fetch details from db",
    "data": [
        {
            "code": "PRO2020",
            "discount": {
                "fiction": 10
            }
        }
    ]
}
```

Checkout :-
```
URL: localhost:8080/v1/bill/Checkout
Request
{
  "isbnList": [
    "10-9-10"
  ],
  "promoCode": "PRO2020"
}
Response
{
    "status": "SUCCESS",
    "data": {
        "totalAmount": 200.0,
        "netAmount": 180.0,
        "discountAmount": 20.0,
        "bookList": [
            {
                "name": "name",
                "description": "description",
                "author": "author",
                "type": "fiction",
                "price": 200.0,
                "isbn": "10-9-10"
            }
        ]
    }
}
```