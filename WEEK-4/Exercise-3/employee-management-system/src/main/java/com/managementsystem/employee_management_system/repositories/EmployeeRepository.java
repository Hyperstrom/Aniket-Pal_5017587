package com.managementsystem.employee_management_system.repositories;

import com.managementsystem.employee_management_system.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
