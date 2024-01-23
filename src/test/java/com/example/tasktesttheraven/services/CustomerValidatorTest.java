package com.example.tasktesttheraven.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CustomerValidatorTest {

    private final CustomerValidator customerValidator = new CustomerValidator();

    @Test
    public void testValidate() {
        String fullName = "Test User";
        String email = "testuser@example.com";
        String phone = "+1234567890";

        assertDoesNotThrow(() -> customerValidator.validate(fullName, email, phone));

        assertThrows(IllegalArgumentException.class, () -> customerValidator.validate(null, email, phone));
        assertThrows(IllegalArgumentException.class, () -> customerValidator.validate(fullName, null, phone));
        assertThrows(IllegalArgumentException.class, () -> customerValidator.validate(fullName, email, null));
    }

    @Test
    public void testValidateWithoutEmail() {
        String fullName = "Test User";
        String phone = "+1234567890";

        assertDoesNotThrow(() -> customerValidator.validateWithoutEmail(fullName, phone));

        assertThrows(IllegalArgumentException.class, () -> customerValidator.validateWithoutEmail(null, phone));
        assertThrows(IllegalArgumentException.class, () -> customerValidator.validateWithoutEmail(fullName, null));
    }
}
