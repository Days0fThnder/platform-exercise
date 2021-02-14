package com.jeanleman.userAuthFender.service;

import com.jeanleman.userAuthFender.model.User;

import java.util.Optional;

public interface IUser {
    Optional<User> findById(int id);
    Optional<User> findByEmail(String email);
    User save(User user);
}
