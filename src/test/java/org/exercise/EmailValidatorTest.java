package org.exercise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EmailValidatorTest {

    private static final EmailValidator emailValidator = new EmailValidator();

    @ParameterizedTest
    @ValueSource(strings = {"user@domain.com",
            "user@domain.co.in",
            "user.name@domain.com",
            "user_name@domain.com",
            "username@yahoo.corporate.in"})
    void shouldReturnTrueWhenValidEmail(String email) throws ValidationException {
        Assertions.assertTrue(emailValidator.isValid(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", //empty
            ".username@yahoo.com", //leading dots
            "username@yahoo.com.", //trailing dots
            "username@yahoo..com", //consecutive dots
            "username@yahoo.c", //top domain length<2
            "username@yahoo.corporate"}) //top domain length>6
    void shouldThrowErrorWhenInvalidEmail(String email) {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            emailValidator.isValid(email);
        });
        Assertions.assertEquals("Email is invalid.", thrown.getMessage());
    }

}
