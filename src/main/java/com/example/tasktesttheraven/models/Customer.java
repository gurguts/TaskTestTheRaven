package com.example.tasktesttheraven.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Lombok-annotated Customer for concise representation of customer details with auto-generated code.
 */
@Data
@AllArgsConstructor
public class Customer {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
}
