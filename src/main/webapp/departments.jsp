<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.employeemanagement.model.Department" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Departments - Employee Management System</title>

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">

</head>

<body>

    <nav class="navbar navbar-dark bg-primary">

        <div class="container">

            <span class="navbar-brand mb-0 h1">
                Employee Management System
            </span>

        </div>

    </nav>

    <div class="container mt-5">

        <div class="d-flex justify-content-between align-items-center mb-4">

            <h2>Departments</h2>

            <a href="index.html"
               class="btn btn-secondary">
                Home
            </a>

        </div>

        <div class="card shadow">

            <div class="card-body">

                <div class="table-responsive">

                    <table class="table table-bordered table-hover">

                        <thead class="table-primary">

                            <tr>

                                <th>ID</th>

                                <th>Department Name</th>

                                <th>Description</th>

                            </tr>

                        </thead>

                        <tbody>

                            <%
                                List<Department> departments =
                                        (List<Department>)
                                        request.getAttribute("departments");

                                if (departments != null &&
                                    !departments.isEmpty()) {

                                    for (Department department : departments) {
                            %>

                            <tr>

                                <td>
                                    <%= department.getDepartmentId() %>
                                </td>

                                <td>
                                    <%= department.getDepartmentName() %>
                                </td>

                                <td>
                                    <%= department.getDescription() %>
                                </td>

                            </tr>

                            <%
                                    }

                                } else {
                            %>

                            <tr>

                                <td colspan="3"
                                    class="text-center">
                                    No departments found.
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

    </div>

</body>

</html>