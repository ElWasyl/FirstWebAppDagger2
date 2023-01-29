package org.exercise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class EmailValidatorTest {
    private static final EmailValidator EMAIL_VALIDATOR = new EmailValidator();

    @ParameterizedTest
    @ValueSource(strings = {"user@domain.com",
            "user@domain.co.in",
            "user.name@domain.com",
            "user_name@domain.com",
            "username@yahoo.corporate.in"})

    void shouldReturnTrueWhenValidEmail(String email) throws ValidationException {
        Assertions.assertTrue(EMAIL_VALIDATOR.isValid(email));
    }

    @ParameterizedTest
    @MethodSource("incorrectEmails")
    void shouldThrowErrorWhenInvalidEmail(String email) {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            EMAIL_VALIDATOR.isValid(email);
        });
        Assertions.assertEquals("Email is invalid.", thrown.getMessage());
    }
    static Stream<Arguments> incorrectEmails() {
        return Stream.of(
                arguments(named("empty", "")),
                arguments(named("leading dots", ".username@yahoo.com")),
                arguments(named("trailing dots", "username@yahoo.com.")),
                arguments(named("consecutive dots", "username@yahoo..com")),
                arguments(named("top domain length<2", "username@yahoo.c")),
                arguments(named("top domain length>6", "username@yahoo.corporate"))
        );
    }


}
