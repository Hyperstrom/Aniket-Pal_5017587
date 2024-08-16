package com.example.employeemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employeemanagementsystem.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
   
}
