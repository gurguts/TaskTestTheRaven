package com.example.tasktesttheraven.services;

import org.springframework.stereotype.Service;

/**
 * CustomerValidator class validates customer data, checking full name, email, and phone for length, format,
 * and required symbols. Methods throw IllegalArgumentException for failed validation.
 */
@Service
public class CustomerValidator {
    public void validate(String fullName, String email, String phone) {
        validateFullName(fullName);
        validateEmail(email);
        validatePhone(phone);
    }

    private void validatePhone(String phone) {
        if (phone == null) {
            throw new IllegalArgumentException("Phone cannot be null");
        }
        if (phone.length() < 6) {
            throw new IllegalArgumentException("Phone must be between 6 and 14 characters long");
        }
        if (phone.length() > 14) {
            throw new IllegalArgumentException("Phone must be between 6 and 14 characters long");
        }
        if (!phone.startsWith("+")) {
            throw new IllegalArgumentException("Phone must start with \"+\"");
        }
        if (!phone.matches("\\+\\d+")) {
            throw new IllegalArgumentException("Phone must contain only digits");
        }
    }

    private void validateEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        if (email.length() < 2) {
            throw new IllegalArgumentException("Email must be between 2 and 100 characters long");
        }
        if (email.length() > 100) {
            throw new IllegalArgumentException("Email must be between 2 and 100 characters long");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email must contain exactly one @");
        }
    }

    private void validateFullName(String fullName) {
        if (fullName == null) {
            throw new IllegalArgumentException("Full name cannot be null");
        }
        if (fullName.length() < 2) {
            throw new IllegalArgumentException("Full name must be between 2 and 50 characters long");
        }
        if (fullName.length() > 50) {
            throw new IllegalArgumentException("Full name must be between 2 and 50 characters long");
        }
    }

    public void validateWithoutEmail(String fullName, String phone) {
        validateFullName(fullName);
        validatePhone(phone);
    }
}
