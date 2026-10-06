<%@ page import="com.employeemanagement.model.User" %>

<%
    User loggedInUser =
            (User) session.getAttribute("loggedInUser");

    if (loggedInUser == null) {
        response.sendRedirect("login.html");
        return;
    }

    boolean isAdmin =
            "ADMIN".equalsIgnoreCase(
                    loggedInUser.getRole()
            );

    Integer totalEmployees =
            (Integer) request.getAttribute(
                    "totalEmployees"
            );

    Integer totalDepartments =
            (Integer) request.getAttribute(
                    "totalDepartments"
            );

    Integer presentToday =
            (Integer) request.getAttribute(
                    "presentToday"
            );

    Integer absentToday =
            (Integer) request.getAttribute(
                    "absentToday"
            );

    Integer totalAttendanceRecords =
            (Integer) request.getAttribute(
                    "totalAttendanceRecords"
            );

    if (totalEmployees == null) {
        totalEmployees = 0;
    }

    if (totalDepartments == null) {
        totalDepartments = 0;
    }

    if (presentToday == null) {
        presentToday = 0;
    }

    if (absentToday == null) {
        absentToday = 0;
    }

    if (totalAttendanceRecords == null) {
        totalAttendanceRecords = 0;
    }
%>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <title>
        Employee Management System - Dashboard
    </title>

    <style>

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f4f6f9;
            color: #1f2937;
        }

        .header {
            background: #1f2937;
            color: white;
            padding: 22px 40px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .header h1 {
            margin: 0;
            font-size: 26px;
        }

        .user-info {
            text-align: right;
        }

        .user-info strong {
            display: block;
            font-size: 16px;
        }

        .role {
            font-size: 13px;
            opacity: 0.8;
        }

        .container {
            width: 92%;
            max-width: 1250px;
            margin: 30px auto;
        }

        .welcome {
            background: white;
            padding: 25px;
            border-radius: 12px;
            margin-bottom: 25px;
            box-shadow: 0 3px 10px rgba(0,0,0,0.08);
        }

        .welcome h2 {
            margin-top: 0;
            margin-bottom: 8px;
        }

        .welcome p {
            margin: 0;
            color: #6b7280;
        }

        .stats-grid {
            display: grid;
            grid-template-columns:
                repeat(auto-fit, minmax(210px, 1fr));

            gap: 20px;

            margin-bottom: 30px;
        }

        .stat-card {
            background: white;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 3px 10px rgba(0,0,0,0.08);
        }

        .stat-title {
            color: #6b7280;
            font-size: 15px;
            margin-bottom: 12px;
        }

        .stat-value {
            font-size: 34px;
            font-weight: bold;
            color: #111827;
        }

        .navigation {
            background: white;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 3px 10px rgba(0,0,0,0.08);
        }

        .navigation h2 {
            margin-top: 0;
        }

        .nav-grid {
            display: grid;
            grid-template-columns:
                repeat(auto-fit, minmax(190px, 1fr));

            gap: 15px;
        }

        .nav-btn {
            display: block;
            text-align: center;
            text-decoration: none;
            background: #374151;
            color: white;
            padding: 14px 10px;
            border-radius: 8px;
            font-weight: bold;
        }

        .nav-btn:hover {
            opacity: 0.9;
        }

        .logout {
            background: #dc2626;
        }

        .admin-title {
            color: #2563eb;
        }

        .employee-title {
            color: #059669;
        }

        @media (max-width: 600px) {

            .header {
                padding: 18px;
                flex-direction: column;
                align-items: flex-start;
                gap: 10px;
            }

            .user-info {
                text-align: left;
            }

            .container {
                width: 94%;
            }

        }

    </style>

</head>

<body>

<div class="header">

    <h1>
        Employee Management System
    </h1>

    <div class="user-info">

        <strong>
            <%= loggedInUser.getUsername() %>
        </strong>

        <span class="role">
            <%= loggedInUser.getRole() %>
        </span>

    </div>

</div>


<div class="container">


    <!-- WELCOME -->

    <div class="welcome">

        <% if (isAdmin) { %>

            <h2 class="admin-title">
                Admin Dashboard
            </h2>

            <p>
                Manage employees, departments,
                attendance and user accounts.
            </p>

        <% } else { %>

            <h2 class="employee-title">
                Employee Dashboard
            </h2>

            <p>
                View your profile, attendance
                and account information.
            </p>

        <% } %>

    </div>


    <!-- STATISTICS -->

    <div class="stats-grid">

        <div class="stat-card">

            <div class="stat-title">
                Total Employees
            </div>

            <div class="stat-value">
                <%= totalEmployees %>
            </div>

        </div>


        <div class="stat-card">

            <div class="stat-title">
                Total Departments
            </div>

            <div class="stat-value">
                <%= totalDepartments %>
            </div>

        </div>


        <div class="stat-card">

            <div class="stat-title">
                Present Today
            </div>

            <div class="stat-value">
                <%= presentToday %>
            </div>

        </div>


        <div class="stat-card">

            <div class="stat-title">
                Absent Today
            </div>

            <div class="stat-value">
                <%= absentToday %>
            </div>

        </div>


        <div class="stat-card">

            <div class="stat-title">
                Total Attendance Records
            </div>

            <div class="stat-value">
                <%= totalAttendanceRecords %>
            </div>

        </div>

    </div>


    <!-- NAVIGATION -->

    <div class="navigation">

        <% if (isAdmin) { %>

            <h2>
                Admin Controls
            </h2>

            <div class="nav-grid">

                <a href="employees" class="nav-btn">
                    Employees
                </a>

                <a href="departments" class="nav-btn">
                    Departments
                </a>

                <a href="attendance" class="nav-btn">
                    Attendance
                </a>

                <a href="users" class="nav-btn">
                    User Management
                </a>

                <a href="change-password" class="nav-btn">
                    Change Password
                </a>

                <a href="logout"
                   class="nav-btn logout">
                    Logout
                </a>

            </div>

        <% } else { %>

            <h2>
                Employee Controls
            </h2>

            <div class="nav-grid">

                <a href="my-profile" class="nav-btn">
                    My Profile
                </a>

                <a href="my-attendance" class="nav-btn">
                    My Attendance
                </a>

                <a href="change-password" class="nav-btn">
                    Change Password
                </a>

                <a href="logout"
                   class="nav-btn logout">
                    Logout
                </a>

            </div>

        <% } %>

    </div>

</div>

</body>

</html>