package com.employeemanagement.dao;

import com.employeemanagement.model.Department;
import com.employeemanagement.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {

    private static final String INSERT_DEPARTMENT =
            "INSERT INTO departments (department_name, description) VALUES (?, ?)";

    private static final String SELECT_ALL_DEPARTMENTS =
            "SELECT department_id, department_name, description " +
            "FROM departments ORDER BY department_id";

    private static final String SELECT_DEPARTMENT_BY_ID =
            "SELECT department_id, department_name, description " +
            "FROM departments WHERE department_id = ?";

    private static final String UPDATE_DEPARTMENT =
            "UPDATE departments SET department_name = ?, description = ? " +
            "WHERE department_id = ?";

    private static final String DELETE_DEPARTMENT =
            "DELETE FROM departments WHERE department_id = ?";

    public boolean addDepartment(Department department) {

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(INSERT_DEPARTMENT)
        ) {

            statement.setString(1, department.getDepartmentName());
            statement.setString(2, department.getDescription());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public List<Department> getAllDepartments() {

        List<Department> departments = new ArrayList<>();

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(SELECT_ALL_DEPARTMENTS);
            ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Department department = new Department();

                department.setDepartmentId(
                        resultSet.getInt("department_id")
                );

                department.setDepartmentName(
                        resultSet.getString("department_name")
                );

                department.setDescription(
                        resultSet.getString("description")
                );

                departments.add(department);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return departments;
    }

    public Department getDepartmentById(int departmentId) {

        Department department = null;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(SELECT_DEPARTMENT_BY_ID)
        ) {

            statement.setInt(1, departmentId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    department = new Department();

                    department.setDepartmentId(
                            resultSet.getInt("department_id")
                    );

                    department.setDepartmentName(
                            resultSet.getString("department_name")
                    );

                    department.setDescription(
                            resultSet.getString("description")
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return department;
    }

    public boolean updateDepartment(Department department) {

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(UPDATE_DEPARTMENT)
        ) {

            statement.setString(1, department.getDepartmentName());
            statement.setString(2, department.getDescription());
            statement.setInt(3, department.getDepartmentId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDepartment(int departmentId) {

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(DELETE_DEPARTMENT)
        ) {

            statement.setInt(1, departmentId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
}