package com.managementsystem.employee_management_system.services;

import com.managementsystem.employee_management_system.dto.EmployeeDTO;
import com.managementsystem.employee_management_system.entities.Employee;
import com.managementsystem.employee_management_system.repositories.EmployeeNameProjection;
import com.managementsystem.employee_management_system.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Retrieve all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Retrieve employee by ID
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    // Create a new employee
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }


    // Update an existing employee
    public Optional<Employee> updateEmployee(Long id, Employee employeeDetails) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setFirstName(employeeDetails.getFirstName());
            employee.setLastName(employeeDetails.getLastName());
            employee.setEmail(employeeDetails.getEmail());
            employee.setDepartment(employeeDetails.getDepartment());
            return employeeRepository.save(employee);
        });
    }

    // Delete an employee by ID
    public boolean deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Retrieve employee by email
    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    // Retrieve employees by department name
    public List<Employee> getEmployeesByDepartmentName(String departmentName) {
        return employeeRepository.findByDepartmentName(departmentName);
    }

    // Retrieve employee names using interface-based projection
    public List<EmployeeNameProjection> getAllEmployeeNames() {
        return employeeRepository.findAllEmployeeNames();
    }

    // Retrieve employee DTOs using class-based projection
    public List<EmployeeDTO> getAllEmployeeDTOs() {
        return employeeRepository.findAllEmployeeDTOs();
    }
}
