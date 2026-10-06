package com.employeemanagement.dao;

import com.employeemanagement.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardDAO {

public int getTotalEmployees() {

    String sql =
            "SELECT COUNT(*) FROM employees";

    try (
        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(sql);

        ResultSet resultSet =
                statement.executeQuery()
    ) {

        if (resultSet.next()) {
            return resultSet.getInt(1);
        }

    } catch (Exception e) {

        e.printStackTrace();
    }

    return 0;
}

public int getTotalDepartments() {

    String sql =
            "SELECT COUNT(*) FROM departments";

    try (
        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(sql);

        ResultSet resultSet =
                statement.executeQuery()
    ) {

        if (resultSet.next()) {
            return resultSet.getInt(1);
        }

    } catch (Exception e) {

        e.printStackTrace();
    }

    return 0;
}

public int getPresentToday() {

    String sql =
            "SELECT COUNT(*) " +
            "FROM attendance " +
            "WHERE attendance_date = CURDATE() " +
            "AND status = 'Present'";

    try (
        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(sql);

        ResultSet resultSet =
                statement.executeQuery()
    ) {

        if (resultSet.next()) {
            return resultSet.getInt(1);
        }

    } catch (Exception e) {

        e.printStackTrace();
    }

    return 0;
}

public int getAbsentToday() {

    String sql =
            "SELECT COUNT(*) " +
            "FROM attendance " +
            "WHERE attendance_date = CURDATE() " +
            "AND status = 'Absent'";

    try (
        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(sql);

        ResultSet resultSet =
                statement.executeQuery()
    ) {

        if (resultSet.next()) {
            return resultSet.getInt(1);
        }

    } catch (Exception e) {

        e.printStackTrace();
    }

    return 0;
}

public int getTotalAttendanceRecords() {

    String sql =
            "SELECT COUNT(*) FROM attendance";

    try (
        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(sql);

        ResultSet resultSet =
                statement.executeQuery()
    ) {

        if (resultSet.next()) {
            return resultSet.getInt(1);
        }

    } catch (Exception e) {

        e.printStackTrace();
    }

    return 0;
}

}
