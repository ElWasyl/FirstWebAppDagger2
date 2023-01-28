package org.exercise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PasswordValidatorTest {
    private static final PasswordValidator PASSWORD_VALIDATOR = new PasswordValidator();

    @Test
    void shouldReturnTrueWhenValidPassword() throws ValidationException {
        String password = "ValidPass1";
        Assertions.assertTrue(PASSWORD_VALIDATOR.isValid(password));
    }

    @ParameterizedTest
    @MethodSource("invalidPasswords")
    void shouldThrowErrorWhenInvalidPassword(String password) {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> PASSWORD_VALIDATOR.isValid(password));
        Assertions.assertEquals("Password is invalid. It can't be empty, and it has to contain at least at least three characters long, and contain at least one capital letter, at least one small letter and at least one number", thrown.getMessage());
    }
    static Stream<Arguments> invalidPasswords() {
        return Stream.of(
                arguments(named("empty", "")),
                arguments(named("length <3", "1a")),
                arguments(named("length <10", "123456789AAaa")),
                arguments(named("no letters", "123456789")),
                arguments(named("no numbers", "abcdfg"))
        );
    }


}
