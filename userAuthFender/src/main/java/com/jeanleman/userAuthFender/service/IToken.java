package com.jeanleman.userAuthFender.service;

import com.jeanleman.userAuthFender.model.Token;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IToken {
    Optional<Token> findByToken(String token);
    Optional<Token> findByUserId(@Param("id")long id);
    Token save(Token token);
    void deleteToken(Token token);
}
