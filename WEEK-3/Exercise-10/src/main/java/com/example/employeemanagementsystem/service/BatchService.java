package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.entity.Employee;
import com.example.employeemanagementsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Service
public class BatchService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public void batchInsertEmployees(List<Employee> employees) {
        for (Employee employee : employees) {
            entityManager.persist(employee);
        }
    }

    public void batchUpdateEmployees(List<Employee> employees) {
        for (Employee employee : employees) {
            entityManager.merge(employee);
        }
    }

    public void batchUpdateEmployeeEmails(List<Long> ids, String newEmailDomain) {
        for (Long id : ids) {
            Employee employee = employeeRepository.findById(id).orElse(null);
            if (employee != null) {
                employee.setEmail(employee.getEmail().replaceAll("@.*", "@" + newEmailDomain));
                employeeRepository.save(employee);
            }
        }
    }
}
