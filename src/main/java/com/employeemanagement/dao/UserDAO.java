package com.employeemanagement.dao;

import com.employeemanagement.model.User;
import com.employeemanagement.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final String LOGIN_QUERY =
            "SELECT user_id, username, password, role, employee_id " +
            "FROM users " +
            "WHERE username = ? AND password = ?";

    public User login(String username, String password) {

        User user = null;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(LOGIN_QUERY)
        ) {

            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    user = new User();

                    user.setUserId(
                            resultSet.getInt("user_id")
                    );

                    user.setUsername(
                            resultSet.getString("username")
                    );

                    user.setPassword(
                            resultSet.getString("password")
                    );

                    user.setRole(
                            resultSet.getString("role")
                    );

                    user.setEmployeeId(
                            resultSet.getInt("employee_id")
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return user;
    }

    public boolean changePassword(
            int userId,
            String currentPassword,
            String newPassword) {

        String checkPasswordQuery =
                "SELECT user_id FROM users " +
                "WHERE user_id = ? AND password = ?";

        String updatePasswordQuery =
                "UPDATE users SET password = ? " +
                "WHERE user_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement checkStatement =
                        connection.prepareStatement(
                                checkPasswordQuery
                        )
        ) {

            checkStatement.setInt(1, userId);
            checkStatement.setString(2, currentPassword);

            try (ResultSet resultSet =
                         checkStatement.executeQuery()) {

                if (!resultSet.next()) {
                    return false;
                }
            }

            try (
                    PreparedStatement updateStatement =
                            connection.prepareStatement(
                                    updatePasswordQuery
                            )
            ) {

                updateStatement.setString(1, newPassword);
                updateStatement.setInt(2, userId);

                return updateStatement.executeUpdate() > 0;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public List<User> getAllUsers() {

        List<User> userList =
                new ArrayList<>();

        String sql =
                "SELECT user_id, username, password, role, employee_id " +
                "FROM users " +
                "ORDER BY user_id";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql);
                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                User user = new User();

                user.setUserId(
                        resultSet.getInt("user_id")
                );

                user.setUsername(
                        resultSet.getString("username")
                );

                user.setPassword(
                        resultSet.getString("password")
                );

                user.setRole(
                        resultSet.getString("role")
                );

                user.setEmployeeId(
                        resultSet.getInt("employee_id")
                );

                userList.add(user);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return userList;
    }

    public List<EmployeeOption> getAvailableEmployees() {

        List<EmployeeOption> employeeList =
                new ArrayList<>();

        String sql =
                "SELECT e.employee_id, e.employee_code, " +
                "e.first_name, e.last_name " +
                "FROM employees e " +
                "LEFT JOIN users u " +
                "ON e.employee_id = u.employee_id " +
                "WHERE u.employee_id IS NULL " +
                "ORDER BY e.employee_id";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql);
                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                EmployeeOption employee =
                        new EmployeeOption();

                employee.setEmployeeId(
                        resultSet.getInt("employee_id")
                );

                employee.setEmployeeCode(
                        resultSet.getString("employee_code")
                );

                employee.setFirstName(
                        resultSet.getString("first_name")
                );

                employee.setLastName(
                        resultSet.getString("last_name")
                );

                employeeList.add(employee);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return employeeList;
    }

    public boolean employeeAlreadyHasUser(
            int employeeId) {

        String sql =
                "SELECT user_id FROM users " +
                "WHERE employee_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, employeeId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                return resultSet.next();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public boolean createUser(
            String username,
            String password,
            String role,
            int employeeId) {

        if (employeeId <= 0) {
            return false;
        }

        if (employeeAlreadyHasUser(employeeId)) {
            return false;
        }

        String sql =
                "INSERT INTO users " +
                "(username, password, role, employee_id) " +
                "VALUES (?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);
            statement.setInt(4, employeeId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public boolean updateUser(
            int userId,
            String username,
            String role,
            int employeeId) {

        if (userId <= 0 ||
                username == null ||
                username.trim().isEmpty() ||
                role == null ||
                role.trim().isEmpty() ||
                employeeId <= 0) {

            return false;
        }

        String usernameCheck =
                "SELECT user_id FROM users " +
                "WHERE username = ? AND user_id <> ?";

        String employeeCheck =
                "SELECT user_id FROM users " +
                "WHERE employee_id = ? AND user_id <> ?";

        String updateQuery =
                "UPDATE users " +
                "SET username = ?, role = ?, employee_id = ? " +
                "WHERE user_id = ?";

        try (
                Connection connection =
                        DBConnection.getConnection()
        ) {

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    usernameCheck
                            )
            ) {

                statement.setString(1, username);
                statement.setInt(2, userId);

                try (
                        ResultSet resultSet =
                                statement.executeQuery()
                ) {

                    if (resultSet.next()) {
                        return false;
                    }
                }
            }

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    employeeCheck
                            )
            ) {

                statement.setInt(1, employeeId);
                statement.setInt(2, userId);

                try (
                        ResultSet resultSet =
                                statement.executeQuery()
                ) {

                    if (resultSet.next()) {
                        return false;
                    }
                }
            }

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    updateQuery
                            )
            ) {

                statement.setString(1, username.trim());
                statement.setString(2, role);
                statement.setInt(3, employeeId);
                statement.setInt(4, userId);

                return statement.executeUpdate() > 0;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteUser(int userId) {

        String sql =
                "DELETE FROM users WHERE user_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
}