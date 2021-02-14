package com.jeanleman.userAuthFender.controller;

import com.jeanleman.userAuthFender.dto.LoginRequest;
import com.jeanleman.userAuthFender.model.Token;
import com.jeanleman.userAuthFender.model.User;
import com.jeanleman.userAuthFender.model.UserApiError;
import com.jeanleman.userAuthFender.service.impl.TokenService;
import com.jeanleman.userAuthFender.service.impl.UserService;
import com.jeanleman.userAuthFender.util.TokenGenerator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/user/auth")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody User registerUser) {
        Optional<User> user = userService.findByEmail(registerUser.getEmail());
        if(user.isPresent()){
            UserApiError apiError = new UserApiError(HttpStatus.BAD_REQUEST, "Email already exists");
            return new ResponseEntity<>(
                    apiError, new HttpHeaders(), apiError.getStatus());
        }
        return ResponseEntity.ok(userService.save(registerUser));

    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest){
        Optional<User> user = userService.findByEmail(loginRequest.getEmail());
        if(!user.isPresent()){
            UserApiError apiError = new UserApiError(HttpStatus.UNAUTHORIZED, "Email not found");
            return new ResponseEntity<>(
                    apiError, new HttpHeaders(), apiError.getStatus());
        }
        User existingUser = user.get();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(bCryptPasswordEncoder.matches(loginRequest.getPassword(), existingUser.getPassword())){
            Token token = new Token();
            token.setUser(existingUser);
            return ResponseEntity.ok(tokenService.save(token));
        }else{
            UserApiError apiError = new UserApiError(HttpStatus.UNAUTHORIZED, "Invalid Email/Password");
            return new ResponseEntity<>(
                    apiError, new HttpHeaders(), apiError.getStatus());
        }
    }


}
