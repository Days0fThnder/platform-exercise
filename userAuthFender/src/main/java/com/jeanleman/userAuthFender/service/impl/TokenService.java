package com.jeanleman.userAuthFender.service.impl;

import com.jeanleman.userAuthFender.model.Token;
import com.jeanleman.userAuthFender.repositories.TokenRepository;
import com.jeanleman.userAuthFender.service.IToken;
import com.jeanleman.userAuthFender.util.TokenGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class TokenService implements IToken {

    private TokenRepository tokenRepository;
    @Override
    public Optional<Token> findByToken(String token) {
        return Optional.empty();
    }

    @Override
    public Token save(Token token) {
        token.setToken(TokenGenerator.generateNewToken());
        token.setCreated(LocalDateTime.now());
        return tokenRepository.save(token);
    }
}
