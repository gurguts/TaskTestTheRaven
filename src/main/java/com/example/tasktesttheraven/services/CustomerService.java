package com.example.tasktesttheraven.services;

import com.example.tasktesttheraven.models.Customer;
import com.example.tasktesttheraven.models.CustomerEntity;
import com.example.tasktesttheraven.repositories.CustomerRepositories;
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
public class CustomerService {
    private final CustomerRepositories customerRepositories;
    private final CustomerValidator customerValidator;

    public CustomerService(CustomerRepositories customerRepositories, CustomerValidator customerValidator) {
        this.customerRepositories = customerRepositories;
        this.customerValidator = customerValidator;
    }

    public Customer createCustomer(String fullName, String email, String phone) {
        customerValidator.validate(fullName, email, phone);

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFullName(fullName);
        customerEntity.setEmail(email);
        customerEntity.setPhone(phone);
        customerEntity.setCreated(System.currentTimeMillis());
        customerEntity.setUpdated(null);
        customerEntity.setIsActive(true);

        customerRepositories.save(customerEntity);

        return convertCustomer(customerEntity);
    }

    public List<Customer> readAllCustomers() {
        Iterable<CustomerEntity> customersEntity = customerRepositories.findByIsActiveTrue();
        return StreamSupport.stream(customersEntity.spliterator(), false)
                .map(this::convertCustomer)
                .collect(Collectors.toList());
    }

    public Customer readCustomer(Long id) {
        Optional<CustomerEntity> customerEntityOptional = customerRepositories.findById(id);
        if (customerEntityOptional.isEmpty() || !customerEntityOptional.get().getIsActive()) {
            return null;
        }
        CustomerEntity customerEntity = customerEntityOptional.get();
        return convertCustomer(customerEntity);
    }

    public Customer updateCustomer(Long idOld, Long idNew, String fullName, String phone) {
        customerValidator.validateWithoutEmail(fullName, phone);

        Optional<CustomerEntity> customerEntityOptional = customerRepositories.findById(idOld);
        if (customerEntityOptional.isEmpty()) {
            return null;
        }
        customerRepositories.deleteById(idOld);
        CustomerEntity customerEntity = customerEntityOptional.get();
        customerEntity.setId(idNew);
        customerEntity.setFullName(fullName);
        customerEntity.setPhone(phone);
        customerEntity = customerRepositories.save(customerEntity);
        return convertCustomer(customerEntity);
    }

    public void deleteCustomer(Long id) {
        Optional<CustomerEntity> customerEntityOptional = customerRepositories.findById(id);
        if (customerEntityOptional.isPresent()) {
            CustomerEntity customerEntity = customerEntityOptional.get();
            customerEntity.setIsActive(false);
            customerRepositories.save(customerEntity);
        }
    }

    private Customer convertCustomer(CustomerEntity customerEntity) {
        return new Customer(customerEntity.getId(),
                customerEntity.getFullName(),
                customerEntity.getEmail(),
                customerEntity.getPhone());
    }
}
