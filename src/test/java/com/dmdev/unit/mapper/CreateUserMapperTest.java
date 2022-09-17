package com.dmdev.unit.mapper;

import com.dmdev.entity.Gender;
import com.dmdev.entity.Role;
import com.dmdev.mapper.CreateUserMapper;
import com.dmdev.utils.mock.user.MockCreateUserDtoBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateUserMapperTest {
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();

    @Test
    void shouldMakeMappingCorrectly() {
        var createUserDto = MockCreateUserDtoBuilder.buildCreateUserDto();

        var actualUser = createUserMapper.map(createUserDto);

        assertThat(actualUser.getName()).isEqualTo(createUserDto.getName());
        assertThat(actualUser.getBirthday()).isEqualTo(createUserDto.getBirthday());
        assertThat(actualUser.getEmail()).isEqualTo(createUserDto.getEmail());
        assertThat(actualUser.getPassword()).isEqualTo(createUserDto.getPassword());
        assertThat(actualUser.getRole()).isEqualTo(Role.valueOf(createUserDto.getRole()));
        assertThat(actualUser.getGender()).isEqualTo(Gender.valueOf(createUserDto.getGender()));
    }
}