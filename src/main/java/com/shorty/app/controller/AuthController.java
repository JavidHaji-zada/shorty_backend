package com.shorty.app.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shorty.app.entity.ConfirmationToken;
import com.shorty.app.entity.Session;
import com.shorty.app.entity.User;
import com.shorty.app.exception.AccountNotActivatedException;
import com.shorty.app.exception.IncorrectPasswordException;
import com.shorty.app.request.UserCreationRequest;
import com.shorty.app.request.UserLoginRequest;
import com.shorty.app.service.ConfirmationTokenService;
import com.shorty.app.service.SessionService;
import com.shorty.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AuthController {

    private UserService userService;
    private ConfirmationTokenService confirmationTokenService;
    private SessionService sessionService;

    @Autowired
    public AuthController(UserService userService, ConfirmationTokenService confirmationTokenService,
            SessionService sessionService) {
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
        this.sessionService = sessionService;
    }

    @PostMapping("/users")
    public ResponseEntity<?> handleRegister(@Valid @RequestBody UserCreationRequest userCreationRequest) {
        return ResponseEntity.ok(userService.createUser(userCreationRequest));
    }

    @GetMapping("/users")
    public ResponseEntity<?> handleLogin(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        System.out.println("login request " + userLoginRequest.toString());
        User user = userService.loadUserByEmail(userLoginRequest.getEmail());
        System.out.println("User to be logged in " + user.toString());
        if (!user.getPassword().equals(userLoginRequest.getPassword())) {
            throw new IncorrectPasswordException("Wrong password entered");
        }
        if (!user.getEnabled()) {
            System.out.println("User not enabled");
            throw new AccountNotActivatedException("Please activate your account");
        }
        Session session = new Session(user);
        System.out.println("Session created " + session.toString());
        sessionService.saveSession(session);
        return ResponseEntity.ok(session);
    }

    @GetMapping("/users/confirm")
    public String confirmMail(@RequestParam("token") String token) {
        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService
                .findConfirmationTokenByToken(token);
        optionalConfirmationToken.ifPresent(userService::confirmUser);
        return "E-mail Confirmed";
    }
}
