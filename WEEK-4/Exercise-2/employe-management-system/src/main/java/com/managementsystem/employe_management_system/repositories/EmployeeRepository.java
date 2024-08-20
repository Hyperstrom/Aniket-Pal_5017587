package com.managementsystem.employe_management_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.managementsystem.employe_management_system.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
