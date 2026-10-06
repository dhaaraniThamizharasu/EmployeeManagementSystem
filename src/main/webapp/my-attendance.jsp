<%@ page import="java.util.List" %>
<%@ page import="com.employeemanagement.dao.AttendanceRecord" %>
<%@ page import="com.employeemanagement.model.User" %>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    User user =
            (User) session.getAttribute("loggedInUser");

    if (user == null) {
        response.sendRedirect("login.html");
        return;
    }

    List<AttendanceRecord> attendanceList =
            (List<AttendanceRecord>)
                    request.getAttribute("attendanceList");

    String errorMessage =
            (String) request.getAttribute("errorMessage");
%>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">

    <title>My Attendance</title>

    <style>

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f4f6f9;
            color: #333;
        }

        .navbar {
            background: #1f2937;
            padding: 18px 35px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .navbar h2 {
            color: white;
            margin: 0;
        }

        .nav-links {
            display: flex;
            gap: 10px;
        }

        .nav-links a {
            color: white;
            text-decoration: none;
            padding: 9px 15px;
            border-radius: 6px;
            background: #374151;
        }

        .nav-links a:hover {
            background: #4b5563;
        }

        .container {
            width: 90%;
            max-width: 1100px;
            margin: 40px auto;
        }

        .page-title {
            margin-bottom: 25px;
        }

        .page-title h1 {
            margin-bottom: 8px;
        }

        .page-title p {
            color: #666;
        }

        .card {
            background: white;
            border-radius: 10px;
            padding: 25px;
            box-shadow: 0 3px 12px rgba(0,0,0,0.08);
        }

        .employee-info {
            display: flex;
            gap: 30px;
            margin-bottom: 25px;
            flex-wrap: wrap;
        }

        .info-box {
            background: #f8fafc;
            padding: 15px 20px;
            border-radius: 8px;
            min-width: 180px;
        }

        .info-box strong {
            display: block;
            margin-bottom: 5px;
            color: #555;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th {
            background: #1f2937;
            color: white;
            padding: 13px;
            text-align: center;
        }

        td {
            padding: 13px;
            border-bottom: 1px solid #ddd;
            text-align: center;
        }

        tr:hover {
            background: #f8fafc;
        }

        .status {
            font-weight: bold;
        }

        .present {
            color: green;
        }

        .absent {
            color: red;
        }

        .leave {
            color: #d97706;
        }

        .empty {
            text-align: center;
            padding: 30px;
            color: #777;
        }

        .error {
            background: #fee2e2;
            color: #991b1b;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
        }

        .back-btn {
            display: inline-block;
            margin-top: 20px;
            text-decoration: none;
            background: #374151;
            color: white;
            padding: 10px 18px;
            border-radius: 6px;
        }

    </style>

</head>

<body>

<div class="navbar">

    <h2>Employee Management System</h2>

    <div class="nav-links">

        <a href="dashboard">Dashboard</a>

        <a href="my-profile">My Profile</a>

        <a href="logout">Logout</a>

    </div>

</div>


<div class="container">

    <div class="page-title">

        <h1>My Attendance</h1>

        <p>
            View your personal attendance records.
        </p>

    </div>


    <div class="card">

        <div class="employee-info">

            <div class="info-box">

                <strong>Username</strong>

                <%= user.getUsername() %>

            </div>


            <div class="info-box">

                <strong>Employee ID</strong>

                <%= user.getEmployeeId() %>

            </div>

        </div>


        <% if (errorMessage != null) { %>

            <div class="error">
                <%= errorMessage %>
            </div>

        <% } else if (attendanceList == null ||
                      attendanceList.isEmpty()) { %>

            <div class="empty">

                No attendance records found.

            </div>

        <% } else { %>

            <table>

                <thead>

                <tr>

                    <th>S.No</th>

                    <th>Date</th>

                    <th>Status</th>

                    <th>Check In</th>

                    <th>Check Out</th>

                </tr>

                </thead>

                <tbody>

                <%
                    int serialNumber = 1;

                    for (AttendanceRecord record :
                            attendanceList) {
                %>

                <tr>

                    <td>
                        <%= serialNumber++ %>
                    </td>

                    <td>
                        <%= record.getAttendanceDate() %>
                    </td>

                    <td class="status">

                        <%
                            String status =
                                    record.getStatus();

                            if (status != null &&
                                status.equalsIgnoreCase("PRESENT")) {
                        %>

                            <span class="present">
                                <%= status %>
                            </span>

                        <%
                            } else if (status != null &&
                                       status.equalsIgnoreCase("ABSENT")) {
                        %>

                            <span class="absent">
                                <%= status %>
                            </span>

                        <%
                            } else if (status != null &&
                                       status.equalsIgnoreCase("LEAVE")) {
                        %>

                            <span class="leave">
                                <%= status %>
                            </span>

                        <%
                            } else {
                        %>

                            <%= status %>

                        <%
                            }
                        %>

                    </td>

                    <td>
                        <%= record.getCheckIn() != null
                                ? record.getCheckIn()
                                : "-" %>
                    </td>

                    <td>
                        <%= record.getCheckOut() != null
                                ? record.getCheckOut()
                                : "-" %>
                    </td>

                </tr>

                <%
                    }
                %>

                </tbody>

            </table>

        <% } %>


        <a href="dashboard" class="back-btn">
            Back to Dashboard
        </a>

    </div>

</div>

</body>
</html>