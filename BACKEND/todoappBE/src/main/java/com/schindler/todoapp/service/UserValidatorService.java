package com.schindler.todoapp.service;


import com.schindler.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserValidatorService {

    private final UserRepository userRepository;


    @Autowired
    public UserValidatorService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Boolean checkExistsUserName(String userName) {
        return userRepository.findNonDeletedUserByUserName(userName).isEmpty();
    }

    public Boolean checkExistsUserEmail(String userEmail) {
        return userRepository.findNonDeletedUserByEmail(userEmail).isEmpty();
    }
}
