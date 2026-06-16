package com.mahmoud.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService underTest;


    @Test
    void canGetAllUsers() {
        User user1 = new User(UUID.randomUUID(), "User1");
        User user2 = new User(UUID.randomUUID(), "User2");

        when(userDao.getAllUsers()).thenReturn(List.of(user1, user2));

        assertThat(underTest.getAllUsers()).isEqualTo(List.of(user1, user2));
    }

    @Test
    void canGetUserById() {
        User user = new User(UUID.randomUUID(), "User1");

        when(userDao.getAllUsers()).thenReturn(List.of(user));

        assertThat(underTest.getUserById(user.getId())).isEqualTo(Optional.of(user));
    }
}