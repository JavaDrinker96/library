package com.gajava.library.controller;

import com.gajava.library.config.jwt.JwtProvider;
import com.gajava.library.controller.request.security.AuthRequest;
import com.gajava.library.controller.request.security.RegistrationRequest;
import com.gajava.library.controller.response.AuthResponse;
import com.gajava.library.model.User;
import com.gajava.library.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public AuthController(final UserService userService, final JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody final RegistrationRequest registrationRequest) {
        final User user = new User();
        user.setPassword(registrationRequest.getPassword());
        user.setLogin(registrationRequest.getLogin());
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> auth(@Valid @RequestBody final AuthRequest request) {
        final User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        final String token = jwtProvider.generateToken(user.getLogin());
        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponse(token));
    }

}
