package com.dmdev.integration.servcie;

import com.dmdev.dao.UserDao;
import com.dmdev.dto.CreateUserDto;
import com.dmdev.entity.User;
import com.dmdev.exception.ValidationException;
import com.dmdev.integration.IntegrationTestBase;
import com.dmdev.mapper.CreateUserMapper;
import com.dmdev.mapper.UserMapper;
import com.dmdev.service.UserService;
import com.dmdev.utils.mock.user.MockExistsDbUserBuilder;
import com.dmdev.utils.mock.user.MockCreateUserDtoBuilder;
import com.dmdev.validator.CreateUserValidator;
import com.dmdev.validator.Error;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class UserServiceTestIT extends IntegrationTestBase {

    private UserService userService;
    private UserMapper userMapper;
    private CreateUserDto validCreateUserDto;
    private CreateUserDto invalidCreateUserDto;
    private User validUser;

    @BeforeEach
    void sepUp() {
        userService = new UserService(CreateUserValidator.getInstance(),
                UserDao.getInstance(),
                CreateUserMapper.getInstance(),
                UserMapper.getInstance());
        userMapper = UserMapper.getInstance();
        validCreateUserDto = MockCreateUserDtoBuilder.buildCreateUserDto();
        invalidCreateUserDto = MockCreateUserDtoBuilder.buildInvalidCreateUserDto();
        validUser = MockExistsDbUserBuilder.getDBUserKate();
    }

    @Test
    void shouldCreateUserCorrectly() {
        var savedUser = userService.create(validCreateUserDto);

        assertThat(validCreateUserDto.getName()).isEqualTo(savedUser.getName());
        assertThat(validCreateUserDto.getEmail()).isEqualTo(savedUser.getEmail());
        assertThat(savedUser.getId()).isNotNull();
    }

    @Test
    void shouldThrowValidationExceptionIfCreateUserDtoIsInvalid() {
        var result = assertThrowsExactly(ValidationException.class, () -> userService.create(invalidCreateUserDto));

        List<Error> errors = result.getErrors();
        assertThat(errors).isNotEmpty().hasSize(3);
        assertEquals("invalid.birthday", errors.get(0).getCode());
        assertEquals("invalid.gender", errors.get(1).getCode());
        assertEquals("invalid.role", errors.get(2).getCode());
    }

    @Test
    void shouldLoginSuccessfully() {
        var actualResult = userService.login(validUser.getEmail(), validUser.getPassword());
        var expectedResult = userMapper.map(validUser);

        assertThat(actualResult).isPresent().contains(expectedResult);
    }

    @Test
    void shouldReturnEmptyResultIfEmailIsIncorrect() {
        var actualResult = userService.login("incorrect@gmail.com", validUser.getPassword());

        assertThat(actualResult).isEmpty();
    }

    @Test
    void shouldReturnEmptyResultIfPasswordIsIncorrect() {
        var actualResult = userService.login(validUser.getEmail(), "incorrectPassword");

        assertThat(actualResult).isEmpty();
    }
}