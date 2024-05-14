# CRUD Application with MongoDB and Spring Boot

This project is a CRUD (Create, Read, Update, Delete) application built using MongoDB and Spring Boot. It provides endpoints to perform various operations on a collection of books.

## Endpoints

- **POST /api/v1/book**
  - Creates a new book.
  
- **GET /api/v1/books**
  - Retrieves all books.
  
- **GET /api/v1/book/name**
  - Retrieves a book by name (provided in the request body).
  
- **GET /api/v1/book/?name=(bookname)**
  - Retrieves a book by name (provided as a path parameter).
  
- **DELETE /api/v1/book/name**
  - Deletes a book by name.
  
- **DELETE /api/v1/books**
  - Deletes all books.
  
- **PUT /api/v1/book/name**
  - Updates the fields of a book by name.

## Technologies Used
- Spring Boot
- MongoDB
- Docker


## 📌You can check the endpoints and API in the Postman folder provided
## PostMan API Outcome
![image](https://github.com/rahulbasutkar04/CRUDAPP/assets/115400916/983957b8-8e28-41f7-a39a-29b983609863)
![image](https://github.com/rahulbasutkar04/CRUDAPP/assets/115400916/7876a311-abd1-478b-bd2b-d41debe861af)
![image](https://github.com/rahulbasutkar04/CRUDAPP/assets/115400916/06b61fbe-d5d3-4096-9a9b-8da2fc13af07)
![image](https://github.com/rahulbasutkar04/CRUDAPP/assets/115400916/b5f8f1aa-b0d7-4515-91bd-e85bb27138b6)
![image](https://github.com/rahulbasutkar04/CRUDAPP/assets/115400916/b6108fea-1c51-4e2c-9e3c-48d104381d70)




