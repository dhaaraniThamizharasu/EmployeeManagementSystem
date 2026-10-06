package com.employeemanagement.model;

import java.sql.Date;
import java.sql.Time;

public class Attendance {

private int attendanceId;
private int employeeId;
private Date attendanceDate;
private String status;
private Time checkIn;
private Time checkOut;

private String employeeCode;
private String employeeName;


public Attendance() {
}


public Attendance(
        int attendanceId,
        int employeeId,
        Date attendanceDate,
        String status,
        Time checkIn,
        Time checkOut) {

    this.attendanceId = attendanceId;
    this.employeeId = employeeId;
    this.attendanceDate = attendanceDate;
    this.status = status;
    this.checkIn = checkIn;
    this.checkOut = checkOut;
}


public int getAttendanceId() {
    return attendanceId;
}

public void setAttendanceId(int attendanceId) {
    this.attendanceId = attendanceId;
}


public int getEmployeeId() {
    return employeeId;
}

public void setEmployeeId(int employeeId) {
    this.employeeId = employeeId;
}


public Date getAttendanceDate() {
    return attendanceDate;
}

public void setAttendanceDate(Date attendanceDate) {
    this.attendanceDate = attendanceDate;
}


public String getStatus() {
    return status;
}

public void setStatus(String status) {
    this.status = status;
}


public Time getCheckIn() {
    return checkIn;
}

public void setCheckIn(Time checkIn) {
    this.checkIn = checkIn;
}


public Time getCheckOut() {
    return checkOut;
}

public void setCheckOut(Time checkOut) {
    this.checkOut = checkOut;
}


public String getEmployeeCode() {
    return employeeCode;
}

public void setEmployeeCode(String employeeCode) {
    this.employeeCode = employeeCode;
}


public String getEmployeeName() {
    return employeeName;
}

public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
}

}
