package com.jeanleman.userAuthFender.service.impl;

import com.jeanleman.userAuthFender.model.Token;
import com.jeanleman.userAuthFender.model.User;
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
        return tokenRepository.findByToken(token);
    }

    @Override
    public Optional<Token> findByUserId(long id) {
        return tokenRepository.findByUserId(id);
    }

    @Override
    public Token save(Token token) {
        token.setToken(TokenGenerator.generateNewToken());
        token.setCreated(LocalDateTime.now());
        return tokenRepository.save(token);
    }

    @Override
    public void deleteToken(Token token) {
        tokenRepository.delete(token);
    }

    public Token returnToken(User user){
        Optional<Token> optionalToken = findByUserId(user.getId());
        if(optionalToken.isPresent()){
            return optionalToken.get();
        }

        Token token = new Token();
        token.setUserId(user.getId());
        return save(token);
    }

    public boolean isValidToken(String auth, long id){

        String[] tokens = auth.split(" ");
        String tokenVal = tokens[1];
        Optional<Token> existingToken = findByToken(tokenVal);
        if(existingToken.isPresent()){
            Token token = existingToken.get();
            return token.getUserId() == id;
        }
        return false;
    }
    public Token isValidTokenToDelete(String auth, long id){
        String[] tokens = auth.split(" ");
        String tokenVal = tokens[1];
        Optional<Token> existingToken = findByToken(tokenVal);
        if(existingToken.isPresent()){
            Token token = existingToken.get();
            return token.getUserId() == id ? token : null;
        }
        return null;
    }

}
