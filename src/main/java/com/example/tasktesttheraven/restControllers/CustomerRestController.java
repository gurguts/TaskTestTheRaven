package com.example.tasktesttheraven.restControllers;

import com.example.tasktesttheraven.dto.CustomerDTO;
import com.example.tasktesttheraven.models.Customer;
import com.example.tasktesttheraven.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This Spring @RestController, CustomerRestController, handles CRUD operations for customers.
 * It uses a CustomerService to create, read, update, and delete customer data.
 */
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestController {
    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            Customer customer = customerService.createCustomer(customerDTO.getFullName(),
                    customerDTO.getEmail(),
                    customerDTO.getPhone());
            return ResponseEntity.ok(customer);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(exception.getMessage());
        }
    }

    @GetMapping
    public List<Customer> readAllCustomers() {
        return customerService.readAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readCustomer(@PathVariable Long id) {
        Customer customer = customerService.readCustomer(id);
        if (customer == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("There is no customer with id: " + id);
        }
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        try {
            Customer customer = customerService.updateCustomer(id,
                    customerDTO.getId(),
                    customerDTO.getFullName(),
                    customerDTO.getPhone());
            if (customer == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("There is no customer with id: " + id);
            }
            return ResponseEntity.ok(customer);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
