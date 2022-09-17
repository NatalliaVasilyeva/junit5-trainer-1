package com.dmdev.utils.mock.user;

import com.dmdev.entity.User;
import com.dmdev.utils.mock.domain.BaseMockBuilder;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class MockUserBuilder extends BaseMockBuilder {

    public static User buildUser() {
        return buildUserList(1).get(0);
    }

    public static User buildShortUser() {
        return User.builder()
                .name(resolveValue(NAMES, 0))
                .birthday(LocalDate.of(1997, 12, 11))
                .email(resolveValue(EMAILS, 0))
                .password(resolveValue(PASSWORDS, 0))
                .build();
    }

    public static List<User> buildUserList(int count) {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(
                    User.builder()
                            .id(resolveValue(IDs, i))
                            .name(resolveValue(NAMES, i))
                            .birthday(resolveValue(BIRTHDAYS, i))
                            .email(resolveValue(EMAILS, i))
                            .password(resolveValue(PASSWORDS, i))
                            .role(resolveValue(ROLES, i))
                            .gender(resolveValue(GENDERS, i))
                            .build()
            );
        }
        return list;
    }
}