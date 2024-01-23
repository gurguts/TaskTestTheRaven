package com.example.tasktesttheraven.dto;

import lombok.Data;

/**
 * Lombok-annotated CustomerDTO for concise representation of customer details with auto-generated code.
 */
@Data
public class CustomerDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
}
