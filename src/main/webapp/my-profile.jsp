<%@ page import="com.employeemanagement.model.Employee" %>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    Employee employee =
            (Employee) request.getAttribute("employee");

    String errorMessage =
            (String) request.getAttribute("errorMessage");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>My Profile - Employee Management System</title>

    <style>

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            font-family: Arial, sans-serif;
            background: #f4f6f9;
            color: #333;
        }

        .navbar {
            background: #1f2937;
            color: white;
            padding: 16px 35px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex-wrap: wrap;
            gap: 15px;
        }

        .navbar h1 {
            font-size: 22px;
        }

        .nav-links {
            display: flex;
            gap: 10px;
            align-items: center;
        }

        .nav-links a {
            text-decoration: none;
            color: white;
            background: #2563eb;
            padding: 8px 14px;
            border-radius: 5px;
            font-size: 14px;
        }

        .nav-links a:hover {
            background: #1d4ed8;
        }

        .logout {
            background: #dc2626 !important;
        }

        .logout:hover {
            background: #b91c1c !important;
        }

        .container {
            width: 90%;
            max-width: 900px;
            margin: 35px auto;
        }

        .card {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
        }

        .card h2 {
            color: #1f2937;
            margin-bottom: 8px;
        }

        .subtitle {
            color: #666;
            margin-bottom: 25px;
        }

        .details {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 18px;
        }

        .detail-box {
            border: 1px solid #e5e7eb;
            border-radius: 7px;
            padding: 16px;
        }

        .detail-box label {
            display: block;
            font-size: 13px;
            color: #666;
            margin-bottom: 7px;
        }

        .detail-box span {
            font-size: 16px;
            font-weight: bold;
            color: #1f2937;
        }

        .error {
            background: #fee2e2;
            color: #991b1b;
            border: 1px solid #fecaca;
            padding: 15px;
            border-radius: 7px;
        }

        footer {
            text-align: center;
            padding: 25px;
            color: #777;
            font-size: 13px;
        }

        @media (max-width: 650px) {

            .details {
                grid-template-columns: 1fr;
            }

            .navbar {
                padding: 15px 20px;
            }

            .container {
                width: 94%;
            }

        }

    </style>

</head>

<body>

    <div class="navbar">

        <h1>Employee Management System</h1>

        <div class="nav-links">

            <a href="dashboard">
                Dashboard
            </a>

            <a href="logout" class="logout">
                Logout
            </a>

        </div>

    </div>


    <div class="container">

        <div class="card">

            <h2>My Profile</h2>

            <p class="subtitle">
                View your employee information
            </p>


            <% if (errorMessage != null) { %>

                <div class="error">
                    <%= errorMessage %>
                </div>

            <% } else if (employee != null) { %>

                <div class="details">

                    <div class="detail-box">
                        <label>Employee ID</label>
                        <span>
                            <%= employee.getEmployeeId() %>
                        </span>
                    </div>

                    <div class="detail-box">
                        <label>Employee Code</label>
                        <span>
                            <%= employee.getEmployeeCode() %>
                        </span>
                    </div>

                    <div class="detail-box">
                        <label>First Name</label>
                        <span>
                            <%= employee.getFirstName() %>
                        </span>
                    </div>

                    <div class="detail-box">
                        <label>Last Name</label>
                        <span>
                            <%= employee.getLastName() %>
                        </span>
                    </div>

                    <div class="detail-box">
                        <label>Email</label>
                        <span>
                            <%= employee.getEmail() %>
                        </span>
                    </div>

                    <div class="detail-box">
                        <label>Phone</label>
                        <span>
                            <%= employee.getPhone() %>
                        </span>
                    </div>

                    <div class="detail-box">
                        <label>Job Title</label>
                        <span>
                            <%= employee.getJobTitle() %>
                        </span>
                    </div>

                    <div class="detail-box">
                        <label>Department</label>
                        <span>
                            <%= employee.getDepartmentName() %>
                        </span>
                    </div>

                    <div class="detail-box">
                        <label>Date of Joining</label>
                        <span>
                            <%= employee.getDateOfJoining() %>
                        </span>
                    </div>

                    <div class="detail-box">
                        <label>Status</label>
                        <span>
                            <%= employee.getStatus() %>
                        </span>
                    </div>

                </div>

            <% } %>

        </div>

    </div>


    <footer>

        Employee Management System &copy; 2026

    </footer>

</body>

</html>