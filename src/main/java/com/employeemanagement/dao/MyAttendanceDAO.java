package com.employeemanagement.dao;

import com.employeemanagement.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MyAttendanceDAO {

    public List<AttendanceRecord> getAttendanceByEmployeeId(int employeeId) {

        List<AttendanceRecord> attendanceList =
                new ArrayList<>();

        String sql =
                "SELECT attendance_id, employee_id, attendance_date, " +
                "status, check_in, check_out " +
                "FROM attendance " +
                "WHERE employee_id = ? " +
                "ORDER BY attendance_date DESC";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, employeeId);

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    AttendanceRecord record =
                            new AttendanceRecord();

                    record.setAttendanceId(
                            resultSet.getInt("attendance_id")
                    );

                    record.setEmployeeId(
                            resultSet.getInt("employee_id")
                    );

                    record.setAttendanceDate(
                            resultSet.getDate("attendance_date")
                    );

                    record.setStatus(
                            resultSet.getString("status")
                    );

                    record.setCheckIn(
                            resultSet.getTime("check_in")
                    );

                    record.setCheckOut(
                            resultSet.getTime("check_out")
                    );

                    attendanceList.add(record);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return attendanceList;
    }
}