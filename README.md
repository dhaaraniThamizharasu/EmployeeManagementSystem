# 👨‍💼 Employee Management System

> A role-based full-stack Java web application for managing employees, departments, attendance, and user accounts through a secure and centralized management platform.

---

## 📌 Project Overview

The **Employee Management System** is a full-stack Java web application designed to simplify employee administration and attendance management.

The system provides separate experiences for **Administrators** and **Employees**.

Administrators can manage employee records, departments, attendance, and user accounts, while employees can securely access their own profile, attendance records, and account settings.

The application follows a layered architecture using **Java Servlets, JSP, JDBC, and MySQL**, with Apache Tomcat serving as the application server.

---

## 🎯 Objectives

The main objectives of this project are:

- Digitize employee record management
- Centralize department information
- Simplify attendance management
- Provide role-based access control
- Allow employees to view their own information
- Provide secure user account management
- Reduce manual HR administration
- Demonstrate practical full-stack Java development

---

## ✨ Key Features

### 🔐 Authentication

- Secure login system
- Username and password authentication
- Session-based authentication
- Logout functionality
- Role-based access control

### 👨‍💼 Employee Management

Administrators can:

- Add employees
- View employees
- Search employees
- Edit employee details
- Delete employees
- Assign employees to departments
- Manage employee status

### 🏢 Department Management

Administrators can:

- Add departments
- View departments
- Edit departments
- Delete departments
- Maintain department descriptions

### 📅 Attendance Management

Administrators can:

- Mark attendance
- View attendance records
- Edit attendance
- Delete attendance
- Record check-in and check-out times
- View attendance status

### 👤 User Management

Administrators can:

- Create user accounts
- Assign users to employees
- Select user roles
- Edit user accounts
- Delete user accounts
- Prevent duplicate usernames
- Prevent duplicate employee account linking

### 👨‍💻 Employee Portal

Employees can:

- View their profile
- View their attendance
- Change their password
- Logout securely

### 📊 Dashboard

The dashboard provides live statistics including:

- Total employees
- Total departments
- Present employees
- Absent employees
- Total attendance records

---

# 🏗️ System Architecture

```mermaid
flowchart TB

    U["👤 User"]
    L["🔐 Login Page"]
    S["☕ Java Servlet Layer"]
    A["🛡️ Authentication & Role Control"]

    D["📊 Dashboard"]
    EM["👨‍💼 Employee Management"]
    DM["🏢 Department Management"]
    AM["📅 Attendance Management"]
    UM["👤 User Management"]

    EP["👨‍💻 Employee Portal"]
    MP["👤 My Profile"]
    MA["📅 My Attendance"]
    CP["🔑 Change Password"]

    DAO["🔗 JDBC / DAO Layer"]
    DB[("🗄️ MySQL Database")]

    U --> L
    L --> S
    S --> A

    A --> D
    A --> EM
    A --> DM
    A --> AM
    A --> UM
    A --> EP

    EP --> MP
    EP --> MA
    EP --> CP

    D --> DAO
    EM --> DAO
    DM --> DAO
    AM --> DAO
    UM --> DAO
    MP --> DAO
    MA --> DAO
    CP --> DAO

    DAO --> DB