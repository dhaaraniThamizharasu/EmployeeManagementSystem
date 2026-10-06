package com.employeemanagement.servlet;

import com.employeemanagement.dao.AttendanceRecord;
import com.employeemanagement.dao.MyAttendanceDAO;
import com.employeemanagement.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/my-attendance")
public class MyAttendanceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private MyAttendanceDAO attendanceDAO;

    @Override
    public void init() throws ServletException {
        attendanceDAO = new MyAttendanceDAO();
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
                    "my-attendance.jsp"
            ).forward(request, response);

            return;
        }

        List<AttendanceRecord> attendanceList =
                attendanceDAO.getAttendanceByEmployeeId(
                        employeeId
                );

        request.setAttribute(
                "attendanceList",
                attendanceList
        );

        request.getRequestDispatcher(
                "my-attendance.jsp"
        ).forward(request, response);
    }
}