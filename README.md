# ShowGo Backend - Movie Ticket Booking Platform API

## 📋 Overview

This repository contains the backend API for ShowGo, a comprehensive movie ticket booking platform inspired by BookMyShow. The backend is built with Java SpringBoot and provides a robust set of RESTful APIs supporting multiple user roles, secure authentication, and integration with the Stripe payment gateway.

[Live Application](https://showgo.muralidharan.me/) | [Frontend Repository](https://github.com/Muralidharan6969/BookMyShow_FE/)

## 🏗️ Architecture

The ShowGo backend implements a microservices-oriented architecture with:

- **SpringBoot**: Core framework for RESTful API development
- **Spring Security**: Authentication and authorization framework
- **JPA/Hibernate**: ORM for database operations
- **MySQL/PostgreSQL**: Database (select the one you're using)
- **JWT**: Token-based authentication
- **Stripe API**: Payment processing integration

## 👥 User Roles & Access Control

The backend supports three distinct user types with comprehensive Role-Based Access Control (RBAC):

1. **User**: End customers who can browse movies and book tickets
    - Can view movies, theaters, and showtimes
    - Can select seats and make bookings
    - Can manage their profile and view booking history

2. **Outlet**: Theater managers with restricted administrative access
    - Can add theaters (subject to admin approval)
    - Can manage screens for approved theaters
    - Can create and manage shows for their theaters

3. **Admin**: System administrators with full platform access
    - Can approve/reject theater requests from outlets
    - Can add/modify cities and movies
    - Has access to all system data and management functions

## 🔒 Security Implementation

- **JWT Token Authentication**: Secure authentication mechanism
- **Custom Security Filters**: For role-based access control
- **CORS Configuration**: Properly configured to prevent cross-origin attacks
- **Request Validation**: Input validation to prevent injection attacks
- **Authorized Endpoints**: API endpoints secured based on user roles

## 💼 Core Features

### Booking System

- **Seat Reservation**: Advanced seat selection and temporary locking
- **Concurrency Control**: Race condition handling using database locks
- **Transaction Management**: Ensuring booking integrity across operations

### Content Management

- **Multi-City Support**: Cities can be added and managed by admins
- **Movie Management**: Complete movie data management system
- **Theater Administration**: Theater approval workflow and management
- **Show Scheduling**: Comprehensive show creation and management

### Payment Integration

- **Stripe Integration**: Secure payment processing with webhooks
- **Payment Verification**: Ensuring successful transactions before confirming bookings
- **Refund Support**: API endpoints for cancellation and refund processes

[//]: # (## 🔧 API Endpoints)

[//]: # ()
[//]: # (### Authentication)

[//]: # ()
[//]: # (- `POST /api/auth/register` - User registration)

[//]: # (- `POST /api/auth/login` - User authentication)

[//]: # (- `POST /api/auth/refresh` - Token refresh)

[//]: # ()
[//]: # (### User Operations)

[//]: # ()
[//]: # (- `GET /api/user/profile` - Get user profile)

[//]: # (- `PUT /api/user/profile` - Update user profile)

[//]: # (- `GET /api/user/bookings` - Get user's booking history)

[//]: # ()
[//]: # (### Movie & Theater Operations)

[//]: # ()
[//]: # (- `GET /api/movies` - List all movies)

[//]: # (- `GET /api/cities` - List all cities)

[//]: # (- `GET /api/theaters` - List theaters by city)

[//]: # (- `GET /api/shows` - List shows by theater and date)

[//]: # ()
[//]: # (### Booking Operations)

[//]: # ()
[//]: # (- `POST /api/bookings/lock` - Lock seats temporarily)

[//]: # (- `POST /api/bookings/confirm` - Confirm booking after payment)

[//]: # (- `GET /api/bookings/{id}` - Get booking details)

[//]: # ()
[//]: # (### Admin Operations)

[//]: # ()
[//]: # (- `POST /api/admin/movies` - Add new movie)

[//]: # (- `POST /api/admin/cities` - Add new city)

[//]: # (- `PUT /api/admin/theaters/{id}/approve` - Approve theater)

[//]: # (### Outlet Operations)

[//]: # ()
[//]: # (- `POST /api/outlet/theaters` - Register new theater)

[//]: # (- `POST /api/outlet/screens` - Add new screen to theater)

[//]: # (- `POST /api/outlet/shows` - Create new show)

## ⚙️ Technical Implementation Highlights

- **Concurrent Request Handling**: Using database locks via Hibernate to handle race conditions during seat booking
- **Transaction Management**: Properly configured transaction boundaries for critical operations
- **Caching Implementation**: Optimized API responses with appropriate caching strategies
- **Error Handling**: Comprehensive exception handling with appropriate HTTP status codes
- **Logging**: Detailed logging for auditing and debugging purposes

## 🚀 Getting Started

### Prerequisites

- Java 11 or higher
- Maven 3.6+
- MySQL/PostgreSQL database
- Stripe account for payment processing

### Installation

1. Clone the repository:
```bash
git clone https://github.com/Muralidharan6969/BookMyShow_BE.git
cd BookMyShow_BE
```

2. Configure application properties:
```properties
#Server Setup
server.port=your_intended_server_port

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/showgo
spring.datasource.username=your_username
spring.datasource.password=your_password

# JWT Configuration
jwt.secret=your_jwt_secret
jwt.expiration=86400000

# Stripe Configuration
stripe.api.key=your_stripe_api_key
```

3. Build the application:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

## 📝 Future Improvements

- Improved caching strategies for better performance
- API versioning for better backward compatibility
- Enhanced security measures and audit logging


## 🤝 Contributions

Contributions, issues, and feature requests are welcome. Feel free to check the issues page if you want to contribute.

---

Developed with ❤️ by Muthiah Muralidharan