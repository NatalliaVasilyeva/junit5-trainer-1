package com.dmdev.integration.dao;

import com.dmdev.dao.UserDao;
import com.dmdev.entity.User;
import com.dmdev.integration.IntegrationTestBase;
import com.dmdev.utils.mock.user.MockExistsDbUserBuilder;
import com.dmdev.utils.mock.user.MockUserBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDaoTestIT extends IntegrationTestBase {

    private final UserDao userDao = UserDao.getInstance();

    @Test
    void shouldReturnAllUsers() {
        assertThat(userDao.findAll()).hasSize(5);
    }

    @Test
    void shouldReturnUserById() {
        User expectedUser = MockExistsDbUserBuilder.getDBUserSveta();
        Optional<User> actualUser = userDao.findById(expectedUser.getId());

        assertThat(actualUser).isPresent();
        assertEquals(actualUser.get(), expectedUser);
    }

    @Test
    void shouldReturnEmptyIfUserNotPresent() {
        Optional<User> actualUser = userDao.findById(10);
        assertThat(actualUser).isEmpty();
    }

    @Test
    void shouldReturnEmptyIfUserIdIsNull() {
        Optional<User> actualUser = userDao.findById(null);
        assertThat(actualUser).isEmpty();
    }

    @Test
    void shouldSaveUserCorrectly() {
        var createdUser = MockUserBuilder.buildUser();
        var savedUser = userDao.save(createdUser);

        assertEquals(createdUser, savedUser);
    }

    @Test
    void shouldSaveUserWithCorrectlyWithNullGenderAndRoleFields() {
        var createdUser = MockUserBuilder.buildShortUser();
        var savedUser = userDao.save(createdUser);

        assertNull(savedUser.getGender());
    }

    @Test
    void shouldUpdateUserCorrectly() {
        Optional<User> optionalUserFromDb = userDao.findById(1);

        assertThat(optionalUserFromDb).isPresent();

        User userFromDb = optionalUserFromDb.get();
        userFromDb.setName("Vasia");
        userFromDb.setEmail("vasia@gmail.com");
        userFromDb.setPassword("111");

        userDao.update(userFromDb);
        Optional<User> optionalUserFromDbAfterUpdating = userDao.findById(1);

        assertThat(optionalUserFromDbAfterUpdating).isPresent();
        assertEquals(userFromDb, optionalUserFromDbAfterUpdating.get());
    }

    @CsvFileSource(resources = "/usersIdToDeleteWithResultsTrue.csv", numLinesToSkip = 1)
    @ParameterizedTest
    void shouldDeleteUserCorrectly(Integer id, boolean result) {
        userDao.delete(id);
        assertTrue(result);
    }

    @CsvFileSource(resources = "/usersIdToDeleteWithResultsFalse.csv", numLinesToSkip = 1)
    @ParameterizedTest
    void shouldNotDeleteUserWithNotExistsId(Integer id, boolean result) {
        userDao.delete(id);
        assertFalse(result);
    }

    @Test
    void shouldFindUserByEmailAndPasswordCorrectly() {
        var expectedUser = MockExistsDbUserBuilder.getDBUserKate();
        Optional<User> actualUser = userDao.findByEmailAndPassword(expectedUser.getEmail(), expectedUser.getPassword());

        assertThat(actualUser).isPresent().contains(expectedUser);
    }

    @Test
    void shouldNotFindUserByEmailAndPasswordIfEmailIsIncorrect() {
        var existsDbUser = MockExistsDbUserBuilder.getDBUserKate();
        Optional<User> actualUser = userDao.findByEmailAndPassword("incorrectEmail@gmail.com", existsDbUser.getPassword());

        assertThat(actualUser).isEmpty();
    }

    @Test
    void shouldNotFindUserByEmailAndPasswordIfPasswordIsIncorrect() {
        var existsDbUser = MockExistsDbUserBuilder.getDBUserKate();
        Optional<User> actualUser = userDao.findByEmailAndPassword(existsDbUser.getEmail(), "incorrectPassword");

        assertThat(actualUser).isEmpty();
    }

    @Test
    void fullCrudFlow() {
        var deleteAllResult = userDao.deleteAll();

        assertThat(deleteAllResult).isNotZero();
        assertThat(userDao.findAll()).isEmpty();

        List<User> users = MockUserBuilder.buildUserList(4);
        List<User> savedUsers = new ArrayList<>();
        users.forEach(user ->
                savedUsers.add(userDao.save(user)));

        assertEquals(users.get(0), savedUsers.get(0));
        assertEquals(users.get(1), savedUsers.get(1));
        assertEquals(users.get(2), savedUsers.get(2));
        assertEquals(users.get(3), savedUsers.get(3));
        assertThat(userDao.findAll()).hasSize(4);

        User userToUpdate = savedUsers.get(0);
        userToUpdate.setEmail("updatedEmail");
        userDao.update(userToUpdate);
        Optional<User> updatingUser = userDao.findById(savedUsers.get(0).getId());

        assertThat(updatingUser).isPresent();
        assertEquals(userToUpdate, updatingUser.get());

        var afterAllDeletingResult = userDao.deleteAll();
        assertThat(afterAllDeletingResult).isNotZero();

        assertThat(userDao.findAll()).isEmpty();
    }
}