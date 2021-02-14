package com.jeanleman.userAuthFender.service;

import com.jeanleman.userAuthFender.model.User;

import java.util.Optional;

public interface IUser {
    Optional<User> findById(long id);
    Optional<User> findByEmail(String email);
    User save(User user);
    User updateUser(User currentUser, User newUser);
}
