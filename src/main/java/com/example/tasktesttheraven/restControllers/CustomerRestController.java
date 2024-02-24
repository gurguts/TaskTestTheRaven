package com.example.tasktesttheraven.restControllers;

import com.example.tasktesttheraven.dto.CustomerDTO;
import com.example.tasktesttheraven.mapper.CustomerMapper;
import com.example.tasktesttheraven.models.Customer;
import com.example.tasktesttheraven.models.CustomerEntity;
import com.example.tasktesttheraven.services.CustomerService;
import com.example.tasktesttheraven.services.CustomerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * This Spring @RestController, CustomerRestController, handles CRUD operations for customers.
 * It uses a CustomerService to create, read, update, and delete customer data.
 */
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerRestController.class);
    private final CustomerService customerService;
    private final CustomerValidator customerValidator;
    private final CustomerMapper customerMapper;

    public CustomerRestController(CustomerService customerService, CustomerValidator customerValidator, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerValidator = customerValidator;
        this.customerMapper = customerMapper;
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDTO customerDTO) {
        logger.debug("Creating customer with email: {}", customerDTO.getEmail());
        customerValidator.validate(customerDTO.getFullName(),
                customerDTO.getEmail(),
                customerDTO.getPhone());

        CustomerEntity customerEntity = customerMapper.customerDTOToCustomerEntity(customerDTO);

        customerEntity.setCreated(System.currentTimeMillis());
        customerEntity.setIsActive(true);

        Customer customer = customerService.createCustomer(customerEntity);
        logger.info("Customer with email {} created successfully", customerDTO.getEmail());
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> readAllCustomers() {
        logger.debug("Reading all active customers");
        List<Customer> customers = customerService.readAllCustomers();
        logger.info("Found {} active customers", customers.size());
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readCustomer(@PathVariable Long id) {
        logger.debug("Reading customer with id: {}", id);
        Optional<Customer> customer = customerService.readCustomer(id);
        if (customer.isEmpty()) {
            logger.warn("No customer found with id: {}", id);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("There is no customer with id: " + id);
        }
        logger.info("Customer with id {} found successfully", id);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        logger.debug("Updating customer with id: {}", id);
        customerValidator.validateUpdateData(id,
                customerDTO.getId(),
                customerDTO.getEmail(),
                customerDTO.getPhone());
        Customer customer = customerService.updateCustomer(id, customerDTO.getFullName(), customerDTO.getPhone());
        logger.info("Customer with id {} updated successfully", id);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        logger.debug("Deactivating customer with id: {}", id);
        customerService.deleteCustomer(id);
        logger.info("Customer with id {} deactivated successfully", id);
    }
}
