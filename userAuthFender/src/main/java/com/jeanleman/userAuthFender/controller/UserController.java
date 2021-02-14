package com.jeanleman.userAuthFender.controller;

import com.jeanleman.userAuthFender.dto.LoginRequest;
import com.jeanleman.userAuthFender.dto.LogoutRequest;
import com.jeanleman.userAuthFender.model.Token;
import com.jeanleman.userAuthFender.model.User;
import com.jeanleman.userAuthFender.model.UserApiError;
import com.jeanleman.userAuthFender.service.impl.TokenService;
import com.jeanleman.userAuthFender.service.impl.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/auth/")
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
            return ResponseEntity.ok(tokenService.returnToken(existingUser));
        }else{
            UserApiError apiError = new UserApiError(HttpStatus.UNAUTHORIZED, "Invalid Email/Password");
            return new ResponseEntity<>(
                    apiError, new HttpHeaders(), apiError.getStatus());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestHeader("Authorization") String auth,
                                         @RequestBody LogoutRequest logoutRequest){
        Optional<User> user = userService.findByEmail(logoutRequest.getEmail());
        if(!user.isPresent()){
            UserApiError apiError = new UserApiError(HttpStatus.UNAUTHORIZED, "Email not found");
            return new ResponseEntity<>(
                    apiError, new HttpHeaders(), apiError.getStatus());
        }
        User existingUser = user.get();
        Token token = tokenService.isValidTokenToDelete(auth, existingUser.getId());
        if(token == null){
            UserApiError apiError = new UserApiError(HttpStatus.UNAUTHORIZED,
                    "Invalid Token");
            return new ResponseEntity<>(
                    apiError, new HttpHeaders(), apiError.getStatus());
        }
        tokenService.deleteToken(token);
        return ResponseEntity.ok("User logout");
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(name= "id") long id,
                                             @RequestHeader("Authorization") String auth,
                                             @RequestBody User user){

        if(!tokenService.isValidToken(auth, id)){
            UserApiError apiError = new UserApiError(HttpStatus.UNAUTHORIZED,
                    "Invalid Token");
            return new ResponseEntity<>(
                    apiError, new HttpHeaders(), apiError.getStatus());
        }
        Optional<User> existingUser = userService.findById(id);
        if(existingUser.isPresent()){
            return ResponseEntity.ok(userService.updateUser(existingUser.get(), user));
        }else{
            UserApiError apiError = new UserApiError(HttpStatus.NOT_FOUND,
                    "User with " + id + " is Not Found!");
            return new ResponseEntity<>(
                    apiError, new HttpHeaders(), apiError.getStatus());
        }
    }


}
