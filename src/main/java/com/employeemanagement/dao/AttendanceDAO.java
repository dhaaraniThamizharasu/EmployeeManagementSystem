package com.employeemanagement.dao;

import com.employeemanagement.model.Attendance;
import com.employeemanagement.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

private static final String INSERT_ATTENDANCE =
        "INSERT INTO attendance " +
        "(employee_id, attendance_date, status, check_in, check_out) " +
        "VALUES (?, ?, ?, ?, ?)";


private static final String SELECT_ALL_ATTENDANCE =
        "SELECT a.attendance_id, " +
        "a.employee_id, " +
        "a.attendance_date, " +
        "a.status, " +
        "a.check_in, " +
        "a.check_out, " +
        "e.employee_code, " +
        "CONCAT(e.first_name, ' ', " +
        "COALESCE(e.last_name, '')) AS employee_name " +
        "FROM attendance a " +
        "INNER JOIN employees e " +
        "ON a.employee_id = e.employee_id " +
        "ORDER BY a.attendance_date DESC, a.attendance_id DESC";


private static final String SELECT_ATTENDANCE_BY_ID =
        "SELECT a.attendance_id, " +
        "a.employee_id, " +
        "a.attendance_date, " +
        "a.status, " +
        "a.check_in, " +
        "a.check_out, " +
        "e.employee_code, " +
        "CONCAT(e.first_name, ' ', " +
        "COALESCE(e.last_name, '')) AS employee_name " +
        "FROM attendance a " +
        "INNER JOIN employees e " +
        "ON a.employee_id = e.employee_id " +
        "WHERE a.attendance_id = ?";


private static final String UPDATE_ATTENDANCE =
        "UPDATE attendance SET " +
        "employee_id = ?, " +
        "attendance_date = ?, " +
        "status = ?, " +
        "check_in = ?, " +
        "check_out = ? " +
        "WHERE attendance_id = ?";


private static final String DELETE_ATTENDANCE =
        "DELETE FROM attendance WHERE attendance_id = ?";


public boolean addAttendance(
        Attendance attendance) {

    try (
        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(
                        INSERT_ATTENDANCE
                )
    ) {

        statement.setInt(
                1,
                attendance.getEmployeeId()
        );

        statement.setDate(
                2,
                attendance.getAttendanceDate()
        );

        statement.setString(
                3,
                attendance.getStatus()
        );

        statement.setTime(
                4,
                attendance.getCheckIn()
        );

        statement.setTime(
                5,
                attendance.getCheckOut()
        );

        return statement.executeUpdate() > 0;

    } catch (Exception e) {

        e.printStackTrace();

        return false;
    }
}


public List<Attendance> getAllAttendance() {

    List<Attendance> attendanceList =
            new ArrayList<>();

    try (
        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(
                        SELECT_ALL_ATTENDANCE
                );

        ResultSet resultSet =
                statement.executeQuery()
    ) {

        while (resultSet.next()) {

            Attendance attendance =
                    new Attendance();

            attendance.setAttendanceId(
                    resultSet.getInt(
                            "attendance_id"
                    )
            );

            attendance.setEmployeeId(
                    resultSet.getInt(
                            "employee_id"
                    )
            );

            attendance.setAttendanceDate(
                    resultSet.getDate(
                            "attendance_date"
                    )
            );

            attendance.setStatus(
                    resultSet.getString(
                            "status"
                    )
            );

            attendance.setCheckIn(
                    resultSet.getTime(
                            "check_in"
                    )
            );

            attendance.setCheckOut(
                    resultSet.getTime(
                            "check_out"
                    )
            );

            attendance.setEmployeeCode(
                    resultSet.getString(
                            "employee_code"
                    )
            );

            attendance.setEmployeeName(
                    resultSet.getString(
                            "employee_name"
                    )
            );

            attendanceList.add(
                    attendance
            );
        }

    } catch (Exception e) {

        e.printStackTrace();
    }

    return attendanceList;
}


public Attendance getAttendanceById(
        int attendanceId) {

    Attendance attendance = null;

    try (
        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(
                        SELECT_ATTENDANCE_BY_ID
                )
    ) {

        statement.setInt(
                1,
                attendanceId
        );

        try (
            ResultSet resultSet =
                    statement.executeQuery()
        ) {

            if (resultSet.next()) {

                attendance =
                        new Attendance();

                attendance.setAttendanceId(
                        resultSet.getInt(
                                "attendance_id"
                        )
                );

                attendance.setEmployeeId(
                        resultSet.getInt(
                                "employee_id"
                        )
                );

                attendance.setAttendanceDate(
                        resultSet.getDate(
                                "attendance_date"
                        )
                );

                attendance.setStatus(
                        resultSet.getString(
                                "status"
                        )
                );

                attendance.setCheckIn(
                        resultSet.getTime(
                                "check_in"
                        )
                );

                attendance.setCheckOut(
                        resultSet.getTime(
                                "check_out"
                        )
                );

                attendance.setEmployeeCode(
                        resultSet.getString(
                                "employee_code"
                        )
                );

                attendance.setEmployeeName(
                        resultSet.getString(
                                "employee_name"
                        )
                );
            }
        }

    } catch (Exception e) {

        e.printStackTrace();
    }

    return attendance;
}


public boolean updateAttendance(
        Attendance attendance) {

    try (
        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(
                        UPDATE_ATTENDANCE
                )
    ) {

        statement.setInt(
                1,
                attendance.getEmployeeId()
        );

        statement.setDate(
                2,
                attendance.getAttendanceDate()
        );

        statement.setString(
                3,
                attendance.getStatus()
        );

        statement.setTime(
                4,
                attendance.getCheckIn()
        );

        statement.setTime(
                5,
                attendance.getCheckOut()
        );

        statement.setInt(
                6,
                attendance.getAttendanceId()
        );

        return statement.executeUpdate() > 0;

    } catch (Exception e) {

        e.printStackTrace();

        return false;
    }
}


public boolean deleteAttendance(
        int attendanceId) {

    try (
        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(
                        DELETE_ATTENDANCE
                )
    ) {

        statement.setInt(
                1,
                attendanceId
        );

        return statement.executeUpdate() > 0;

    } catch (Exception e) {

        e.printStackTrace();

        return false;
    }
}

}
