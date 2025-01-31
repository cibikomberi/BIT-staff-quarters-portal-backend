# Staff Quarters Portal - Backend  

This is the backend for the **Staff Quarters Portal**, a system designed to manage guest and inmate check-ins/checkouts and track complaints efficiently. The backend is built using **Java Spring Boot** and provides secure REST APIs for the frontend.  

## Table of Contents  

- [Features](#features)  
- [Technologies Used](#technologies-used)  
- [Implementation Details](#implementation-details)  
- [Frontend Repository](#frontend-repository)  
- [Contact](#contact)  

---

## Features  

- **Guest and Inmate Management**: Handles check-in and check-out operations securely.  
- **Complaint Tracking**: Allows logging, tracking, and resolution of complaints.  
- **Secure Authentication & Authorization**: Implements **Spring Security** with **JWT-based authentication**.  
- **Role-Based Access Control (RBAC)**: Restricts access based on user roles (Admin, Handler, User).  
- **Database Management**: Uses **PostgreSQL** for efficient data storage and retrieval.  

## Technologies Used  

- **Java Spring Boot** – REST API development  
- **Spring Security** – Authentication & Authorization  
- **JWT (JSON Web Tokens)** – Secure user authentication  
- **PostgreSQL** – Relational database for storing guests, inmates, and complaints  
- **Spring Data JPA** – ORM for database interaction  

## Implementation Details  

- **Spring Boot & REST APIs**:  
  - The backend is built using **Spring Boot**, following a layered architecture (**Controller-Service-Repository**) for clean code organization.  
  - **Spring Data JPA** is used to interact with **PostgreSQL**, making database operations seamless.  

- **Authentication & Authorization**:  
  - Implements **Spring Security** with **JWT authentication**, ensuring secure user logins.  
  - Uses **role-based access control (RBAC)** to restrict endpoints based on user roles (**Admin, Handler, User**).  

- **Database Management (PostgreSQL)**:  
  - Stores **users, complaints, check-ins, and check-outs** in a structured manner.  
  - Uses **Spring Data JPA** for efficient querying and persistence.  

- **Complaint Tracking System**:  
  - Users can log complaints, which are assigned to handlers for resolution.  
  - Handlers update the status of complaints as they progress.  

- **Check-In & Check-Out Handling**:  
  - Guests and inmates can be checked in and out through dedicated services, maintaining a history of movements.  

## Frontend Repository  

The frontend for this project is built using **React.js** with **Material Design** for a user-friendly experience.  
🔗 **Frontend Repository:** [https://github.com/cibikomberi/BIT-staff-quarters-portal-frontend](https://github.com/cibikomberi/BIT-staff-quarters-portal-frontend)  

## Contact  

For any queries or support, please contact [cibikomberi@gmail.com](mailto:cibikomberi@gmail.com).  

---  

*Thank you for using the Staff Quarters Portal Backend!* 🚀
