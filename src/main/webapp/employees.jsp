<%@ page import="java.util.List" %>
<%@ page import="com.employeemanagement.model.Employee" %>
<%@ page import="com.employeemanagement.model.Department" %>

<%
    String message = (String) request.getAttribute("message");
    String errorMessage = (String) request.getAttribute("errorMessage");

    List<Employee> employees =
            (List<Employee>) request.getAttribute("employees");

    List<Department> departments =
            (List<Department>) request.getAttribute("departments");

    boolean searchPerformed =
            "search".equals(request.getParameter("action"));
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Management</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f9;
            margin: 0;
            padding: 0;
        }

        .navbar {
            background-color: #343a40;
            color: white;
            padding: 15px 25px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .navbar h2 {
            margin: 0;
        }

        .navbar a {
            color: white;
            text-decoration: none;
            margin-left: 15px;
        }

        .container {
            width: 95%;
            margin: 25px auto;
        }

        .card {
            background: white;
            padding: 20px;
            margin-bottom: 25px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        h2 {
            margin-top: 0;
            color: #333;
        }

        label {
            display: block;
            margin-top: 10px;
            font-weight: bold;
        }

        input, select {
            width: 100%;
            padding: 9px;
            margin-top: 5px;
            box-sizing: border-box;
        }

        button {
            margin-top: 15px;
            padding: 10px 18px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            background-color: #007bff;
            color: white;
        }

        button:hover {
            background-color: #0056b3;
        }

        .search-box {
            display: flex;
            gap: 10px;
            align-items: end;
        }

        .search-box input {
            flex: 1;
        }

        .search-box button {
            width: 120px;
        }

        .message {
            padding: 12px;
            background-color: #d4edda;
            color: #155724;
            margin-bottom: 15px;
            border-radius: 5px;
        }

        .error {
            padding: 12px;
            background-color: #f8d7da;
            color: #721c24;
            margin-bottom: 15px;
            border-radius: 5px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        table th,
        table td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: center;
        }

        table th {
            background-color: #343a40;
            color: white;
        }

        .edit {
            background-color: #ffc107;
            color: black;
            padding: 6px 10px;
            text-decoration: none;
            border-radius: 4px;
        }

        .delete {
            background-color: #dc3545;
            color: white;
            padding: 6px 10px;
            text-decoration: none;
            border-radius: 4px;
        }
    </style>
</head>

<body>

<div class="navbar">
    <h2>Employee Management System</h2>

    <div>
        <a href="dashboard">Dashboard</a>
        <a href="departments">Departments</a>
        <a href="attendance">Attendance</a>
        <a href="logout">Logout</a>
    </div>
</div>

<div class="container">

    <% if (message != null) { %>
        <div class="message">
            <%= message %>
        </div>
    <% } %>

    <% if (errorMessage != null) { %>
        <div class="error">
            <%= errorMessage %>
        </div>
    <% } %>


    <!-- SEARCH EMPLOYEE -->

    <div class="card">

        <h2>Search Employee</h2>

        <form action="employees" method="get">

            <input type="hidden"
                   name="action"
                   value="search">

            <div class="search-box">

                <div style="flex: 1;">
                    <label>Search by Employee Code, Name or Email</label>

                    <input type="text"
                           name="keyword"
                           placeholder="Enter employee code, name or email"
                           required>
                </div>

                <button type="submit">
                    Search
                </button>

            </div>

        </form>

    </div>


    <!-- ADD / EDIT EMPLOYEE -->

    <div class="card">

        <h2>Add / Edit Employee</h2>

        <form action="employees" method="post">

            <input type="hidden"
                   name="action"
                   value="save">

            <label>Employee ID</label>

            <input type="text"
                   name="employeeId"
                   placeholder="Leave empty when adding a new employee">

            <label>Employee Code</label>

            <input type="text"
                   name="employeeCode"
                   required>

            <label>First Name</label>

            <input type="text"
                   name="firstName"
                   required>

            <label>Last Name</label>

            <input type="text"
                   name="lastName">

            <label>Email</label>

            <input type="email"
                   name="email"
                   required>

            <label>Phone</label>

            <input type="text"
                   name="phone">

            <label>Job Title</label>

            <input type="text"
                   name="jobTitle">

            <label>Department</label>

            <select name="departmentId">

                <option value="">
                    Select Department
                </option>

                <% if (departments != null) {
                    for (Department department : departments) {
                %>

                    <option value="<%= department.getDepartmentId() %>">
                        <%= department.getDepartmentName() %>
                    </option>

                <%  }
                   }
                %>

            </select>

            <label>Date of Joining</label>

            <input type="date"
                   name="dateOfJoining">

            <label>Salary</label>

            <input type="number"
                   step="0.01"
                   name="salary">

            <label>Status</label>

            <select name="status">

                <option value="Active">
                    Active
                </option>

                <option value="Inactive">
                    Inactive
                </option>

            </select>

            <button type="submit">
                Save Employee
            </button>

        </form>

    </div>


    <!-- EMPLOYEE DETAILS ONLY AFTER SEARCH -->

    <% if (searchPerformed) { %>

    <div class="card">

        <h2>Employee Details</h2>

        <% if (employees != null && !employees.isEmpty()) { %>

        <table>

            <thead>

            <tr>
                <th>ID</th>
                <th>Employee Code</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Job Title</th>
                <th>Department</th>
                <th>Date of Joining</th>
                <th>Salary</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>

            </thead>

            <tbody>

            <% for (Employee employee : employees) { %>

            <tr>

                <td>
                    <%= employee.getEmployeeId() %>
                </td>

                <td>
                    <%= employee.getEmployeeCode() %>
                </td>

                <td>
                    <%= employee.getFirstName() %>
                </td>

                <td>
                    <%= employee.getLastName() %>
                </td>

                <td>
                    <%= employee.getEmail() %>
                </td>

                <td>
                    <%= employee.getPhone() %>
                </td>

                <td>
                    <%= employee.getJobTitle() %>
                </td>

                <td>
                    <%= employee.getDepartmentName() %>
                </td>

                <td>
                    <%= employee.getDateOfJoining() %>
                </td>

                <td>
                    <%= employee.getSalary() %>
                </td>

                <td>
                    <%= employee.getStatus() %>
                </td>

                <td>

                    <a class="edit"
                       href="employees?action=edit&id=<%= employee.getEmployeeId() %>">
                        Edit
                    </a>

                    <a class="delete"
                       href="employees?action=delete&id=<%= employee.getEmployeeId() %>"
                       onclick="return confirm('Are you sure you want to delete this employee?');">
                        Delete
                    </a>

                </td>

            </tr>

            <% } %>

            </tbody>

        </table>

        <% } else { %>

            <p>No employees found for your search.</p>

        <% } %>

    </div>

    <% } %>

</div>

</body>
</html>