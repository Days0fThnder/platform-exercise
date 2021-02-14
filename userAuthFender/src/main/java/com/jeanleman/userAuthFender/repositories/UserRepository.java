package com.jeanleman.userAuthFender.repositories;

import com.jeanleman.userAuthFender.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
