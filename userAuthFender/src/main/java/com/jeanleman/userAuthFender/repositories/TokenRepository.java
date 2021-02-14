package com.jeanleman.userAuthFender.repositories;

import com.jeanleman.userAuthFender.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {
}
