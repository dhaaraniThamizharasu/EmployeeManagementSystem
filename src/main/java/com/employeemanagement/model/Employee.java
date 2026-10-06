package com.employeemanagement.model;

import java.sql.Date;

public class Employee {

private int employeeId;
private String employeeCode;
private String firstName;
private String lastName;
private String email;
private String phone;
private String jobTitle;
private int departmentId;
private Date dateOfJoining;
private double salary;
private String status;

private String departmentName;

public Employee() {
}

public Employee(
        int employeeId,
        String employeeCode,
        String firstName,
        String lastName,
        String email,
        String phone,
        String jobTitle,
        int departmentId,
        Date dateOfJoining,
        double salary,
        String status) {

    this.employeeId = employeeId;
    this.employeeCode = employeeCode;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
    this.jobTitle = jobTitle;
    this.departmentId = departmentId;
    this.dateOfJoining = dateOfJoining;
    this.salary = salary;
    this.status = status;
}

public int getEmployeeId() {
    return employeeId;
}

public void setEmployeeId(int employeeId) {
    this.employeeId = employeeId;
}

public String getEmployeeCode() {
    return employeeCode;
}

public void setEmployeeCode(String employeeCode) {
    this.employeeCode = employeeCode;
}

public String getFirstName() {
    return firstName;
}

public void setFirstName(String firstName) {
    this.firstName = firstName;
}

public String getLastName() {
    return lastName;
}

public void setLastName(String lastName) {
    this.lastName = lastName;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public String getPhone() {
    return phone;
}

public void setPhone(String phone) {
    this.phone = phone;
}

public String getJobTitle() {
    return jobTitle;
}

public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
}

public int getDepartmentId() {
    return departmentId;
}

public void setDepartmentId(int departmentId) {
    this.departmentId = departmentId;
}

public Date getDateOfJoining() {
    return dateOfJoining;
}

public void setDateOfJoining(Date dateOfJoining) {
    this.dateOfJoining = dateOfJoining;
}

public double getSalary() {
    return salary;
}

public void setSalary(double salary) {
    this.salary = salary;
}

public String getStatus() {
    return status;
}

public void setStatus(String status) {
    this.status = status;
}

public String getDepartmentName() {
    return departmentName;
}

public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
}

}
