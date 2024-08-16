package com.managementsystem.employee_management_system.controllers;

import com.managementsystem.employee_management_system.repositories.DepartmentRepository;
import com.managementsystem.employee_management_system.repositories.EmployeeNameProjection;
import com.managementsystem.employee_management_system.dto.EmployeeDTO;
import com.managementsystem.employee_management_system.entities.Department;
import com.managementsystem.employee_management_system.entities.Employee;
import com.managementsystem.employee_management_system.responce.ApiResponse;
import com.managementsystem.employee_management_system.repositories.EmployeeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;  // Inject DepartmentRepository

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Employee>>> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return ResponseEntity.ok(new ApiResponse<>("Employees retrieved successfully", employees));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(value -> ResponseEntity.ok(new ApiResponse<>("Employee retrieved successfully", value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("Employee not found", null)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@RequestBody Employee employee) {
        // Ensure department is fully loaded from the database
        Department department = departmentRepository.findById(employee.getDepartment().getId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        employee.setDepartment(department);

        Employee savedEmployee = employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Employee created successfully", savedEmployee));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.setFirstName(employeeDetails.getFirstName());
            employee.setLastName(employeeDetails.getLastName());
            employee.setEmail(employeeDetails.getEmail());

            // Ensure department is fully loaded from the database
            Department department = departmentRepository.findById(employeeDetails.getDepartment().getId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            employee.setDepartment(department);

            Employee updatedEmployee = employeeRepository.save(employee);
            return ResponseEntity.ok(new ApiResponse<>("Employee updated successfully", updatedEmployee));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Employee not found", null));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse<>("Employee deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Employee not found", null));
        }
    }

    @GetMapping("/lastname/{lastName}")
    public ResponseEntity<ApiResponse<List<Employee>>> getEmployeesByLastName(@PathVariable String lastName) {
        TypedQuery<Employee> query = entityManager.createNamedQuery("Employee.findByLastName", Employee.class);
        query.setParameter("lastName", lastName);
        List<Employee> employees = query.getResultList();
        return ResponseEntity.ok(new ApiResponse<>("Employees retrieved successfully", employees));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<ApiResponse<List<Employee>>> getEmployeesByDepartmentId(@PathVariable Long departmentId) {
        TypedQuery<Employee> query = entityManager.createNamedQuery("Employee.findByDepartmentId", Employee.class);
        query.setParameter("departmentId", departmentId);
        List<Employee> employees = query.getResultList();
        return ResponseEntity.ok(new ApiResponse<>("Employees retrieved successfully", employees));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<Employee>> getEmployeeByEmail(@PathVariable String email) {
        Employee employee = employeeRepository.findByEmail(email);
        if (employee != null) {
            return ResponseEntity.ok(new ApiResponse<>("Employee retrieved successfully", employee));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Employee not found", null));
        }
    }

    @GetMapping("/count-by-department")
    public ResponseEntity<ApiResponse<List<Object[]>>> countEmployeesByDepartment() {
        List<Object[]> counts = employeeRepository.countEmployeesByDepartment();
        return ResponseEntity.ok(new ApiResponse<>("Employee count by department retrieved successfully", counts));
    }

    @GetMapping("/projections/names")
    public ResponseEntity<ApiResponse<List<EmployeeNameProjection>>> getAllEmployeeNames() {
        List<EmployeeNameProjection> employees = employeeRepository.findAllEmployeeNames();
        return ResponseEntity.ok(new ApiResponse<>("Employee names retrieved successfully", employees));
    }

    @GetMapping("/projections/dto")
    public ResponseEntity<ApiResponse<List<EmployeeDTO>>> getAllEmployeeDTOs() {
        List<EmployeeDTO> employees = employeeRepository.findAllEmployeeDTOs();
        return ResponseEntity.ok(new ApiResponse<>("Employee DTOs retrieved successfully", employees));
    }


}
