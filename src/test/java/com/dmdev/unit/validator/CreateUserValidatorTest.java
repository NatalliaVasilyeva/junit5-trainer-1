package com.dmdev.unit.validator;

import com.dmdev.dto.CreateUserDto;
import com.dmdev.utils.mock.user.MockCreateUserDtoBuilder;
import com.dmdev.validator.CreateUserValidator;
import com.dmdev.validator.Error;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateUserValidatorTest {

    private static CreateUserValidator createUserValidator;

    @BeforeAll
    static void getValidatorInstance() {
        createUserValidator = CreateUserValidator.getInstance();
    }

    @Test
    void shouldReturnPositiveResultIfCreateUserDtoIsValid() {
        var createUserDto = MockCreateUserDtoBuilder.buildCreateUserDto();

        var validationResult = createUserValidator.validate(createUserDto);

        assertTrue(validationResult.isValid());
        assertThat(validationResult.getErrors()).isEmpty();
    }

    @CsvFileSource(resources = "/invalidCreateUserDataOneParameter.csv", numLinesToSkip = 1)
    @ParameterizedTest
    void shouldReturnNegativeResultAndValidErrorCodeIfCreateUserDtoIsInvalid(String date, String gender, String role, String errorCode) {
        var createUserDto = CreateUserDto.builder()
                .birthday(date)
                .gender(gender)
                .role(role)
                .build();

        var validationResult = createUserValidator.validate(createUserDto);

        assertFalse(validationResult.isValid());
        assertThat(validationResult.getErrors()).isNotEmpty();
        assertThat(validationResult.getErrors()).hasSize(1);
        assertThat(validationResult.getErrors().get(0).getCode()).isEqualTo(errorCode);
    }

    @Test
    void shouldReturnNegativeResultAndAllValidErrorsCodeIfCreateUserDtoIsInvalid() {
        var createUserDto = MockCreateUserDtoBuilder.buildInvalidCreateUserDto();

        var validationResult = createUserValidator.validate(createUserDto);

        List<Error> errors = validationResult.getErrors();
        assertFalse(validationResult.isValid());
        assertThat(errors).isNotEmpty().hasSize(3);
        assertEquals("invalid.birthday", errors.get(0).getCode());
        assertEquals("invalid.gender", errors.get(1).getCode());
        assertEquals("invalid.role", errors.get(2).getCode());
    }
}