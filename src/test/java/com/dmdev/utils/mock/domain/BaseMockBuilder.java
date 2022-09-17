package com.dmdev.utils.mock.domain;

import com.dmdev.entity.Gender;
import com.dmdev.entity.Role;

import java.time.LocalDate;
import java.util.List;

public abstract class BaseMockBuilder {

    protected static List<Integer> IDs = List.of(1, 2, 3, 4);
    protected static List<String> NAMES = List.of("Maksim", "Sveta", "Ivan", "Katerina");
    protected static List<LocalDate> BIRTHDAYS = List.of(LocalDate.of(1986, 1, 11), LocalDate.of(1989, 3, 31),
            LocalDate.of(1996, 10, 5), LocalDate.of(1993, 6, 23));
    protected static List<String> EMAILS = List.of("maksim@gmail.com", "sveta@gmail.com", "ivan@gmail.com", "katerina@gmail.com");
    protected static List<String> PASSWORDS = List.of("111", "222", "333", "444");
    protected static List<Role> ROLES = List.of(Role.ADMIN, Role.USER, Role.ADMIN, Role.USER);
    protected static List<Gender> GENDERS = List.of(Gender.MALE, Gender.FEMALE, Gender.MALE, Gender.FEMALE);

    protected static <T> T resolveValue(List<T> list, int index) {

        if (list == null || list.isEmpty()) {
            return null;
        }
        if (list.size() <= index) {
            return list.get(0);
        }
        return list.get(index);
    }
}