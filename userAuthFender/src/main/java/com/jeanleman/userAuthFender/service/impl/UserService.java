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

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        newUser.setCreated(LocalDateTime.now());
        newUser.setLastUpdated(LocalDateTime.now());
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(User currentUser, User newUser){
        if(isNonNull(newUser.getName()))
            currentUser.setName(newUser.getName());
        if(isNonNull(newUser.getEmail()))
            currentUser.setEmail(newUser.getEmail());
        if(isNonNull(newUser.getPassword()))
            currentUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        currentUser.setLastUpdated(LocalDateTime.now());
        return userRepository.save(currentUser);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    private boolean isNonNull(String userAttr){
        return userAttr != null && !userAttr.isEmpty();
    }
}
