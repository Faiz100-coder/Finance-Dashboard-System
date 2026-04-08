Project also Available on BitBucket: https://bitbucket.org/faizans-workspace/workspace/projects/FDS

# 💰 Finance Dashboard System (Backend)

A clean,  **microservices backend system** built using Spring Boot that manages users, financial records, and provides dashboard Summary.
Service: 
1. User Management service
2. Finance Service

This project focuses on **backend design, data modeling, access control, and real-world system thinking**, rather than unnecessary complexity.



# 🚀 Project Overview

This system is designed as a **Finance Dashboard Backend** where:

* Users have **role-based access (ADMIN, ANALYST, VIEWER)**
* Financial records are managed securely
* Dashboard APIs provide **aggregated insights (not just CRUD)**

The goal was to build a system that is:

* Cleanly structured
* Secure (JWT-based authentication)
* Scalable (microservices architecture)



# 🏗️ Architecture

This project follows a **2-microservice architecture**:

## 🔹 User Service (Port: 8080)

* User management
* Role assignment
* Authentication (JWT)

## 🔹 Finance Service (Port: 8081)

* Financial records CRUD
* Filtering
* Dashboard analytics

👉 Services are independent but connected through **JWT-based communication**



# 🔐 Authentication & Authorization

* JWT-based authentication
* Token contains:

  * email
  * role
* Token validated in both services

👉 Important design decision:

> If user role changes → user must login again to get updated token

This ensures:

* Security
* Role consistency



# 🧠 Role-Based Access Control

| Role    | Access           |
| ------- | ---------------- |
| ADMIN   | Full access      |
| ANALYST | Read + analytics |
| VIEWER  | Read-only        |

Implemented using:

```java
@PreAuthorize("hasRole('ADMIN')")
```



# 📦 Tech Stack

* Java + Spring Boot
* Spring Security + JWT
* Spring Data JPA
* MySQL
* Lombok
* ModelMapper
* Maven

Additional Practices:

* Builder Pattern
* DTO separation
* Bean Validation
* SLF4J Logging



# 🧑 User APIs

## ➤ Create User

Request:

```json
{
  "name": "Faizan Ahmed",
  "email": "myadmin@gmail.com",
  "password": "111111"
}
```

Response:

```json
{
  "id": 8,
  "name": "Faizan Ahmed",
  "email": "myadmin@gmail.com",
  "role": "VIEWER",
  "status": "ACTIVE"
}
```



## ➤ Login

Request:

```json
{
  "email": "myadmin@admin.com",
  "password": "111111"
}
```

Response:

```json
{
  "token": "<JWT_TOKEN>",
  "type": "Bearer"
}
```



## ➤ Assign Role (Admin Only)

```json
{
  "role": "ANALYST"
}
```

Response:

```json
{
  "role": "ANALYST",
  "message": "Role Updated Successfully.."
}
```



# 💰 Finance APIs

## ➤ Features

* Create Record (Admin)
* Get All Records (Admin, Analyst)
* Get By ID (All roles)
* Update Record (Admin)
* Soft Delete (Admin)
* Filter by type/category/date

👉 Soft delete used instead of hard delete to preserve financial history



# 📊 Dashboard API

## ➤ GET /dashboard/summary

Response:

```json
{
  "totalIncome": 40000.0,
  "totalExpense": 45000.0,
  "netBalance": -5000.0,
  "categoryWiseTotals": {
    "INCOME": {
      "salary": 30000.0,
      "home": 10000.0
    },
    "EXPENSE": {
      "salary": 30000.0,
      "rent": 15000.0
    }
  },
  "recentTransactions": [...],
  "monthlyTrends": {
    "JUNE": 20000.0,
    "MAY": 35000.0,
    "APRIL": 30000.0
  }
}
```



# ⚠️ Exception Handling

Custom error structure used:

```json
{
  "errorCode": "4006",
  "errorMessage": "Email Already Exists.. Try Another Email.."
}
```

Examples:

```json
{
  "status": 401,
  "error": "UNAUTHORIZED",
  "message": "Authentication token is missing or invalid"
}
```

```json
{
  "status": 403,
  "error": "FORBIDDEN",
  "message": "You do not have permission to access this resource"
}
```

👉 Different services use different error code ranges:

* User Service → 400x
* Finance Service → 500x

