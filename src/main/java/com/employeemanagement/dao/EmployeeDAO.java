package com.employeemanagement.dao;

import com.employeemanagement.model.Employee;
import com.employeemanagement.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

private static final String INSERT_EMPLOYEE =
        "INSERT INTO employees " +
        "(employee_code, first_name, last_name, email, phone, " +
        "job_title, department_id, date_of_joining, salary, status) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

private static final String SELECT_ALL_EMPLOYEES =
        "SELECT e.employee_id, " +
        "e.employee_code, " +
        "e.first_name, " +
        "e.last_name, " +
        "e.email, " +
        "e.phone, " +
        "e.job_title, " +
        "e.department_id, " +
        "e.date_of_joining, " +
        "e.salary, " +
        "e.status, " +
        "d.department_name " +
        "FROM employees e " +
        "LEFT JOIN departments d " +
        "ON e.department_id = d.department_id " +
        "ORDER BY e.employee_id DESC";

private static final String SELECT_EMPLOYEE_BY_ID =
        "SELECT e.employee_id, " +
        "e.employee_code, " +
        "e.first_name, " +
        "e.last_name, " +
        "e.email, " +
        "e.phone, " +
        "e.job_title, " +
        "e.department_id, " +
        "e.date_of_joining, " +
        "e.salary, " +
        "e.status, " +
        "d.department_name " +
        "FROM employees e " +
        "LEFT JOIN departments d " +
        "ON e.department_id = d.department_id " +
        "WHERE e.employee_id = ?";

private static final String SEARCH_EMPLOYEES =
        "SELECT e.employee_id, " +
        "e.employee_code, " +
        "e.first_name, " +
        "e.last_name, " +
        "e.email, " +
        "e.phone, " +
        "e.job_title, " +
        "e.department_id, " +
        "e.date_of_joining, " +
        "e.salary, " +
        "e.status, " +
        "d.department_name " +
        "FROM employees e " +
        "LEFT JOIN departments d " +
        "ON e.department_id = d.department_id " +
        "WHERE LOWER(e.employee_code) LIKE ? " +
        "OR LOWER(e.first_name) LIKE ? " +
        "OR LOWER(COALESCE(e.last_name, '')) LIKE ? " +
        "OR LOWER(e.email) LIKE ? " +
        "OR LOWER(COALESCE(e.job_title, '')) LIKE ? " +
        "ORDER BY e.employee_id DESC";

private static final String UPDATE_EMPLOYEE =
        "UPDATE employees SET " +
        "employee_code = ?, " +
        "first_name = ?, " +
        "last_name = ?, " +
        "email = ?, " +
        "phone = ?, " +
        "job_title = ?, " +
        "department_id = ?, " +
        "date_of_joining = ?, " +
        "salary = ?, " +
        "status = ? " +
        "WHERE employee_id = ?";

private static final String DELETE_EMPLOYEE =
        "DELETE FROM employees WHERE employee_id = ?";


public boolean addEmployee(Employee employee) {

    try (
        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(
                        INSERT_EMPLOYEE
                )
    ) {

        statement.setString(
                1,
                employee.getEmployeeCode()
        );

        statement.setString(
                2,
                employee.getFirstName()
        );

        statement.setString(
                3,
                employee.getLastName()
        );

        statement.setString(
                4,
                employee.getEmail()
        );

        statement.setString(
                5,
                employee.getPhone()
        );

        statement.setString(
                6,
                employee.getJobTitle()
        );

        if (employee.getDepartmentId() > 0) {

            statement.setInt(
                    7,
                    employee.getDepartmentId()
            );

        } else {

            statement.setNull(
                    7,
                    java.sql.Types.INTEGER
            );
        }

        statement.setDate(
                8,
                employee.getDateOfJoining()
        );

        statement.setDouble(
                9,
                employee.getSalary()
        );

        statement.setString(
                10,
                employee.getStatus()
        );

        return statement.executeUpdate() > 0;

    } catch (Exception e) {

        e.printStackTrace();

        return false;
    }
}


public List<Employee> getAllEmployees() {

    List<Employee> employeeList =
            new ArrayList<>();

    try (
        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(
                        SELECT_ALL_EMPLOYEES
                );

        ResultSet resultSet =
                statement.executeQuery()
    ) {

        while (resultSet.next()) {

            employeeList.add(
                    createEmployeeFromResultSet(
                            resultSet
                    )
            );
        }

    } catch (Exception e) {

        e.printStackTrace();
    }

    return employeeList;
}


