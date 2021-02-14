package com.jeanleman.userAuthFender.service;

import com.jeanleman.userAuthFender.model.Token;

import java.util.Optional;

public interface IToken {
    Optional<Token> findByToken(String token);
    Token save(Token token);
}
