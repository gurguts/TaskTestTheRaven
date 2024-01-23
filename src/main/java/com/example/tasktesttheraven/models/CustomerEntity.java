package com.example.tasktesttheraven.models;

import jakarta.persistence.*;
import lombok.Data;

/**
 * This Lombok-annotated class, CustomerEntity, represents a customer entity.
 * It is annotated with @Entity and includes JPA annotations for table mapping.
 * The @Data annotation automates common boilerplate code generation.
 */
@Data
@Entity
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created")
    private Long created;

    @Column(name = "updated")
    private Long updated;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "is_active")
    private Boolean isActive;
}
