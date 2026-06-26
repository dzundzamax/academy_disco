package com.betacom.disco.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.disco.entities.Customer;
import com.betacom.disco.services.CustomerService;



@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping()
    public List<Customer> getAll() {
        return customerService.getAll();
    }
    
    
    @GetMapping("/get/{id}")
    public Optional<Customer> getById(@PathVariable Long id) {
        return customerService.getById(id);
    }

    //localhost:8080/api/customers/?name=name&surname=surname
    @GetMapping("/find")
    public Optional<Customer> getNameAndSurname(
        @RequestParam String name,
        @RequestParam String surname) {
        return customerService.getNameAndSurname(name, surname);
    }

    //localhost:8080/api/customers/?name=name&surname=surname
    @GetMapping("/{name}")
    public Optional<Customer> getNameAndSurname2(
        @PathVariable String name, 
        @RequestParam String surname) {
        return customerService.getNameAndSurname(name, surname);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer newCustomer){
        Customer customer = customerService.createUser(newCustomer);
        if (customer == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(201).body(customer);
    }
}
