package com.example.tasktesttheraven.services;

import com.example.tasktesttheraven.exceptions.CustomerException;
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
            throw new CustomerException("Phone cannot be null");
        }
        if (phone.length() < 6) {
            throw new CustomerException("Phone must be between 6 and 14 characters long");
        }
        if (phone.length() > 14) {
            throw new CustomerException("Phone must be between 6 and 14 characters long");
        }
        if (!phone.startsWith("+")) {
            throw new CustomerException("Phone must start with \"+\"");
        }
        if (!phone.matches("\\+\\d+")) {
            throw new CustomerException("Phone must contain only digits");
        }
    }

    private void validateEmail(String email) {
        if (email == null) {
            throw new CustomerException("Email cannot be null");
        }
        if (email.length() < 2) {
            throw new CustomerException("Email must be between 2 and 100 characters long");
        }
        if (email.length() > 100) {
            throw new CustomerException("Email must be between 2 and 100 characters long");
        }
        if (!email.contains("@")) {
            throw new CustomerException("Email must contain exactly one @");
        }
    }

    private void validateFullName(String fullName) {
        if (fullName == null) {
            throw new CustomerException("Full name cannot be null");
        }
        if (fullName.length() < 2) {
            throw new CustomerException("Full name must be between 2 and 50 characters long");
        }
        if (fullName.length() > 50) {
            throw new CustomerException("Full name must be between 2 and 50 characters long");
        }
    }

    public void validateUpdateData(Long idNew, Long idOld, String fullName, String phone) {
        if (idNew.equals(idOld)){
            throw new CustomerException("Entities' IDs do not match");
        }
        validateFullName(fullName);
        validatePhone(phone);
    }
}