Used Swagger For Documentation
User Service: 
<img width="1336" height="859" alt="image" src="https://github.com/user-attachments/assets/9ba55728-08dc-44cc-8e5b-065cf5571ab5" />
<img width="1435" height="819" alt="image" src="https://github.com/user-attachments/assets/bc35c473-71f1-4293-8363-a0c3c4a08c2b" />
Schemas
<img width="1096" height="534" alt="image" src="https://github.com/user-attachments/assets/a0934b9e-48fe-41d5-adc5-32ef575f7d32" />

Financial Service
<img width="1368" height="890" alt="image" src="https://github.com/user-attachments/assets/f93f2ca7-1397-480e-828b-3858a086a73a" />
<img width="1403" height="903" alt="image" src="https://github.com/user-attachments/assets/12c2e25c-0de4-462d-863a-b45f3f3fa566" />
Schemas
<img width="1456" height="609" alt="image" src="https://github.com/user-attachments/assets/c869b117-3aaa-46bf-b6de-4262b95dfe70" />


# 🧠 Design Decisions (Important)

### Why Microservices?

* To show that I can build System Using Microservices Architecture, I understand System.
* Separation of concerns
* Independent scaling
* Better maintainability

### Why JWT?

* Stateless authentication
* Secure API communication

### Why Soft Delete?

* Financial data should not be lost
* Supports audit/history

### Why DTO?

* Avoid exposing internal entities
* Cleaner API design

---

# ⚙️ Setup Instructions

1. Clone repository:

```
git clone https://bitbucket.org/faizans-workspace/workspace/projects/FDS
```

2. Start services:

* User Service → Port 8080
* Finance Service → Port 8081

3. Configure MySQL in `application.properties`

4. Run both applications

---

# 🔗 API Testing

* Use Postman or Swagger
* Add header:

```
Authorization: Bearer <token>
```



# 🏆 Final Notes

This project focuses on:

* Clean backend architecture
* Secure API design
* Real-world implementation practices

It demonstrates how to build a **structured, maintainable backend system** rather than just writing CRUD APIs.



# 🔗 Repository

[https://bitbucket.org/faizans-workspace/workspace/projects/FDS](https://bitbucket.org/faizans-workspace/workspace/projects/FDS)



✨ Built with a focus on clarity, correctness, and real backend engineering practices.



# 📊 Evaluation Criteria Alignment

This project was built keeping the evaluation criteria in mind:

### 1. Backend Design

* Clear separation: controller → service → repository → DTO
* Microservices architecture (User + Finance service)

### 2. Logical Thinking

* Role-based access using `@PreAuthorize`
* JWT-based identity with role validation
* Dashboard aggregation logic (not just CRUD)

### 3. Functionality

* All required APIs implemented:

  * User management
  * Financial records
  * Dashboard summary
  * I used BcryptPasswordEncoder to encode the passsword because We all know that in real systems passwords are never store in a plain text    
     userDB
    <img width="833" height="95" alt="image" src="https://github.com/user-attachments/assets/3c5ed5b8-891e-4b29-8dbc-e038e8777bac" />
   this is a Demo data used while testing
 financial db
<img width="977" height="365" alt="image" src="https://github.com/user-attachments/assets/66a45d2f-e3ba-4947-8a09-ed69d54aad63" />


### 4. Code Quality

* Clean naming conventions
* DTO-based design
* Builder pattern used

### 5. Database Design

* Proper entity modeling
* Soft delete using `isDeleted`
* Separation of user and financial domains

### 6. Validation & Reliability

* Bean validation used
* Proper exception handling with HTTP status codes

### 7. Documentation

* Clear API examples
* Setup instructions included

### 8. Additional Thoughtfulness

* JWT expiration handling
* Role refresh after login
* Logging using SLF4J



# 🔗 Additional Project (Related Work)

I have also built a **Turf Booking System Backend**, which demonstrates similar backend concepts applied in a different domain.

### Highlights:

* Role-based access (Admin / Vendor / User)
* Cloudinary Image Integeration 
* Microservices-based structure
* JWT authentication
* completed Auth & Vendor services with MySQL schema design.
* Integrated Cloudinary for image upload & storage and applied

👉 This project shows my ability to apply backend principles across domains.

🔗 Repository Link:
https://bitbucket.org/faizans-workspace/workspace/projects/TRUF


