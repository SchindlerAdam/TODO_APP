package com.schindler.todoapp.service;


import com.schindler.todoapp.domain.User;
import com.schindler.todoapp.dto.user.commands.RegisterNewUserCommand;
import com.schindler.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(RegisterNewUserCommand registerNewUserCommand) {
        userRepository.save(new User(registerNewUserCommand));
    }
}
