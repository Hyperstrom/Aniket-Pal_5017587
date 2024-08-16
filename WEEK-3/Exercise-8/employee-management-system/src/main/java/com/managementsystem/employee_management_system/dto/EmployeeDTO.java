package com.managementsystem.employee_management_system.dto;

public class EmployeeDTO {
    private String firstName;
    private String lastName;
    private String departmentName;

    public EmployeeDTO(String firstName, String lastName, String departmentName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.departmentName = departmentName;
    }
    
    // Getters and Setters
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
