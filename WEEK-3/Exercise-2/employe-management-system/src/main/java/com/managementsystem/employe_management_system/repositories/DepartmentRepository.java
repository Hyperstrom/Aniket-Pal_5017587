package com.managementsystem.employe_management_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.managementsystem.employe_management_system.entities.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
