package com.managementsystem.employee_management_system.repositories;

import com.managementsystem.employee_management_system.dto.EmployeeDTO;
import com.managementsystem.employee_management_system.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.email = :email")
    Employee findByEmail(@Param("email") String email);

    @Query("SELECT e FROM Employee e WHERE e.department.name = :departmentName")
    List<Employee> findByDepartmentName(@Param("departmentName") String departmentName);

    @Query("SELECT e.department.name, COUNT(e) FROM Employee e GROUP BY e.department.name")
    List<Object[]> countEmployeesByDepartment();

    // Interface-based projection
    @Query("SELECT e.firstName AS firstName, e.lastName AS lastName, e.department.name AS departmentName " +
           "FROM Employee e")
    List<EmployeeNameProjection> findAllEmployeeNames();

    // Class-based projection
    @Query("SELECT new com.managementsystem.employee_management_system.dto.EmployeeDTO(e.firstName, e.lastName, e.department.name) " +
           "FROM Employee e")
    List<EmployeeDTO> findAllEmployeeDTOs();
}
