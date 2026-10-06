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

@WebServlet("/change-password")
public class ChangePasswordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
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

        request.getRequestDispatcher(
                "change-password.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(
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

        int userId =
                user.getUserId();

        String currentPassword =
                request.getParameter("currentPassword");

        String newPassword =
                request.getParameter("newPassword");

        String confirmPassword =
                request.getParameter("confirmPassword");

        if (currentPassword == null ||
                newPassword == null ||
                confirmPassword == null ||
                currentPassword.trim().isEmpty() ||
                newPassword.trim().isEmpty() ||
                confirmPassword.trim().isEmpty()) {

            request.setAttribute(
                    "errorMessage",
                    "All password fields are required."
            );

            request.getRequestDispatcher(
                    "change-password.jsp"
            ).forward(request, response);

            return;
        }

        if (!newPassword.equals(confirmPassword)) {

            request.setAttribute(
                    "errorMessage",
                    "New password and confirm password do not match."
            );

            request.getRequestDispatcher(
                    "change-password.jsp"
            ).forward(request, response);

            return;
        }

        if (newPassword.length() < 6) {

            request.setAttribute(
                    "errorMessage",
                    "New password must contain at least 6 characters."
            );

            request.getRequestDispatcher(
                    "change-password.jsp"
            ).forward(request, response);

            return;
        }

        if (currentPassword.equals(newPassword)) {

            request.setAttribute(
                    "errorMessage",
                    "New password must be different from the current password."
            );

            request.getRequestDispatcher(
                    "change-password.jsp"
            ).forward(request, response);

            return;
        }

        boolean changed =
                userDAO.changePassword(
                        userId,
                        currentPassword,
                        newPassword
                );

        if (changed) {

            user.setPassword(newPassword);

            session.setAttribute(
                    "loggedInUser",
                    user
            );

            request.setAttribute(
                    "successMessage",
                    "Password changed successfully."
            );

        } else {

            request.setAttribute(
                    "errorMessage",
                    "Current password is incorrect."
            );
        }

        request.getRequestDispatcher(
                "change-password.jsp"
        ).forward(request, response);
    }
}