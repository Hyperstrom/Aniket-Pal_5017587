// src/main/java/com/managementsystem/book_management_system/controller/CustomerController.java
package com.managementsystem.book_management_system.controller;

import com.managementsystem.book_management_system.dto.CustomerDTO;
import com.managementsystem.book_management_system.entity.Customer;
import com.managementsystem.book_management_system.mapper.CustomerMapper;
import com.managementsystem.book_management_system.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(c -> new ResponseEntity<>(customerMapper.toCustomerDTO(c), HttpStatus.OK))
                       .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = customerMapper.toCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return new ResponseEntity<>(customerMapper.toCustomerDTO(savedCustomer), HttpStatus.CREATED);
    }
}
