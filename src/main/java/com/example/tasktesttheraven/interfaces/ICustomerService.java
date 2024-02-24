package com.example.tasktesttheraven.interfaces;

import com.example.tasktesttheraven.models.Customer;
import com.example.tasktesttheraven.models.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {
    Customer createCustomer(CustomerEntity customerEntity);
    List<Customer> readAllCustomers();
    Optional<Customer> readCustomer(Long id);
    Customer updateCustomer(Long id, String fullName, String phone);
    void deleteCustomer(Long id);
}
