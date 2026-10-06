package com.employeemanagement.servlet;

import com.employeemanagement.dao.DashboardDAO;
import com.employeemanagement.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

private static final long serialVersionUID = 1L;

private DashboardDAO dashboardDAO;

@Override
public void init() throws ServletException {

    dashboardDAO = new DashboardDAO();
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

    User loggedInUser =
            (User) session.getAttribute("loggedInUser");

    int totalEmployees =
            dashboardDAO.getTotalEmployees();

    int totalDepartments =
            dashboardDAO.getTotalDepartments();

    int presentToday =
            dashboardDAO.getPresentToday();

    int absentToday =
            dashboardDAO.getAbsentToday();

    int totalAttendanceRecords =
            dashboardDAO.getTotalAttendanceRecords();

    request.setAttribute(
            "loggedInUser",
            loggedInUser
    );

    request.setAttribute(
            "totalEmployees",
            totalEmployees
    );

    request.setAttribute(
            "totalDepartments",
            totalDepartments
    );

    request.setAttribute(
            "presentToday",
            presentToday
    );

    request.setAttribute(
            "absentToday",
            absentToday
    );

    request.setAttribute(
            "totalAttendanceRecords",
            totalAttendanceRecords
    );

    request.getRequestDispatcher(
            "/dashboard.jsp"
    ).forward(
            request,
            response
    );
}

}
