<%@ page import="java.util.List" %>
<%@ page import="com.employeemanagement.model.User" %>
<%@ page import="com.employeemanagement.dao.EmployeeOption" %>

<%
    User loggedInUser =
            (User) session.getAttribute("loggedInUser");

    if (loggedInUser == null ||
            !"ADMIN".equalsIgnoreCase(loggedInUser.getRole())) {

        response.sendRedirect("login.html");
        return;
    }

    List<User> userList =
            (List<User>) request.getAttribute("userList");

    List<EmployeeOption> availableEmployees =
            (List<EmployeeOption>) request.getAttribute(
                    "availableEmployees"
            );

    String success =
            request.getParameter("success");

    String error =
            request.getParameter("error");
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>User Management</title>

    <style>

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f4f6f9;
        }

        .header {
            background: #1f2937;
            color: white;
            padding: 20px 40px;
        }

        .header h1 {
            margin: 0;
        }

        .container {
            width: 90%;
            max-width: 1200px;
            margin: 30px auto;
        }

        .card {
            background: white;
            padding: 25px;
            border-radius: 10px;
            margin-bottom: 25px;
            box-shadow: 0 3px 10px rgba(0,0,0,0.08);
        }

        h2 {
            margin-top: 0;
            color: #1f2937;
        }

        .form-row {
            display: flex;
            gap: 20px;
            flex-wrap: wrap;
        }

        .form-group {
            flex: 1;
            min-width: 220px;
        }

        label {
            display: block;
            margin-bottom: 7px;
            font-weight: bold;
        }

        input,
        select {
            width: 100%;
            padding: 11px;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-sizing: border-box;
        }

        button {
            border: none;
            padding: 11px 20px;
            border-radius: 6px;
            cursor: pointer;
            font-weight: bold;
        }

        .add-btn {
            background: #2563eb;
            color: white;
            margin-top: 20px;
        }

        .edit-btn {
            background: #f59e0b;
            color: white;
        }

        .delete-btn {
            background: #dc2626;
            color: white;
        }

        .cancel-btn {
            background: #6b7280;
            color: white;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th,
        td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }

        th {
            background: #1f2937;
            color: white;
        }

        .message-success {
            background: #dcfce7;
            color: #166534;
            padding: 12px;
            border-radius: 6px;
            margin-bottom: 20px;
        }

        .message-error {
            background: #fee2e2;
            color: #991b1b;
            padding: 12px;
            border-radius: 6px;
            margin-bottom: 20px;
        }

        .nav {
            margin-bottom: 20px;
        }

        .nav a {
            display: inline-block;
            text-decoration: none;
            background: #374151;
            color: white;
            padding: 10px 15px;
            border-radius: 6px;
            margin-right: 8px;
        }

        .edit-form {
            background: #fff7ed;
            padding: 20px;
            border-radius: 8px;
            margin-top: 15px;
            border: 1px solid #fed7aa;
        }

        .action-buttons {
            display: flex;
            gap: 8px;
            flex-wrap: wrap;
        }

    </style>

</head>

<body>

<div class="header">

    <h1>User Management</h1>

</div>

