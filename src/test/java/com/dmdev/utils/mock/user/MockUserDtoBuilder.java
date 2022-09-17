package com.dmdev.utils.mock.user;

import com.dmdev.dto.UserDto;
import com.dmdev.utils.mock.domain.BaseMockBuilder;

import java.util.ArrayList;
import java.util.List;

public class MockUserDtoBuilder extends BaseMockBuilder {

    public static UserDto buildUserDto() {
        return buildUserDtoList(1).get(0);
    }

    public static List<UserDto> buildUserDtoList(int count) {
        List<UserDto> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(
                    UserDto.builder()
                            .id(resolveValue(IDs, i))
                            .name(resolveValue(NAMES, i))
                            .birthday(resolveValue(BIRTHDAYS, i))
                            .email(resolveValue(EMAILS, i))
                            .image(null)
                            .role(resolveValue(ROLES, i))
                            .gender(resolveValue(GENDERS, i))
                            .build()
            );
        }
        return list;
    }
}