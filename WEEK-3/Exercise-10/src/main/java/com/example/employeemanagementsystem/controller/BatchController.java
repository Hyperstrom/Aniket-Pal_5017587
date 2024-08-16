package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.entity.Employee;
import com.example.employeemanagementsystem.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/batch")
public class BatchController {

    @Autowired
    private BatchService batchService;

    @PostMapping("/insertEmployees")
    public void insertEmployees(@RequestBody List<Employee> employees) {
        batchService.batchInsertEmployees(employees);
    }

    @PostMapping("/updateEmployees")
    public void updateEmployees(@RequestBody List<Employee> employees) {
        batchService.batchUpdateEmployees(employees);
    }

    @PostMapping("/updateEmails")
    public void updateEmployeeEmails(@RequestParam List<Long> ids, @RequestParam String newEmailDomain) {
        batchService.batchUpdateEmployeeEmails(ids, newEmailDomain);
    }
}