<div class="container">

    <div class="nav">

        <a href="dashboard">
            Dashboard
        </a>

        <a href="employees">
            Employees
        </a>

        <a href="logout">
            Logout
        </a>

    </div>

    <% if (success != null) { %>

        <div class="message-success">
            <%= success %>
        </div>

    <% } %>

    <% if (error != null) { %>

        <div class="message-error">
            <%= error %>
        </div>

    <% } %>


    <!-- ADD USER -->

    <div class="card">

        <h2>Add New User</h2>

        <% if (availableEmployees != null &&
                !availableEmployees.isEmpty()) { %>

            <form action="users" method="post">

                <input
                        type="hidden"
                        name="action"
                        value="create"
                >

                <div class="form-row">

                    <div class="form-group">

                        <label>
                            Username
                        </label>

                        <input
                                type="text"
                                name="username"
                                required
                        >

                    </div>

                    <div class="form-group">

                        <label>
                            Password
                        </label>

                        <input
                                type="password"
                                name="password"
                                minlength="6"
                                required
                        >

                    </div>

                    <div class="form-group">

                        <label>
                            Role
                        </label>

                        <select
                                name="role"
                                required
                        >

                            <option value="EMPLOYEE">
                                EMPLOYEE
                            </option>

                            <option value="ADMIN">
                                ADMIN
                            </option>

                        </select>

                    </div>

                    <div class="form-group">

                        <label>
                            Employee
                        </label>

                        <select
                                name="employeeId"
                                required
                        >

                            <option value="">
                                -- Select Employee --
                            </option>

                            <% for (EmployeeOption employee :
                                    availableEmployees) { %>

                                <option
                                        value="<%= employee.getEmployeeId() %>"
                                >

                                    <%= employee.getEmployeeCode() %>
                                    -
                                    <%= employee.getFullName() %>

                                </option>

                            <% } %>

                        </select>

                    </div>

                </div>

                <button
                        type="submit"
                        class="add-btn"
                >
                    Add User
                </button>

            </form>

        <% } else { %>

            <p>
                All existing employees already have user accounts.
                Add a new employee from the Employees page first.
            </p>

        <% } %>

    </div>


    <!-- USER LIST -->

    <div class="card">

        <h2>Existing Users</h2>

        <% if (userList != null &&
                !userList.isEmpty()) { %>

            <table>

                <tr>

                    <th>
                        User ID
                    </th>

                    <th>
                        Username
                    </th>

                    <th>
                        Role
                    </th>

                    <th>
                        Employee ID
                    </th>

                    <th>
                        Actions
                    </th>

                </tr>

                <% for (User user : userList) { %>

                    <tr>

                        <td>
                            <%= user.getUserId() %>
                        </td>

                        <td>
                            <%= user.getUsername() %>
                        </td>

                        <td>
                            <%= user.getRole() %>
                        </td>

                        <td>

                            <% if (user.getEmployeeId() > 0) { %>

                                <%= user.getEmployeeId() %>

                            <% } else { %>

                                Not Linked

                            <% } %>

                        </td>

                        <td>

                            <div class="action-buttons">

                                <button
                                        type="button"
                                        class="edit-btn"
                                        onclick="showEditForm(
                                            '<%= user.getUserId() %>',
                                            '<%= user.getUsername() %>',
                                            '<%= user.getRole() %>',
                                            '<%= user.getEmployeeId() %>'
                                        )"
                                >
                                    Edit
                                </button>

                                <% if (user.getUserId() !=
                                        loggedInUser.getUserId()) { %>

                                    <form
                                            action="users"
                                            method="post"
                                            style="display:inline;"
                                            onsubmit="return confirm(
                                                'Are you sure you want to delete this user?'
                                            );"
                                    >

                                        <input
                                                type="hidden"
                                                name="action"
                                                value="delete"
                                        >

                                        <input
                                                type="hidden"
                                                name="userId"
                                                value="<%= user.getUserId() %>"
                                        >

                                        <button
                                                type="submit"
                                                class="delete-btn"
                                        >
                                            Delete
                                        </button>

                                    </form>

                                <% } %>

                            </div>

                        </td>

                    </tr>

                <% } %>

            </table>

        <% } else { %>

            <p>
                No users found.
            </p>

        <% } %>


        <!-- EDIT USER FORM -->

        <div
                id="editSection"
                class="edit-form"
                style="display:none;"
        >

            <h2>
                Edit User
            </h2>

            <form
                    action="users"
                    method="post"
            >

                <input
                        type="hidden"
                        name="action"
                        value="edit"
                >

                <input
                        type="hidden"
                        name="userId"
                        id="editUserId"
                >

                <div class="form-row">

                    <div class="form-group">

                        <label>
                            Username
                        </label>

                        <input
                                type="text"
                                name="username"
                                id="editUsername"
                                required
                        >

                    </div>

                    <div class="form-group">

                        <label>
                            Role
                        </label>

                        <select
                                name="role"
                                id="editRole"
                                required
                        >

                            <option value="EMPLOYEE">
                                EMPLOYEE
                            </option>

                            <option value="ADMIN">
                                ADMIN
                            </option>

                        </select>

                    </div>

                    <div class="form-group">

                        <label>
                            Employee
                        </label>

                        <select
                                name="employeeId"
                                id="editEmployeeId"
                                required
                        >

                            <option value="">
                                -- Select Employee --
                            </option>

                            <% if (availableEmployees != null) {

                                for (EmployeeOption employee :
                                        availableEmployees) { %>

                                    <option
                                            value="<%= employee.getEmployeeId() %>"
                                    >

                                        <%= employee.getEmployeeCode() %>
                                        -
                                        <%= employee.getFullName() %>

                                    </option>

                                <% }
                            } %>

                        </select>

                    </div>

                </div>

                <br>

                <button
                        type="submit"
                        class="edit-btn"
                >
                    Update User
                </button>

                <button
                        type="button"
                        class="cancel-btn"
                        onclick="hideEditForm()"
                >
                    Cancel
                </button>

            </form>

        </div>

    </div>

</div>


<script>

    function showEditForm(
        userId,
        username,
        role,
        employeeId
    ) {

        document.getElementById(
            "editSection"
        ).style.display = "block";

        document.getElementById(
            "editUserId"
        ).value = userId;

        document.getElementById(
            "editUsername"
        ).value = username;

        document.getElementById(
            "editRole"
        ).value = role;

        var employeeSelect =
            document.getElementById(
                "editEmployeeId"
            );

        var optionExists = false;

        for (var i = 0;
             i < employeeSelect.options.length;
             i++) {

            if (employeeSelect.options[i].value ==
                    employeeId) {

                optionExists = true;

                break;
            }
        }

        if (!optionExists &&
                employeeId != "0") {

            var option =
                document.createElement("option");

            option.value = employeeId;

            option.text =
                "Current Employee (ID " +
                employeeId +
                ")";

            employeeSelect.add(option);
        }

        employeeSelect.value = employeeId;

        document.getElementById(
            "editSection"
        ).scrollIntoView({
            behavior: "smooth"
        });
    }


    function hideEditForm() {

        document.getElementById(
            "editSection"
        ).style.display = "none";
    }

</script>

</body>

</html>