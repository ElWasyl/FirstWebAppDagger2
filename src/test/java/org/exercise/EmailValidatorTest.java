package org.exercise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailValidatorTest {
    EmailValidatorTest() {
    }

    @Test
    void shouldReturnTrueWhenValidEmail() {
        String email = "example@whatever.com";
        Assertions.assertTrue(EmailValidator.isValid(email));
    }

    @Test
    void shouldReturnFalseWhenNoAtSign() {
        String email = "examplewhatever.com";
        Assertions.assertFalse(EmailValidator.isValid(email));
    }

    @Test
    void shouldReturnFalseWhenEmpty() {
        String email = "";
        Assertions.assertFalse(EmailValidator.isValid(email));
    }
}
