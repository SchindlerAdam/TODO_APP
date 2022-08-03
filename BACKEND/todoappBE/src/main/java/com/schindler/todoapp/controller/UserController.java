package com.schindler.todoapp.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schindler.todoapp.domain.enums.UserRole;
import com.schindler.todoapp.dto.user.commands.RegisterNewUserCommand;
import com.schindler.todoapp.dto.user.datas.UserForRefreshToken;
import com.schindler.todoapp.service.UserService;
import com.schindler.todoapp.validation.RegisterUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/users/")
public class UserController {

    private final UserService userService;
    private final RegisterUserValidator registerUserValidator;


    @Autowired
    public UserController(UserService userService, RegisterUserValidator registerUserValidator) {
        this.userService = userService;
        this.registerUserValidator = registerUserValidator;
    }


    @InitBinder("registerNewUserCommand")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(registerUserValidator);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid RegisterNewUserCommand registerNewUserCommand) {
        userService.registerUser(registerNewUserCommand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testApi() {
        return new ResponseEntity<>("JWT WORKS FINE!", HttpStatus.OK);
    }
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secretThatShouldNotBeHere".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String userName = decodedJWT.getSubject();
                UserForRefreshToken user = userService.getUserForRefreshToken(userName);
                String accessToken = JWT.create()
                        .withSubject(userName)
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60* 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getUserRole().stream().map(UserRole::getProfileType).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token",refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {

        }
    }

/*    @Secured("ROLE_USER")
    @PostMapping("/login")
    public ResponseEntity<LoginData> login() {
        return new ResponseEntity<>(userService.login(), HttpStatus.OK);
    }*/
}
