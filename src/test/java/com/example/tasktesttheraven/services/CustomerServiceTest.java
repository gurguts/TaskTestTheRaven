package com.example.tasktesttheraven.services;

import com.example.tasktesttheraven.models.Customer;
import com.example.tasktesttheraven.models.CustomerEntity;
import com.example.tasktesttheraven.repositories.CustomerRepositories;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepositories customerRepositories;

    @Mock
    private CustomerValidator customerValidator;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testCreateCustomer() {
        String fullName = "Test User";
        String email = "testuser@example.com";
        String phone = "1234567890";

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFullName(fullName);
        customerEntity.setEmail(email);
        customerEntity.setPhone(phone);
        customerService.createCustomer(customerEntity);

        verify(customerValidator, times(1)).validate(fullName, email, phone);
        verify(customerRepositories, times(1)).save(any(CustomerEntity.class));
    }

    @Test
    public void testReadAllCustomers() {
        CustomerEntity customerEntity1 = new CustomerEntity();
        customerEntity1.setFullName("Test User 1");
        customerEntity1.setEmail("testuser1@example.com");
        customerEntity1.setPhone("1234567890");
        customerEntity1.setCreated(System.currentTimeMillis());
        customerEntity1.setUpdated(null);
        customerEntity1.setIsActive(true);

        CustomerEntity customerEntity2 = new CustomerEntity();
        customerEntity2.setFullName("Test User 2");
        customerEntity2.setEmail("testuser2@example.com");
        customerEntity2.setPhone("0987654321");
        customerEntity2.setCreated(System.currentTimeMillis());
        customerEntity2.setUpdated(null);
        customerEntity2.setIsActive(true);

        when(customerRepositories.findByIsActiveTrue()).thenReturn(Arrays.asList(customerEntity1, customerEntity2));

        List<Customer> customers = customerService.readAllCustomers();

        verify(customerRepositories, times(1)).findByIsActiveTrue();
        assertEquals(2, customers.size());
    }

    @Test
    public void testReadCustomer() {
        Long id = 1L;

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFullName("Test User");
        customerEntity.setEmail("testuser@example.com");
        customerEntity.setPhone("1234567890");
        customerEntity.setCreated(System.currentTimeMillis());
        customerEntity.setUpdated(null);
        customerEntity.setIsActive(true);

        when(customerRepositories.findById(id)).thenReturn(Optional.of(customerEntity));

        Customer customer = customerService.readCustomer(id).get();

        verify(customerRepositories, times(1)).findById(id);
        assertEquals(customerEntity.getFullName(), customer.getFullName());
        assertEquals(customerEntity.getEmail(), customer.getEmail());
        assertEquals(customerEntity.getPhone(), customer.getPhone());
    }

    @Test
    public void testReadCustomerNotFound() {
        Long id = 1L;

        when(customerRepositories.findById(id)).thenReturn(Optional.empty());

        Optional<Customer> customer = customerService.readCustomer(id);

        verify(customerRepositories, times(1)).findById(id);
        assertNull(customer);
    }

    @Test
    public void testReadCustomerInactive() {
        Long id = 1L;

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFullName("Test User");
        customerEntity.setEmail("testuser@example.com");
        customerEntity.setPhone("1234567890");
        customerEntity.setCreated(System.currentTimeMillis());
        customerEntity.setUpdated(null);
        customerEntity.setIsActive(false);

        when(customerRepositories.findById(id)).thenReturn(Optional.of(customerEntity));

        Optional<Customer> customer = customerService.readCustomer(id);

        verify(customerRepositories, times(1)).findById(id);
        assertNull(customer);
    }

    @Test
    public void testUpdateCustomer() {
        Long id = 1L;
        String fullName = "Test User Updated";
        String phone = "0987654321";

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFullName("Test User");
        customerEntity.setEmail("testuser@example.com");
        customerEntity.setPhone("1234567890");
        customerEntity.setCreated(System.currentTimeMillis());
        customerEntity.setUpdated(null);
        customerEntity.setIsActive(true);

        when(customerRepositories.findById(id)).thenReturn(Optional.of(customerEntity));
        when(customerRepositories.save(any(CustomerEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        Customer customer = customerService.updateCustomer(id, fullName, phone);

        verify(customerValidator, times(1)).validateUpdateData(3L,3L,fullName, phone);
        verify(customerRepositories, times(1)).findById(id);
        verify(customerRepositories, times(1)).deleteById(id);
        verify(customerRepositories, times(1)).save(any(CustomerEntity.class));
        assertEquals(id, customer.getId());
        assertEquals(fullName, customer.getFullName());
        assertEquals(phone, customer.getPhone());
    }

    @Test
    public void testUpdateCustomerNotFound() {
        Long idOld = 1L;
        String fullName = "Test User Updated";
        String phone = "0987654321";

        when(customerRepositories.findById(idOld)).thenReturn(Optional.empty());

        Customer customer = customerService.updateCustomer(idOld, fullName, phone);

        verify(customerValidator, times(1)).validateUpdateData(3L,3L,fullName, phone);
        verify(customerRepositories, times(1)).findById(idOld);
        assertNull(customer);
    }

    @Test
    public void testDeleteCustomer() {
        Long id = 1L;

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFullName("Test User");
        customerEntity.setEmail("testuser@example.com");
        customerEntity.setPhone("1234567890");
        customerEntity.setCreated(System.currentTimeMillis());
        customerEntity.setUpdated(null);
        customerEntity.setIsActive(true);

        when(customerRepositories.findById(id)).thenReturn(Optional.of(customerEntity));

        customerService.deleteCustomer(id);

        verify(customerRepositories, times(1)).findById(id);
        verify(customerRepositories, times(1)).save(any(CustomerEntity.class));
    }

    @Test
    public void testDeleteCustomerNotFound() {
        Long id = 1L;

        when(customerRepositories.findById(id)).thenReturn(Optional.empty());

        customerService.deleteCustomer(id);

        verify(customerRepositories, times(1)).findById(id);
    }
}

