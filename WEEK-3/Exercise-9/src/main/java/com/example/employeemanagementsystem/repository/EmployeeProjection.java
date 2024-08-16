package com.example.employeemanagementsystem.repository;

import com.example.employeemanagementsystem.entity.Employee;
import org.springframework.beans.factory.annotation.Value;

public interface EmployeeProjection {
    Long getId();
    String getName();
    @Value("#{target.email}")
    String getEmail();
}
