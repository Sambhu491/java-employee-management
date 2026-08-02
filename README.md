# Employee Management System (Java + JDBC + MySQL)

A console-based Employee Management System developed using **Core Java**, **JDBC**, and **MySQL**. The application demonstrates user authentication, role-based access, CRUD operations, database connectivity, and input validation through a menu-driven command-line interface.

This project was built to strengthen practical understanding of Java, Object-Oriented Programming, JDBC, SQL, and database-driven application development. It is intended as a learning project and portfolio demonstration.

---

# Features

## Authentication

- Login system using username and password
- Role-based authentication
  - Administrator
  - Employee

---

## Administrator

Administrators can:

- Add new employees
- Retrieve all employee records
- Search employees by:
  - Employee ID
  - Employee Name
- Update employee information
- Delete employee records

---

## Employee

Employees can:

- View their information
- Search employee records
- Update personal details
- Change account password

---

## Input Validation

The application validates user input before storing it in the database.

Validation includes:

- Email address format
- Mobile number format
- Date of Birth format
- Positive Employee ID
- Positive Salary
- Password confirmation

---

# Technologies Used

- Java
- JDBC
- MySQL
- SQL
- Object-Oriented Programming (OOP)
- Regular Expressions (Regex)

---

# Project Structure

```
src/
└── com/
    └── Project1/
        └── AdminEmployee.java
```

---

# Database

The application uses MySQL as the backend database.

### Tables

```
employeeDetails
```

Stores employee information.

```
loginDetails
```

Stores login credentials and user roles.

---

# Functionalities

- User Login
- Role-based Access Control
- Add Employee
- View Employee Records
- Search Employees
- Update Employee Details
- Delete Employee
- Change Password
- Data Validation

---

# Learning Outcomes

This project helped reinforce concepts including:

- Core Java
- Object-Oriented Programming
- JDBC
- SQL CRUD Operations
- Database Connectivity
- Prepared SQL Queries
- User Input Validation
- Console Application Design

---

# Current Limitations

This project was created for educational purposes.

Current limitations include:

- Console-based interface
- Passwords are stored in plaintext
- SQL queries are not fully parameterized
- Single-class implementation
- Local database only

These areas are intended for future improvements.

---

# Future Enhancements

- Password hashing (BCrypt)
- PreparedStatement for all database queries
- Layered architecture (DAO, Service, Model)
- Logging
- Exception handling improvements
- Configuration file for database credentials
- Spring Boot REST API version
- JavaFX desktop interface

---

# About

Developed as a personal learning project to practice Java, JDBC, MySQL, and database application development.
