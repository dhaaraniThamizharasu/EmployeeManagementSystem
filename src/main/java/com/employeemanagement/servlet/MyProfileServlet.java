package com.employeemanagement.servlet;

import com.employeemanagement.dao.EmployeeDAO;
import com.employeemanagement.model.Employee;
import com.employeemanagement.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/my-profile")
public class MyProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private EmployeeDAO employeeDAO;

    @Override
    public void init() throws ServletException {
        employeeDAO = new EmployeeDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null ||
                session.getAttribute("loggedInUser") == null) {

            response.sendRedirect("login.html");
            return;
        }

        User user =
                (User) session.getAttribute("loggedInUser");

        int employeeId =
                user.getEmployeeId();

        if (employeeId <= 0) {

            request.setAttribute(
                    "errorMessage",
                    "No employee profile is linked to this account."
            );

            request.getRequestDispatcher(
                    "my-profile.jsp"
            ).forward(request, response);

            return;
        }

        Employee employee =
                employeeDAO.getEmployeeById(employeeId);

        if (employee == null) {

            request.setAttribute(
                    "errorMessage",
                    "Employee profile not found."
            );

        } else {

            request.setAttribute(
                    "employee",
                    employee
            );
        }

        request.getRequestDispatcher(
                "my-profile.jsp"
        ).forward(request, response);
    }
}