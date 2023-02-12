package ru.netology.cloud_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.netology.cloud_api.dto.AuthRq;
import ru.netology.cloud_api.dto.AuthRs;
import ru.netology.cloud_api.security.JwtTokenUtil;
import ru.netology.cloud_api.service.authservice.AuthService;
import ru.netology.cloud_api.service.userservice.UserService;


@RestController
public class AuthController {
    private final JwtTokenUtil jwtTokenUtil;
private final AuthService authService;

    private final UserService userService;

    public AuthController(JwtTokenUtil jwtTokenUtil, UserService userService, AuthService authService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRq authRq) {
        final UserDetails userDetails = userService.getUserByLogin(authRq.getLogin());
        if (userDetails != null) {
            var name = userDetails.getUsername();
            var pass = userDetails.getPassword();
            if (name.equals(authRq.getLogin()) && pass.equals(authRq.getPassword())) {
                final String token = jwtTokenUtil.generateToken(userDetails);
                userService.addTokenToUser(authRq.getLogin(), token);
                return ResponseEntity.status(HttpStatus.OK).body(AuthRs.builder().authToken(token).build());
            }

        }
        return ResponseEntity.status(400).body("Bad credentials");
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("auth-token") String authToken) {
        authService.logoutUser(authToken);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}

