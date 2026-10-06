package com.employeemanagement.dao;

public class EmployeeOption {

    private int employeeId;
    private String employeeCode;
    private String firstName;
    private String lastName;

    public EmployeeOption() {
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

    public String getFullName() {

        if (lastName == null ||
                lastName.trim().isEmpty()) {

            return firstName;
        }

        return firstName + " " + lastName;
    }
}