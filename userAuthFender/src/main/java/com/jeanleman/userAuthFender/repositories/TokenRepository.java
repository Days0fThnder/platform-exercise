package com.jeanleman.userAuthFender.repositories;

import com.jeanleman.userAuthFender.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("SELECT t FROM Token t where t.userId = :id")
    Optional<Token> findByUserId(@Param("id")long id);
    @Query("SELECT t FROM Token t where t.token = :token")
    Optional<Token> findByToken(@Param("token")String token);
}
