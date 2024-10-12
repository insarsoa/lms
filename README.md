# Getting Started

### Reference Documentation
# Library Management System

LMS in short is a system to handle Book Operations

* addBook
* removeBook
* findBookByISBN
* findBooksByAuthor
* borrowBook
* returnBook

## Software Used to build this system

* Java 17
* Spring Boot 3.3.4
* Lombok 1.18.20
* JsonWebToken 0.11.15
* EhCache 3.10.8
* Spring Security 6.2.3
* Junit 5

## Project Modules
```modules
package - my.project.lms

- controller
- exceptionhandler
- exceptions
- filter
- model
- service
- util
```

## Usage

```lms
# mvn clean install

cleans, builds the project and creates a lms-0.0.1-SNAPSHOT.jar

# mvn test

runs unit tests

# mvn verify

runs integration tests

# mvn surefire-report:report

creates a reports folder in target with surefire.html 
to view test reports in any browser 
with stats of unit and integration tests.
```
## API Endpoints
```endpoints
create jwt token (POST) - http://localhost:8080/login
add book (POST)         - http://localhost:8080/api/library/books
remove book (DELETE)    - http://localhost:8080/api/library/books/{ISBN}
findBookByISBN (GET)    - http://localhost:8080/api/library/books/{ISBN}
findBooksByAuthor (GET) - http://localhost:8080/api/library/books?author={authorname}
borrowBook (POST)       - http://localhost:8080/api/library/books/borrow//{ISBN}
returnBook (POST)       - http://localhost:8080/api/library/books/return/{ISBN}
```
## Postman

```rest api
# create jwt token

uri - http://localhost:8080/login

header

content-type : application/json

payload

{
  "username":"user",
  "password":"password"
}

# addBook (copy token from response of create jwt token 
and replace <jwttoken> in below header)

uri - http://localhost:8080/api/library/books

header

content-type : application/json
Authorization : Bearer <jwttoken>

payload

{
    "isbn": "5555",
    "title": "Python",
    "author": "Arul",
    "publicationYear": 2024,
    "availableCopies": 6
}
```

## Ehcache used for caching

Fast.
Simple.
Opensource.
Minimal dependencies.
Improves Performance
Provides for scalability into terabytes.
Scalable to hundreds of caches.
Tuned for high concurrent load on large/wide multi-CPU servers.
Scalable to hundreds of nodes.

## Error Handling and SOLID Principles used also API Authentication using JWT token Implemented.