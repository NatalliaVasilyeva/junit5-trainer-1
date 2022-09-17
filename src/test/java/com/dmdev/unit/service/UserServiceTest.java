package com.dmdev.unit.service;

import com.dmdev.dao.UserDao;
import com.dmdev.exception.ValidationException;
import com.dmdev.mapper.CreateUserMapper;
import com.dmdev.mapper.UserMapper;
import com.dmdev.service.UserService;
import com.dmdev.utils.mock.user.MockExistsDbUserBuilder;
import com.dmdev.utils.mock.user.MockCreateUserDtoBuilder;
import com.dmdev.utils.mock.user.MockUserBuilder;
import com.dmdev.utils.mock.user.MockUserDtoBuilder;
import com.dmdev.validator.CreateUserValidator;
import com.dmdev.validator.Error;
import com.dmdev.validator.ValidationResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private CreateUserValidator createUserValidator;

    @Mock
    private UserDao userDao;

    @Mock
    private CreateUserMapper createUserMapper;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldLoginSuccessfully() {
        var expectedUserDto = MockUserDtoBuilder.buildUserDto();
        var loginUser = MockExistsDbUserBuilder.getDBUserKate();

        when(userDao.findByEmailAndPassword(loginUser.getEmail(), loginUser.getPassword())).thenReturn(Optional.of(loginUser));
        when(userMapper.map(loginUser)).thenReturn(expectedUserDto);
        var actualUserDto = userService.login(loginUser.getEmail(), loginUser.getPassword());

        assertThat(actualUserDto).isPresent();
        assertEquals(actualUserDto.get(), expectedUserDto);
        verify(userDao, times(1)).findByEmailAndPassword(loginUser.getEmail(), loginUser.getPassword());
        verify(userMapper, times(1)).map(loginUser);

    }

    @Test
    void shouldCreateUserSuccessfully() {
        var createUserDto = MockCreateUserDtoBuilder.buildCreateUserDto();
        var user = MockUserBuilder.buildUser();
        var expectedUserDto = MockUserDtoBuilder.buildUserDto();

        when(createUserValidator.validate(createUserDto)).thenReturn(new ValidationResult());
        when(createUserMapper.map(createUserDto)).thenReturn(user);
        when(userDao.save(user)).thenReturn(user);
        when(userMapper.map(user)).thenReturn(expectedUserDto);
        var actualUserDto = userService.create(createUserDto);

        assertEquals(actualUserDto, expectedUserDto);
        verify(createUserValidator, times(1)).validate(createUserDto);
        verify(createUserMapper, times(1)).map(createUserDto);
        verify(userDao, times(1)).save(user);
        verify(userMapper, times(1)).map(user);

    }

    @Test
    void shouldThrowExceptionWhenValidationReturnFalse() {
        var createUserDto = MockCreateUserDtoBuilder.buildInvalidCreateUserDto();
        var validationResult = new ValidationResult();
        validationResult.add(Error.of("invalid.birthday", "Birthday is invalid"));

        when(createUserValidator.validate(createUserDto)).thenReturn(validationResult);

        assertThrowsExactly(ValidationException.class, () -> userService.create(createUserDto));
        verify(createUserValidator, times(1)).validate(createUserDto);
        verify(createUserMapper, times(0)).map(any());
        verify(userDao, times(0)).save(any());
        verify(userMapper, times(0)).map(any());

    }

    @Test
    public void mockThrowSneakilyThrownCheckedException() {
        lenient().when(userDao.save(any())).thenThrow(new RuntimeException());
    }
}