public List<Employee> searchEmployees(
        String searchTerm) {

    List<Employee> employeeList =
            new ArrayList<>();

    try (
        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(
                        SEARCH_EMPLOYEES
                )
    ) {

        String searchValue =
                "%" +
                searchTerm.toLowerCase().trim() +
                "%";

        statement.setString(1, searchValue);
        statement.setString(2, searchValue);
        statement.setString(3, searchValue);
        statement.setString(4, searchValue);
        statement.setString(5, searchValue);

        try (
            ResultSet resultSet =
                    statement.executeQuery()
        ) {

            while (resultSet.next()) {

                employeeList.add(
                        createEmployeeFromResultSet(
                                resultSet
                        )
                );
            }
        }

    } catch (Exception e) {

        e.printStackTrace();
    }

    return employeeList;
}


public Employee getEmployeeById(
        int employeeId) {

    Employee employee = null;

    try (
        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(
                        SELECT_EMPLOYEE_BY_ID
                )
    ) {

        statement.setInt(
                1,
                employeeId
        );

        try (
            ResultSet resultSet =
                    statement.executeQuery()
        ) {

            if (resultSet.next()) {

                employee =
                        createEmployeeFromResultSet(
                                resultSet
                        );
            }
        }

    } catch (Exception e) {

        e.printStackTrace();
    }

    return employee;
}


public boolean updateEmployee(
        Employee employee) {

    try (
        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(
                        UPDATE_EMPLOYEE
                )
    ) {

        statement.setString(
                1,
                employee.getEmployeeCode()
        );

        statement.setString(
                2,
                employee.getFirstName()
        );

        statement.setString(
                3,
                employee.getLastName()
        );

        statement.setString(
                4,
                employee.getEmail()
        );

        statement.setString(
                5,
                employee.getPhone()
        );

        statement.setString(
                6,
                employee.getJobTitle()
        );

        if (employee.getDepartmentId() > 0) {

            statement.setInt(
                    7,
                    employee.getDepartmentId()
            );

        } else {

            statement.setNull(
                    7,
                    java.sql.Types.INTEGER
            );
        }

        statement.setDate(
                8,
                employee.getDateOfJoining()
        );

        statement.setDouble(
                9,
                employee.getSalary()
        );

        statement.setString(
                10,
                employee.getStatus()
        );

        statement.setInt(
                11,
                employee.getEmployeeId()
        );

        return statement.executeUpdate() > 0;

    } catch (Exception e) {

        e.printStackTrace();

        return false;
    }
}


public boolean deleteEmployee(
        int employeeId) {

    try (
        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(
                        DELETE_EMPLOYEE
                )
    ) {

        statement.setInt(
                1,
                employeeId
        );

        return statement.executeUpdate() > 0;

    } catch (Exception e) {

        e.printStackTrace();

        return false;
    }
}


private Employee createEmployeeFromResultSet(
        ResultSet resultSet)
        throws Exception {

    Employee employee =
            new Employee();

    employee.setEmployeeId(
            resultSet.getInt(
                    "employee_id"
            )
    );

    employee.setEmployeeCode(
            resultSet.getString(
                    "employee_code"
            )
    );

    employee.setFirstName(
            resultSet.getString(
                    "first_name"
            )
    );

    employee.setLastName(
            resultSet.getString(
                    "last_name"
            )
    );

    employee.setEmail(
            resultSet.getString(
                    "email"
            )
    );

    employee.setPhone(
            resultSet.getString(
                    "phone"
            )
    );

    employee.setJobTitle(
            resultSet.getString(
                    "job_title"
            )
    );

    employee.setDepartmentId(
            resultSet.getInt(
                    "department_id"
            )
    );

    employee.setDateOfJoining(
            resultSet.getDate(
                    "date_of_joining"
            )
    );

    employee.setSalary(
            resultSet.getDouble(
                    "salary"
            )
    );

    employee.setStatus(
            resultSet.getString(
                    "status"
            )
    );

    employee.setDepartmentName(
            resultSet.getString(
                    "department_name"
            )
    );

    return employee;
}

}
