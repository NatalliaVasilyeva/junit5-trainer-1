package com.dmdev.unit.mapper;

import com.dmdev.dto.UserDto;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import com.dmdev.mapper.UserMapper;
import com.dmdev.utils.mock.user.MockUserBuilder;
import com.dmdev.utils.mock.user.MockUserDtoBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private final UserMapper userMapper = UserMapper.getInstance();

    @Test
    void shouldMakeMappingCorrectly() {
        var user = MockUserBuilder.buildUser();
        var expectedUserDto = MockUserDtoBuilder.buildUserDto();

        var actualUserDto = userMapper.map(user);

        assertThat(actualUserDto).isEqualTo(expectedUserDto);
        assertThat(actualUserDto.getId()).isEqualTo(user.getId());
        assertThat(actualUserDto.getName()).isEqualTo(user.getName());
        assertThat(actualUserDto.getBirthday()).isEqualTo(user.getBirthday());
        assertThat(actualUserDto.getEmail()).isEqualTo(user.getEmail());
        assertThat(actualUserDto.getRole()).isEqualTo(user.getRole());
        assertThat(actualUserDto.getGender()).isEqualTo(user.getGender());
    }

    @Test
    void shouldMakeMappingCorrectlyIdNotPresentAllUserFields() {
        var shortUser = User.builder()
                .id(1)
                .name("Petia")
                .email("petia@gmail.com")
                .password("888")
                .role(Role.ADMIN)
                .build();

        var expectedShortUserDto = UserDto.builder()
                .id(1)
                .name("Petia")
                .email("petia@gmail.com")
                .role(Role.ADMIN)
                .build();

        var actualUserDto = userMapper.map(shortUser);

        assertThat(actualUserDto).isEqualTo(expectedShortUserDto);
    }

}