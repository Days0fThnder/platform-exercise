package com.jeanleman.userAuthFender.service.impl;

import com.jeanleman.userAuthFender.model.User;
import com.jeanleman.userAuthFender.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;

    private UserService userServiceUnderTest;

    @BeforeEach
    void setUp() {
        userServiceUnderTest = new UserService(mockUserRepository, mockBCryptPasswordEncoder);
    }

    @Test
    void testFindById() {
        // Setup

        // Configure UserRepository.findById(...).
        final Optional<User> user = Optional.of(new User(0L, "name", "email", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0), LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
        when(mockUserRepository.findById(0L)).thenReturn(user);

        // Run the test
        final Optional<User> result = userServiceUnderTest.findById(0L);

        // Verify the results
        assertEquals(user.get(), result.get());
    }

    @Test
    void testFindById_UserRepositoryReturnsAbsent() {
        // Setup
        when(mockUserRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final Optional<User> result = userServiceUnderTest.findById(0L);

        // Verify the results
        assertEquals(Optional.empty(), result);
    }

    @Test
    void testFindByEmail() {
        // Setup

        // Configure UserRepository.findByEmail(...).
        final Optional<User> user = Optional.of(new User(0L, "name", "email", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0), LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
        when(mockUserRepository.findByEmail("email")).thenReturn(user);

        // Run the test
        final Optional<User> result = userServiceUnderTest.findByEmail("email");

        // Verify the results
        assertEquals(user.get(),result.get());
    }

    @Test
    void testFindByEmail_UserRepositoryReturnsAbsent() {
        // Setup
        when(mockUserRepository.findByEmail("email")).thenReturn(Optional.empty());

        // Run the test
        final Optional<User> result = userServiceUnderTest.findByEmail("email");

        // Verify the results
        assertEquals(Optional.empty(), result);
    }

    @Test
    void testSave() {
        // Setup
        final User user = new User(0L, "name", "email", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0), LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockBCryptPasswordEncoder.encode("password")).thenReturn("result");

        // Configure UserRepository.save(...).
        final User user1 = new User(0L, "name", "email", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0), LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockUserRepository.save(any(User.class))).thenReturn(user1);

        // Run the test
        final User result = userServiceUnderTest.save(user);

        // Verify the results
        assertNotNull(result);
    }

    @Test
    void testUpdateUser() {
        // Setup
        final User currentUser = new User(0L, "name", "email", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0), LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final User newUser = new User(0L, "name", "email", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0), LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockBCryptPasswordEncoder.encode("password")).thenReturn("result");

        // Configure UserRepository.save(...).
        final User user = new User(0L, "name", "email", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0), LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockUserRepository.save(any(User.class))).thenReturn(user);

        // Run the test
        final User result = userServiceUnderTest.updateUser(currentUser, newUser);

        // Verify the results
        assertNotNull(result);
    }

    @Test
    void testDelete() {
        // Setup
        final User user = new User(0L, "name", "email", "password", LocalDateTime.of(2020, 1, 1, 0, 0, 0), LocalDateTime.of(2020, 1, 1, 0, 0, 0));

        // Run the test
        userServiceUnderTest.delete(user);

        // Verify the results
        verify(mockUserRepository).delete(any(User.class));
    }
}
