package com.employeemanagement.servlet;

import com.employeemanagement.dao.DepartmentDAO;
import com.employeemanagement.dao.EmployeeDAO;
import com.employeemanagement.model.Department;
import com.employeemanagement.model.Employee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private EmployeeDAO employeeDAO;
    private DepartmentDAO departmentDAO;

    @Override
    public void init() throws ServletException {
        employeeDAO = new EmployeeDAO();
        departmentDAO = new DepartmentDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.equals("list")) {

            listEmployees(request, response);

        } else if (action.equals("search")) {

            searchEmployees(request, response);

        } else if (action.equals("edit")) {

            showEditForm(request, response);

        } else if (action.equals("delete")) {

            deleteEmployee(request, response);

        } else {

            listEmployees(request, response);
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

            addEmployee(request, response);

        } else if ("update".equals(action)) {

            updateEmployee(request, response);

        } else if ("save".equals(action)) {

            saveEmployee(request, response);

        } else {

            listEmployees(request, response);
        }
    }

    private void listEmployees(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List<Employee> employeeList =
                employeeDAO.getAllEmployees();

        List<Department> departments =
                departmentDAO.getAllDepartments();

        request.setAttribute(
                "employeeList",
                employeeList
        );

        request.setAttribute(
                "employees",
                employeeList
        );

        request.setAttribute(
                "departments",
                departments
        );

        request.getRequestDispatcher(
                "/employees.jsp"
        ).forward(
                request,
                response
        );
    }

    private void searchEmployees(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String search =
                request.getParameter("keyword");

        if (search == null) {
            search = "";
        }

        search = search.trim();

        List<Employee> employeeList =
                employeeDAO.searchEmployees(search);

        List<Department> departments =
                departmentDAO.getAllDepartments();

        request.setAttribute(
                "employeeList",
                employeeList
        );

        request.setAttribute(
                "employees",
                employeeList
        );

        request.setAttribute(
                "departments",
                departments
        );

        request.setAttribute(
                "searchTerm",
                search
        );

        request.getRequestDispatcher(
                "/employees.jsp"
        ).forward(
                request,
                response
        );
    }

    private void addEmployee(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            Employee employee =
                    createEmployeeFromRequest(request);

            boolean success =
                    employeeDAO.addEmployee(employee);

            if (success) {

                response.sendRedirect("employees");

            } else {

                response.sendRedirect(
                        "employees?error=add"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "employees?error=add"
            );
        }
    }

    private void saveEmployee(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            String employeeId =
                    request.getParameter("employeeId");

            Employee employee =
                    createEmployeeFromRequest(request);

            if (employeeId == null ||
                    employeeId.trim().isEmpty()) {

                boolean success =
                        employeeDAO.addEmployee(employee);

                if (success) {

                    response.sendRedirect("employees");

                } else {

                    response.sendRedirect(
                            "employees?error=add"
                    );
                }

            } else {

                employee.setEmployeeId(
                        Integer.parseInt(
                                employeeId
                        )
                );

                boolean success =
                        employeeDAO.updateEmployee(employee);

                if (success) {

                    response.sendRedirect("employees");

                } else {

                    response.sendRedirect(
                            "employees?error=update"
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "employees?error=save"
            );
        }
    }

    private void showEditForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int employeeId =
                    Integer.parseInt(
                            request.getParameter("id")
                    );

            Employee employee =
                    employeeDAO.getEmployeeById(employeeId);

            List<Employee> employeeList =
                    employeeDAO.getAllEmployees();

            List<Department> departments =
                    departmentDAO.getAllDepartments();

            request.setAttribute(
                    "employeeList",
                    employeeList
            );

            request.setAttribute(
                    "employees",
                    employeeList
            );

            request.setAttribute(
                    "departments",
                    departments
            );

            request.setAttribute(
                    "editEmployee",
                    employee
            );

            request.getRequestDispatcher(
                    "/employees.jsp"
            ).forward(
                    request,
                    response
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect("employees");
        }
    }

    private void updateEmployee(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            Employee employee =
                    createEmployeeFromRequest(request);

            employee.setEmployeeId(
                    Integer.parseInt(
                            request.getParameter(
                                    "employeeId"
                            )
                    )
            );

            boolean success =
                    employeeDAO.updateEmployee(employee);

            if (success) {

                response.sendRedirect("employees");

            } else {

                response.sendRedirect(
                        "employees?error=update"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "employees?error=update"
            );
        }
    }

    private void deleteEmployee(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int employeeId =
                    Integer.parseInt(
                            request.getParameter("id")
                    );

            boolean success =
                    employeeDAO.deleteEmployee(employeeId);

            if (success) {

                response.sendRedirect("employees");

            } else {

                response.sendRedirect(
                        "employees?error=delete"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "employees?error=delete"
            );
        }
    }

    private Employee createEmployeeFromRequest(
            HttpServletRequest request) {

        Employee employee =
                new Employee();

        employee.setEmployeeCode(
                request.getParameter("employeeCode")
        );

        employee.setFirstName(
                request.getParameter("firstName")
        );

        employee.setLastName(
                request.getParameter("lastName")
        );

        employee.setEmail(
                request.getParameter("email")
        );

        employee.setPhone(
                request.getParameter("phone")
        );

        employee.setJobTitle(
                request.getParameter("jobTitle")
        );

        String departmentId =
                request.getParameter("departmentId");

        if (departmentId != null &&
                !departmentId.trim().isEmpty()) {

            employee.setDepartmentId(
                    Integer.parseInt(departmentId)
            );

        } else {

            employee.setDepartmentId(0);
        }

        String dateOfJoining =
                request.getParameter("dateOfJoining");

        if (dateOfJoining != null &&
                !dateOfJoining.trim().isEmpty()) {

            employee.setDateOfJoining(
                    Date.valueOf(dateOfJoining)
            );
        }

        String salary =
                request.getParameter("salary");

        if (salary != null &&
                !salary.trim().isEmpty()) {

            employee.setSalary(
                    Double.parseDouble(salary)
            );
        }

        employee.setStatus(
                request.getParameter("status")
        );

        return employee;
    }
}