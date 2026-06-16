package com.mahmoud.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserArrayDataAccessServiceTest {

    private UserArrayDataAccessService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserArrayDataAccessService();
    }

    @Test
    void canGetAllUsers() {
        assertThat(underTest.getAllUsers().size()).isEqualTo(2);
    }
}