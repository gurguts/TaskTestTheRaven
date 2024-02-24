package com.example.tasktesttheraven.restControllers;

import com.example.tasktesttheraven.dto.CustomerDTO;
import com.example.tasktesttheraven.models.Customer;
import com.example.tasktesttheraven.models.CustomerEntity;
import com.example.tasktesttheraven.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void testCreateCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFullName("Test");
        customerDTO.setEmail("test@test.com");
        customerDTO.setPhone("+123456789");
        Customer customer = new Customer(1L, "Test", "test@test.com", "+123456789");

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFullName(customerDTO.getFullName());
        customerEntity.setEmail(customerDTO.getEmail());
        customerEntity.setPhone(customerDTO.getPhone());
        when(customerService.createCustomer(customerEntity))
                .thenReturn(customer);

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullName\":\"Test\",\"email\":\"test@test.com\",\"phone\":\"+123456789\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testReadAllCustomers() throws Exception {
        Customer customer1 = new Customer(1L, "Test1", "test1@test.com", "+123456789");
        Customer customer2 = new Customer(2L, "Test2", "test2@test.com", "+987654321");
        List<Customer> customers = Arrays.asList(customer1, customer2);

        when(customerService.readAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testReadCustomer() throws Exception {
        Long id = 1L;
        Customer customer = new Customer(id, "Test", "test@test.com", "+123456789");

        when(customerService.readCustomer(id)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/api/v1/customers/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        Long id = 1L;
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(id);
        customerDTO.setFullName("Test");
        customerDTO.setPhone("+123456789");
        Customer customer = new Customer(id, "Test", "test@test.com", "+123456789");

        when(customerService.updateCustomer(id, customerDTO.getFullName(), customerDTO.getPhone()))
                .thenReturn(customer);

        mockMvc.perform(put("/api/v1/customers/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"fullName\":\"Test\",\"phone\":\"+123456789\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        long id = 1L;

        mockMvc.perform(delete("/api/v1/customers/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}