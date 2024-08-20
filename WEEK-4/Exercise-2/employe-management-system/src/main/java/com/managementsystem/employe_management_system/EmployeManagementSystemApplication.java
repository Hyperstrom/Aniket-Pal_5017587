package com.managementsystem.employe_management_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.managementsystem.employe_management_system.entities.Department;
import com.managementsystem.employe_management_system.entities.Employee;
import com.managementsystem.employe_management_system.repositories.DepartmentRepository;
import com.managementsystem.employe_management_system.repositories.EmployeeRepository;

@SpringBootApplication
public class EmployeManagementSystemApplication implements CommandLineRunner {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public static void main(String[] args) {
        SpringApplication.run(EmployeManagementSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //First Department and Employee
        Department department = new Department();
        department.setName("IT");
        departmentRepository.save(department);

        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setEmail("john.doe@example.com");
        employee.setDepartment(department);
        employeeRepository.save(employee);

        System.out.println("Department: " + departmentRepository.findById(department.getId()).get());
        System.out.println("Employee: " + employeeRepository.findById(employee.getId()).get());

    }
}
