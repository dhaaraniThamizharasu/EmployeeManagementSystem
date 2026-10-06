<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.employeemanagement.model.Attendance" %>
<%@ page import="com.employeemanagement.model.Employee" %>

<%
List<Attendance> attendanceList =
(List<Attendance>) request.getAttribute("attendanceList");

List<Employee> employees =
        (List<Employee>) request.getAttribute("employees");

Attendance editAttendance =
        (Attendance) request.getAttribute("editAttendance");

%>

<!DOCTYPE html>

<html lang="en">

<head>

<meta charset="UTF-8">

<meta name="viewport"
      content="width=device-width, initial-scale=1.0">

<title>Attendance - Employee Management System</title>

<link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
    rel="stylesheet">

</head>

<body class="bg-light">

<nav class="navbar navbar-dark bg-primary">

<div class="container">

    <span class="navbar-brand">
        Employee Management System
    </span>

    <a href="dashboard.jsp"
       class="btn btn-light">
        Dashboard
    </a>

</div>

</nav>

<div class="container mt-5">

<div class="text-center mb-4">

    <h1>Attendance Management</h1>

    <p class="text-muted">
        Add, view, edit and delete employee attendance
    </p>

</div>

<%
    String error = request.getParameter("error");

    if (error != null) {
%>

    <div class="alert alert-danger">

        Attendance operation failed.
        Please check the entered information.

    </div>

<%
    }
%>

<div class="card shadow mb-5">

    <div class="card-header bg-primary text-white">

        <h4 class="mb-0">

            <%
                if (editAttendance == null) {
            %>

                Mark Attendance

            <%
                } else {
            %>

                Edit Attendance

            <%
                }
            %>

        </h4>

    </div>

    <div class="card-body">

        <form method="post"
              action="attendance">

            <%
                if (editAttendance != null) {
            %>

                <input
                    type="hidden"
                    name="action"
                    value="update">

                <input
                    type="hidden"
                    name="attendanceId"
                    value="<%= editAttendance.getAttendanceId() %>">

            <%
                } else {
            %>

                <input
                    type="hidden"
                    name="action"
                    value="add">

            <%
                }
            %>

            <div class="row g-3">

                <div class="col-md-6">

                    <label class="form-label">
                        Employee
                    </label>

                    <select
                        name="employeeId"
                        class="form-select"
                        required>

                        <option value="">
                            Select Employee
                        </option>

                        <%
                            if (employees != null) {

                                for (Employee employee :
                                        employees) {

                                    boolean selected = false;

                                    if (editAttendance != null &&
                                        editAttendance.getEmployeeId()
                                        == employee.getEmployeeId()) {

                                        selected = true;
                                    }
                        %>

                            <option
                                value="<%= employee.getEmployeeId() %>"
                                <%= selected ? "selected" : "" %>>

                                <%= employee.getEmployeeCode() %>
                                -
                                <%= employee.getFirstName() %>
                                <%= employee.getLastName() == null
                                        ? ""
                                        : employee.getLastName() %>

                            </option>

                        <%
                                }
                            }
                        %>

                    </select>

                </div>

                <div class="col-md-6">

                    <label class="form-label">
                        Attendance Date
                    </label>

                    <input
                        type="date"
                        name="attendanceDate"
                        class="form-control"
                        required
                        value="<%= editAttendance != null
                                && editAttendance.getAttendanceDate() != null
                                ? editAttendance.getAttendanceDate()
                                : "" %>">

                </div>

                <div class="col-md-4">

                    <label class="form-label">
                        Status
                    </label>

                    <select
                        name="status"
                        class="form-select"
                        required>

                        <option
                            value="Present"
                            <%= editAttendance != null
                                && "Present".equals(
                                    editAttendance.getStatus())
                                ? "selected"
                                : "" %>>

                            Present

                        </option>

                        <option
                            value="Absent"
                            <%= editAttendance != null
                                && "Absent".equals(
                                    editAttendance.getStatus())
                                ? "selected"
                                : "" %>>

                            Absent

                        </option>

                        <option
                            value="Leave"
                            <%= editAttendance != null
                                && "Leave".equals(
                                    editAttendance.getStatus())
                                ? "selected"
                                : "" %>>

                            Leave

                        </option>

                    </select>

                </div>

                <div class="col-md-4">

                    <label class="form-label">
                        Check In
                    </label>

                    <input
                        type="time"
                        name="checkIn"
                        class="form-control"
                        value="<%= editAttendance != null
                                && editAttendance.getCheckIn() != null
                                ? editAttendance.getCheckIn().toString().substring(0, 5)
                                : "" %>">

                </div>

                <div class="col-md-4">

                    <label class="form-label">
                        Check Out
                    </label>

                    <input
                        type="time"
                        name="checkOut"
                        class="form-control"
                        value="<%= editAttendance != null
                                && editAttendance.getCheckOut() != null
                                ? editAttendance.getCheckOut().toString().substring(0, 5)
                                : "" %>">

                </div>

            </div>

            <div class="mt-4">

                <%
                    if (editAttendance == null) {
                %>

                    <button
                        type="submit"
                        class="btn btn-primary">

                        Mark Attendance

                    </button>

                <%
                    } else {
                %>

                    <button
                        type="submit"
                        class="btn btn-success">

                        Update Attendance

                    </button>

                    <a
                        href="attendance"
                        class="btn btn-secondary">

                        Cancel

                    </a>

                <%
                    }
                %>

            </div>

        </form>

    </div>

