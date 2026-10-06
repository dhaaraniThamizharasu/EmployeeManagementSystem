package com.employeemanagement.servlet;

import com.employeemanagement.dao.EmployeeOption;
import com.employeemanagement.dao.UserDAO;
import com.employeemanagement.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {

        userDAO = new UserDAO();
    }

    private boolean isAdmin(HttpSession session) {

        if (session == null) {
            return false;
        }

        User user =
                (User) session.getAttribute("loggedInUser");

        return user != null &&
                "ADMIN".equalsIgnoreCase(user.getRole());
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (!isAdmin(session)) {

            response.sendRedirect("dashboard");
            return;
        }

        loadUsersPage(request, response);
    }

    private void loadUsersPage(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List<User> userList =
                userDAO.getAllUsers();

        List<EmployeeOption> availableEmployees =
                userDAO.getAvailableEmployees();

        request.setAttribute(
                "userList",
                userList
        );

        request.setAttribute(
                "availableEmployees",
                availableEmployees
        );

        request.getRequestDispatcher(
                "users.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (!isAdmin(session)) {

            response.sendRedirect("dashboard");
            return;
        }

        String action =
                request.getParameter("action");

        if ("create".equals(action)) {

            createUser(request, response);

        } else if ("edit".equals(action)) {

            editUser(request, response);

        } else if ("delete".equals(action)) {

            deleteUser(request, response);

        } else {

            response.sendRedirect("users");
        }
    }

    private void createUser(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String username =
                request.getParameter("username");

        String password =
                request.getParameter("password");

        String role =
                request.getParameter("role");

        String employeeIdText =
                request.getParameter("employeeId");

        if (username == null ||
                password == null ||
                role == null ||
                employeeIdText == null ||
                username.trim().isEmpty() ||
                password.trim().isEmpty() ||
                role.trim().isEmpty() ||
                employeeIdText.trim().isEmpty()) {

            response.sendRedirect(
                    "users?error=Please+fill+all+required+fields"
            );

            return;
        }

        if (password.length() < 6) {

            response.sendRedirect(
                    "users?error=Password+must+contain+at+least+6+characters"
            );

            return;
        }

        int employeeId;

        try {

            employeeId =
                    Integer.parseInt(
                            employeeIdText
                    );

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    "users?error=Invalid+employee+selection"
            );

            return;
        }

        if (employeeId <= 0) {

            response.sendRedirect(
                    "users?error=Please+select+an+employee"
            );

            return;
        }

        if (userDAO.employeeAlreadyHasUser(employeeId)) {

            response.sendRedirect(
                    "users?error=This+employee+already+has+a+user+account"
            );

            return;
        }

        boolean created =
                userDAO.createUser(
                        username.trim(),
                        password,
                        role,
                        employeeId
                );

        if (created) {

            response.sendRedirect(
                    "users?success=User+created+successfully"
            );

        } else {

            response.sendRedirect(
                    "users?error=Could+not+create+user.+Username+may+already+exist"
            );
        }
    }

    private void editUser(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String userIdText =
                request.getParameter("userId");

        String username =
                request.getParameter("username");

        String role =
                request.getParameter("role");

        String employeeIdText =
                request.getParameter("employeeId");

        if (userIdText == null ||
                username == null ||
                role == null ||
                employeeIdText == null ||
                username.trim().isEmpty() ||
                role.trim().isEmpty() ||
                employeeIdText.trim().isEmpty()) {

            response.sendRedirect(
                    "users?error=Please+fill+all+required+fields"
            );

            return;
        }

        int userId;
        int employeeId;

        try {

            userId =
                    Integer.parseInt(userIdText);

            employeeId =
                    Integer.parseInt(employeeIdText);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    "users?error=Invalid+user+or+employee+ID"
            );

            return;
        }

        if (userId <= 0 ||
                employeeId <= 0) {

            response.sendRedirect(
                    "users?error=Invalid+user+or+employee"
            );

            return;
        }

        boolean updated =
                userDAO.updateUser(
                        userId,
                        username.trim(),
                        role,
                        employeeId
                );

        if (updated) {

            HttpSession session =
                    request.getSession(false);

            User loggedInUser =
                    (User) session.getAttribute(
                            "loggedInUser"
                    );

            if (loggedInUser != null &&
                    loggedInUser.getUserId() == userId) {

                loggedInUser.setUsername(
                        username.trim()
                );

                loggedInUser.setRole(role);

                loggedInUser.setEmployeeId(
                        employeeId
                );

                session.setAttribute(
                        "loggedInUser",
                        loggedInUser
                );
            }

            response.sendRedirect(
                    "users?success=User+updated+successfully"
            );

        } else {

            response.sendRedirect(
                    "users?error=Could+not+update+user.+Username+or+employee+may+already+be+used"
            );
        }
    }

    private void deleteUser(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String userIdText =
                request.getParameter("userId");

        try {

            int userId =
                    Integer.parseInt(userIdText);

            HttpSession session =
                    request.getSession(false);

            User loggedInUser =
                    (User) session.getAttribute(
                            "loggedInUser"
                    );

            if (loggedInUser.getUserId() == userId) {

                response.sendRedirect(
                        "users?error=You+cannot+delete+your+own+account"
                );

                return;
            }

            boolean deleted =
                    userDAO.deleteUser(userId);

            if (deleted) {

                response.sendRedirect(
                        "users?success=User+deleted+successfully"
                );

            } else {

                response.sendRedirect(
                        "users?error=User+could+not+be+deleted"
                );
            }

        } catch (Exception e) {

            response.sendRedirect(
                    "users?error=Invalid+user+ID"
            );
        }
    }
}