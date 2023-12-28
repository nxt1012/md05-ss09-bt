package com.ra.md05ss09bt.controller;

import com.ra.md05ss09bt.payload.request.LoginRequest;
import com.ra.md05ss09bt.payload.request.SignupRequest;
import com.ra.md05ss09bt.payload.response.MessageResponse;
import com.ra.md05ss09bt.payload.response.UserInfoResponse;
import com.ra.md05ss09bt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<MessageResponse> login(@RequestBody LoginRequest loginRequest) {
        MessageResponse message = userService.login(loginRequest);
        if (message != null) {
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserInfoResponse> register(@RequestBody SignupRequest signupRequest) {
        UserInfoResponse registeredUser = userService.register(signupRequest);
        if (registeredUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        }
        return ResponseEntity.internalServerError().build();
    }
}
