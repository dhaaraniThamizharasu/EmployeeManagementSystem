<%@ page import="com.employeemanagement.model.User" %>

<%
    User user =
            (User) session.getAttribute("loggedInUser");

    if (user == null) {
        response.sendRedirect("login.html");
        return;
    }

    String errorMessage =
            (String) request.getAttribute("errorMessage");

    String successMessage =
            (String) request.getAttribute("successMessage");
%>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">

    <title>Change Password</title>

    <style>

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f4f7fb;
            color: #333;
        }

        .navbar {
            background: #1f2937;
            padding: 18px 40px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .navbar h2 {
            color: white;
            margin: 0;
        }

        .navbar a {
            color: white;
            text-decoration: none;
            margin-left: 20px;
            font-weight: bold;
        }

        .navbar a:hover {
            text-decoration: underline;
        }

        .container {
            width: 450px;
            max-width: 90%;
            margin: 60px auto;
        }

        .card {
            background: white;
            padding: 35px;
            border-radius: 12px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.10);
        }

        .card h1 {
            text-align: center;
            margin-top: 0;
            margin-bottom: 10px;
        }

        .username {
            text-align: center;
            color: #666;
            margin-bottom: 30px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
        }

        input {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 7px;
            font-size: 15px;
        }

        input:focus {
            outline: none;
            border-color: #2563eb;
        }

        .btn {
            width: 100%;
            padding: 13px;
            border: none;
            border-radius: 7px;
            background: #2563eb;
            color: white;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
        }

        .btn:hover {
            background: #1d4ed8;
        }

        .message {
            padding: 12px;
            border-radius: 7px;
            margin-bottom: 20px;
            text-align: center;
        }

        .error {
            background: #fee2e2;
            color: #991b1b;
        }

        .success {
            background: #dcfce7;
            color: #166534;
        }

        .links {
            text-align: center;
            margin-top: 25px;
        }

        .links a {
            color: #2563eb;
            text-decoration: none;
            margin: 0 8px;
            font-weight: bold;
        }

        .links a:hover {
            text-decoration: underline;
        }

        .note {
            margin-top: 20px;
            padding: 12px;
            background: #f3f4f6;
            border-radius: 7px;
            font-size: 13px;
            color: #555;
        }

    </style>

</head>

<body>

<div class="navbar">

    <h2>Employee Management System</h2>

    <div>
        <a href="dashboard">Dashboard</a>
        <a href="logout">Logout</a>
    </div>

</div>


<div class="container">

    <div class="card">

        <h1>Change Password</h1>

        <div class="username">
            Logged in as:
            <strong><%= user.getUsername() %></strong>
        </div>


        <% if (errorMessage != null) { %>

            <div class="message error">
                <%= errorMessage %>
            </div>

        <% } %>


        <% if (successMessage != null) { %>

            <div class="message success">
                <%= successMessage %>
            </div>

        <% } %>


        <form action="change-password" method="post">

            <div class="form-group">

                <label for="currentPassword">
                    Current Password
                </label>

                <input
                    type="password"
                    id="currentPassword"
                    name="currentPassword"
                    placeholder="Enter current password"
                    required
                >

            </div>


            <div class="form-group">

                <label for="newPassword">
                    New Password
                </label>

                <input
                    type="password"
                    id="newPassword"
                    name="newPassword"
                    placeholder="Enter new password"
                    minlength="6"
                    required
                >

            </div>


            <div class="form-group">

                <label for="confirmPassword">
                    Confirm New Password
                </label>

                <input
                    type="password"
                    id="confirmPassword"
                    name="confirmPassword"
                    placeholder="Confirm new password"
                    minlength="6"
                    required
                >

            </div>


            <button type="submit" class="btn">
                Change Password
            </button>

        </form>


        <div class="note">
            Password must contain at least 6 characters.
        </div>


        <div class="links">

            <a href="dashboard">
                Back to Dashboard
            </a>

            <% if (user.getRole().equalsIgnoreCase("EMPLOYEE")) { %>

                <a href="my-profile">
                    My Profile
                </a>

            <% } %>

        </div>

    </div>

</div>

</body>
</html>