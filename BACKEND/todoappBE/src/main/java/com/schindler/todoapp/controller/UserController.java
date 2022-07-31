package com.schindler.todoapp.controller;

import com.schindler.todoapp.dto.user.commands.RegisterNewUserCommand;
import com.schindler.todoapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todo/user")
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody RegisterNewUserCommand registerNewUserCommand) {
        userService.registerUser(registerNewUserCommand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
