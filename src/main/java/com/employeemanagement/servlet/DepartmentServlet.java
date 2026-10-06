package com.employeemanagement.servlet;

import com.employeemanagement.dao.DepartmentDAO;
import com.employeemanagement.model.Department;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/departments")
public class DepartmentServlet extends HttpServlet {

    private DepartmentDAO departmentDAO;

    @Override
    public void init() throws ServletException {
        departmentDAO = new DepartmentDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List<Department> departments =
                departmentDAO.getAllDepartments();

        request.setAttribute("departments", departments);

        request.getRequestDispatcher("/departments.jsp")
                .forward(request, response);
    }
}