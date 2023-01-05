package org.exercise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PasswordValidatorTest {
    PasswordValidatorTest() {
    }

    @Test
    void shouldReturnTrueWhenValidPassword() {
        String password = "ValidPass1";
        Assertions.assertTrue(PasswordValidator.isValid(password));
    }

    @Test
    void shouldReturnFalseWhenEmptyPassword() {
        String password = "";
        Assertions.assertFalse(PasswordValidator.isValid(password));
    }

    @Test
    void shouldReturnFalseWhenPasswordTooShort() {
        String password = "1a";
        Assertions.assertFalse(PasswordValidator.isValid(password));
    }

    @Test
    void shouldReturnFalseWhenPasswordTooLong() {
        String password = "123456789AAaa";
        Assertions.assertFalse(PasswordValidator.isValid(password));
    }

    @Test
    void shouldReturnFalseWhenPasswordNoLetters() {
        String password = "123456789";
        Assertions.assertFalse(PasswordValidator.isValid(password));
    }

    @Test
    void shouldReturnFalseWhenPasswordNoNumbers() {
        String password = "abcdfg";
        Assertions.assertFalse(PasswordValidator.isValid(password));
    }
}
