package com.dmdev.utils.mock.user;

import com.dmdev.dto.CreateUserDto;
import com.dmdev.utils.mock.domain.BaseMockBuilder;

import java.util.ArrayList;
import java.util.List;

public class MockCreateUserDtoBuilder extends BaseMockBuilder {
    public static CreateUserDto buildCreateUserDto() {
        return buildCreateUserDtoList(1).get(0);
    }

    public static CreateUserDto buildInvalidCreateUserDto() {
        return CreateUserDto.builder()
                .name(resolveValue(NAMES, 0))
                .birthday("1997-13-11")
                .email(resolveValue(EMAILS, 0))
                .password(resolveValue(PASSWORDS, 0))
                .role("invalidRole")
                .gender("invalidGender")
                .build();
    }

    public static List<CreateUserDto> buildCreateUserDtoList(int count) {
        List<CreateUserDto> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(
                    CreateUserDto.builder()
                            .name(resolveValue(NAMES, i))
                            .birthday(resolveValue(BIRTHDAYS, i).toString())
                            .email(resolveValue(EMAILS, i))
                            .password(resolveValue(PASSWORDS, i))
                            .role(resolveValue(ROLES, i).name())
                            .gender(resolveValue(GENDERS, i).name())
                            .build()
            );
        }
        return list;
    }
}