package com.managementsystem.book_management_system.controller;

import com.managementsystem.book_management_system.entity.Customer;
import com.managementsystem.book_management_system.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    // POST: Create a new customer from JSON request body
    @PostMapping("/register-json")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "Customer Registered via JSON");
        return new ResponseEntity<>(savedCustomer, headers, HttpStatus.CREATED);
    }

    // POST: Create a new customer from form data
    @PostMapping("/register-form")
    public ResponseEntity<Customer> registerCustomerFromForm(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password) {

        Customer customer = new Customer(name, email, password);
        Customer savedCustomer = customerRepository.save(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "Customer Registered via Form");
        return new ResponseEntity<>(savedCustomer, headers, HttpStatus.CREATED);
    }
}
