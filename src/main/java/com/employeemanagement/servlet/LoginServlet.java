package com.employeemanagement.servlet;

import com.employeemanagement.dao.UserDAO;
import com.employeemanagement.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String username =
                request.getParameter("username");

        String password =
                request.getParameter("password");

        User user =
                userDAO.login(username, password);

        if (user != null) {

            HttpSession session =
                    request.getSession();

            session.setAttribute(
                    "loggedInUser",
                    user
            );

            /*
             * IMPORTANT:
             *
             * Do not redirect directly to dashboard.jsp.
             *
             * The DashboardServlet must run first
             * so that the dashboard statistics are
             * loaded from MySQL.
             */
            response.sendRedirect("dashboard");

        } else {

            request.setAttribute(
                    "errorMessage",
                    "Invalid username or password."
            );

            request.getRequestDispatcher(
                    "login.html"
            ).forward(
                    request,
                    response
            );
        }
    }
}