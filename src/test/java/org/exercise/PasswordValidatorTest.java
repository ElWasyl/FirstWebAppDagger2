package org.exercise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PasswordValidatorTest {
    private static final PasswordValidator passwordValidator = new PasswordValidator();

    @Test
    void shouldReturnTrueWhenValidPassword() throws ValidationException {
        String password = "ValidPass1";
        Assertions.assertTrue(passwordValidator.isValid(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", //empty
            "1a", //length <3
            "123456789AAaa", //length <10
            "123456789", //no letters
            "abcdfg"}) //no numbers
    void shouldThrowErrorWhenInvalidPassword(String password) {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> passwordValidator.isValid(password));
        Assertions.assertEquals("Password is invalid. It can't be empty, and it has to contain at least at least three characters long, and contain at least one capital letter, at least one small letter and at least one number", thrown.getMessage());
    }

}
