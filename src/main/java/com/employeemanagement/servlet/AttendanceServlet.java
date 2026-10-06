package com.employeemanagement.servlet;

import com.employeemanagement.dao.AttendanceDAO;
import com.employeemanagement.dao.EmployeeDAO;
import com.employeemanagement.model.Attendance;
import com.employeemanagement.model.Employee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@WebServlet("/attendance")
public class AttendanceServlet extends HttpServlet {

private static final long serialVersionUID = 1L;

private AttendanceDAO attendanceDAO;
private EmployeeDAO employeeDAO;

@Override
public void init() throws ServletException {
    attendanceDAO = new AttendanceDAO();
    employeeDAO = new EmployeeDAO();
}

@Override
protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {

    String action = request.getParameter("action");

    if (action == null || action.equals("list")) {

        listAttendance(request, response);

    } else if (action.equals("edit")) {

        showEditForm(request, response);

    } else if (action.equals("delete")) {

        deleteAttendance(request, response);

    } else {

        listAttendance(request, response);
    }
}

@Override
protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");

    String action = request.getParameter("action");

    if ("add".equals(action)) {

        addAttendance(request, response);

    } else if ("update".equals(action)) {

        updateAttendance(request, response);

    } else {

        listAttendance(request, response);
    }
}

private void listAttendance(
        HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {

    List<Attendance> attendanceList =
            attendanceDAO.getAllAttendance();

    List<Employee> employees =
            employeeDAO.getAllEmployees();

    request.setAttribute(
            "attendanceList",
            attendanceList
    );

    request.setAttribute(
            "employees",
            employees
    );

    request.getRequestDispatcher(
            "/attendance.jsp"
    ).forward(request, response);
}

private void addAttendance(
        HttpServletRequest request,
        HttpServletResponse response)
        throws IOException {

    try {

        int employeeId =
                Integer.parseInt(
                        request.getParameter("employeeId")
                );

        String attendanceDate =
                request.getParameter("attendanceDate");

        String status =
                request.getParameter("status");

        String checkIn =
                request.getParameter("checkIn");

        String checkOut =
                request.getParameter("checkOut");

        Attendance attendance =
                new Attendance();

        attendance.setEmployeeId(employeeId);

        attendance.setAttendanceDate(
                Date.valueOf(attendanceDate)
        );

        attendance.setStatus(status);

        if (checkIn != null &&
                !checkIn.trim().isEmpty()) {

            attendance.setCheckIn(
                    Time.valueOf(checkIn + ":00")
            );

        } else {

            attendance.setCheckIn(null);
        }

        if (checkOut != null &&
                !checkOut.trim().isEmpty()) {

            attendance.setCheckOut(
                    Time.valueOf(checkOut + ":00")
            );

        } else {

            attendance.setCheckOut(null);
        }

        boolean success =
                attendanceDAO.addAttendance(
                        attendance
                );

        if (success) {

            response.sendRedirect(
                    "attendance"
            );

        } else {

            response.sendRedirect(
                    "attendance?error=add"
            );
        }

    } catch (Exception e) {

        e.printStackTrace();

        response.sendRedirect(
                "attendance?error=add"
        );
    }
}

private void showEditForm(
        HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {

    try {

        String idParameter =
                request.getParameter("id");

        if (idParameter == null ||
                idParameter.trim().isEmpty()) {

            response.sendRedirect("attendance");
            return;
        }

        int attendanceId =
                Integer.parseInt(idParameter);

        Attendance attendance =
                attendanceDAO.getAttendanceById(
                        attendanceId
                );

        if (attendance == null) {

            response.sendRedirect("attendance");
            return;
        }

        List<Attendance> attendanceList =
                attendanceDAO.getAllAttendance();

        List<Employee> employees =
                employeeDAO.getAllEmployees();

        request.setAttribute(
                "attendanceList",
                attendanceList
        );

        request.setAttribute(
                "employees",
                employees
        );

        request.setAttribute(
                "editAttendance",
                attendance
        );

        request.getRequestDispatcher(
                "/attendance.jsp"
        ).forward(
                request,
                response
        );

    } catch (Exception e) {

        e.printStackTrace();

        response.sendRedirect(
                "attendance?error=edit"
        );
    }
}

private void updateAttendance(
        HttpServletRequest request,
        HttpServletResponse response)
        throws IOException {

    try {

        String attendanceIdParameter =
                request.getParameter("attendanceId");

        String employeeIdParameter =
                request.getParameter("employeeId");

        String attendanceDate =
                request.getParameter("attendanceDate");

        String status =
                request.getParameter("status");

        String checkIn =
                request.getParameter("checkIn");

        String checkOut =
                request.getParameter("checkOut");

        if (attendanceIdParameter == null ||
                attendanceIdParameter.trim().isEmpty()) {

            response.sendRedirect(
                    "attendance?error=update"
            );

            return;
        }

        if (employeeIdParameter == null ||
                employeeIdParameter.trim().isEmpty()) {

            response.sendRedirect(
                    "attendance?error=update"
            );

            return;
        }

        if (attendanceDate == null ||
                attendanceDate.trim().isEmpty()) {

            response.sendRedirect(
                    "attendance?error=update"
            );

            return;
        }

        int attendanceId =
                Integer.parseInt(
                        attendanceIdParameter
                );

        int employeeId =
                Integer.parseInt(
                        employeeIdParameter
                );

        Attendance attendance =
                new Attendance();

        attendance.setAttendanceId(
                attendanceId
        );

        attendance.setEmployeeId(
                employeeId
        );

        attendance.setAttendanceDate(
                Date.valueOf(attendanceDate)
        );

        attendance.setStatus(status);

        if (checkIn != null &&
                !checkIn.trim().isEmpty()) {

            attendance.setCheckIn(
                    Time.valueOf(checkIn + ":00")
            );

        } else {

            attendance.setCheckIn(null);
        }

        if (checkOut != null &&
                !checkOut.trim().isEmpty()) {

            attendance.setCheckOut(
                    Time.valueOf(checkOut + ":00")
            );

        } else {

            attendance.setCheckOut(null);
        }

        boolean success =
                attendanceDAO.updateAttendance(
                        attendance
                );

        if (success) {

            response.sendRedirect(
                    "attendance"
            );

        } else {

            response.sendRedirect(
                    "attendance?error=update"
            );
        }

    } catch (Exception e) {

        e.printStackTrace();

        response.sendRedirect(
                "attendance?error=update"
        );
    }
}

private void deleteAttendance(
        HttpServletRequest request,
        HttpServletResponse response)
        throws IOException {

    try {

        String idParameter =
                request.getParameter("id");

        if (idParameter == null ||
                idParameter.trim().isEmpty()) {

            response.sendRedirect("attendance");
            return;
        }

        int attendanceId =
                Integer.parseInt(idParameter);

        boolean success =
                attendanceDAO.deleteAttendance(
                        attendanceId
                );

        if (success) {

            response.sendRedirect(
                    "attendance"
            );

        } else {

            response.sendRedirect(
                    "attendance?error=delete"
            );
        }

    } catch (Exception e) {

        e.printStackTrace();

        response.sendRedirect(
                "attendance?error=delete"
        );
    }
}

}
