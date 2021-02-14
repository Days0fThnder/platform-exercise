package com.jeanleman.userAuthFender.service.impl;

import com.jeanleman.userAuthFender.model.User;
import com.jeanleman.userAuthFender.repositories.UserRepository;
import com.jeanleman.userAuthFender.service.IUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements IUser {

    private UserRepository userRepository;

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        User newUser = new User();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        newUser.setCreated(LocalDateTime.now());
        newUser.setLastUpdated(LocalDateTime.now());
        return userRepository.save(newUser);
    }
}
