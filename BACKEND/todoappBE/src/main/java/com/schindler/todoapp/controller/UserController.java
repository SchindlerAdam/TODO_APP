package com.schindler.todoapp.controller;

import com.schindler.todoapp.dto.user.commands.RegisterNewUserCommand;
import com.schindler.todoapp.dto.user.datas.LoginData;
import com.schindler.todoapp.service.UserService;
import com.schindler.todoapp.validation.RegisterUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user/")
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


    @Secured("ROLE_USER")
    @GetMapping("/login")
    public ResponseEntity<LoginData> login() {
        return new ResponseEntity<>(userService.login(), HttpStatus.OK);
    }
}