</div>

<div class="card shadow">

    <div class="card-header bg-dark text-white">

        <h4 class="mb-0">
            Attendance Records
        </h4>

    </div>

    <div class="card-body">

        <div class="table-responsive">

            <table class="table table-bordered table-striped">

                <thead class="table-dark">

                    <tr>

                        <th>ID</th>
                        <th>Employee Code</th>
                        <th>Employee Name</th>
                        <th>Date</th>
                        <th>Status</th>
                        <th>Check In</th>
                        <th>Check Out</th>
                        <th>Actions</th>

                    </tr>

                </thead>

                <tbody>

                <%
                    if (attendanceList != null &&
                        !attendanceList.isEmpty()) {

                        for (Attendance attendance :
                                attendanceList) {
                %>

                    <tr>

                        <td>
                            <%= attendance.getAttendanceId() %>
                        </td>

                        <td>
                            <%= attendance.getEmployeeCode() %>
                        </td>

                        <td>
                            <%= attendance.getEmployeeName() %>
                        </td>

                        <td>
                            <%= attendance.getAttendanceDate() %>
                        </td>

                        <td>

                            <%
                                if ("Present".equals(
                                        attendance.getStatus())) {
                            %>

                                <span class="badge bg-success">
                                    Present
                                </span>

                            <%
                                } else if ("Absent".equals(
                                        attendance.getStatus())) {
                            %>

                                <span class="badge bg-danger">
                                    Absent
                                </span>

                            <%
                                } else {
                            %>

                                <span class="badge bg-warning text-dark">
                                    <%= attendance.getStatus() %>
                                </span>

                            <%
                                }
                            %>

                        </td>

                        <td>
                            <%= attendance.getCheckIn() == null
                                    ? "-"
                                    : attendance.getCheckIn()
                                                .toString()
                                                .substring(0, 5) %>
                        </td>

                        <td>
                            <%= attendance.getCheckOut() == null
                                    ? "-"
                                    : attendance.getCheckOut()
                                                .toString()
                                                .substring(0, 5) %>
                        </td>

                        <td>

                            <a
                                href="attendance?action=edit&id=<%= attendance.getAttendanceId() %>"
                                class="btn btn-sm btn-warning">

                                Edit

                            </a>

                            <a
                                href="attendance?action=delete&id=<%= attendance.getAttendanceId() %>"
                                class="btn btn-sm btn-danger"
                                onclick="return confirm('Are you sure you want to delete this attendance record?');">

                                Delete

                            </a>

                        </td>

                    </tr>

                <%
                        }

                    } else {
                %>

                    <tr>

                        <td
                            colspan="8"
                            class="text-center text-muted">

                            No attendance records found.

                        </td>

                    </tr>

                <%
                    }
                %>

                </tbody>

            </table>

        </div>

    </div>

</div>

<div class="text-center mt-4 mb-5">

    <a
        href="dashboard.jsp"
        class="btn btn-secondary">

        Back to Dashboard

    </a>

</div>

</div>

</body>

</html>
