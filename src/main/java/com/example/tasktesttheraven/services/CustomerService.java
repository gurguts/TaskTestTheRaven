package com.example.tasktesttheraven.services;

import com.example.tasktesttheraven.exceptions.CustomerException;
import com.example.tasktesttheraven.interfaces.ICustomerService;
import com.example.tasktesttheraven.mapper.CustomerMapper;
import com.example.tasktesttheraven.models.Customer;
import com.example.tasktesttheraven.models.CustomerEntity;
import com.example.tasktesttheraven.repositories.CustomerRepositories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * CustomerService class manages customer operations with validation.
 * It creates, reads, updates, and deletes customer data using a repository and a validator.
 * The methods handle input validation, entity conversion, and interaction with the repository.
 */
@Service
public class CustomerService implements ICustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepositories customerRepositories;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepositories customerRepositories, CustomerMapper customerMapper) {
        this.customerRepositories = customerRepositories;
        this.customerMapper = customerMapper;
    }

    public Customer createCustomer(CustomerEntity customerEntity) {
        String email = customerEntity.getEmail();
        logger.debug("Creating customer with email: {}", email);
        Optional<CustomerEntity> existingCustomers = customerRepositories.findByEmail(email);

        if (existingCustomers.isPresent()) {
            logger.error("Customer with email {} already exists", email);
            throw new RuntimeException("Customer with email " + email + " already exists");
        }

        customerRepositories.save(customerEntity);
        logger.info("Customer with email {} created successfully", email);

        return customerMapper.customerEntityToCustomer(customerEntity);
    }

    public List<Customer> readAllCustomers() {
        logger.debug("Reading all active customers");
        Iterable<CustomerEntity> customersEntity = customerRepositories.findByIsActiveTrue();
        return StreamSupport.stream(customersEntity.spliterator(), false)
                .map(customerMapper::customerEntityToCustomer)
                .collect(Collectors.toList());
    }

    public Optional<Customer> readCustomer(Long id) {
        logger.debug("Reading customer with id: {}", id);
        Optional<CustomerEntity> customerEntityOptional = customerRepositories.findById(id);
        if (customerEntityOptional.isEmpty() || !customerEntityOptional.get().getIsActive()) {
            logger.warn("No customer found or inactive with id: {}", id);
            return Optional.empty();
        }
        logger.info("Active customer found with id: {}", id);
        return Optional.of(customerMapper.customerEntityToCustomer(customerEntityOptional.get()));
    }

    public Customer updateCustomer(Long id, String fullName, String phone) {
        logger.debug("Updating customer with id: {}", id);
        Optional<CustomerEntity> customerEntityOptional = customerRepositories.findById(id);
        if (customerEntityOptional.isEmpty()) {
            logger.error("Customer with id {} not found", id);
            throw new CustomerException("Customer with id " + id + " not found");
        }
        CustomerEntity customerEntity = customerEntityOptional.get();
        customerEntity.setFullName(fullName);
        customerEntity.setPhone(phone);
        customerEntity.setUpdated(System.currentTimeMillis());
        logger.info("Customer with id {} updated successfully", id);
        customerEntity = customerRepositories.save(customerEntity);
        return customerMapper.customerEntityToCustomer(customerEntity);
    }

    public void deleteCustomer(Long id) {
        logger.debug("Deactivating customer with id: {}", id);
        customerRepositories.deactivateCustomer(id);
        logger.info("Customer with id {} deactivated successfully", id);
    }
}
