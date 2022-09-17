package com.dmdev.utils.mock.user;

import com.dmdev.entity.Gender;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;

import java.time.LocalDate;

public class MockExistsDbUserBuilder {

    public static User getDBUserSveta() {
        return User.builder()
                .id(3)
                .name("Sveta")
                .birthday(LocalDate.of(2001, 12, 23))
                .email("sveta@gmail.com")
                .password("321")
                .role(Role.USER)
                .gender(Gender.FEMALE)
                .build();
    }

    public static User getDBUserKate() {
        return User.builder()
                .id(5)
                .name("Kate")
                .birthday(LocalDate.of(1989, 8, 9))
                .email("kate@gmail.com")
                .password("777")
                .role(Role.ADMIN)
                .gender(Gender.FEMALE)
                .build();
    }
}