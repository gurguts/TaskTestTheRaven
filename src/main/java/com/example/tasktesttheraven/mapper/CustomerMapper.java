package com.example.tasktesttheraven.mapper;

import com.example.tasktesttheraven.dto.CustomerDTO;
import com.example.tasktesttheraven.models.Customer;
import com.example.tasktesttheraven.models.CustomerEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerEntity customerDTOToCustomerEntity(CustomerDTO customer);
    Customer customerEntityToCustomer(CustomerEntity customer);
}