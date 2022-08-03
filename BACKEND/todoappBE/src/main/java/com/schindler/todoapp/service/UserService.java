package com.schindler.todoapp.service;


import com.schindler.todoapp.domain.MyAppUser;
import com.schindler.todoapp.dto.user.commands.RegisterNewUserCommand;
import com.schindler.todoapp.dto.user.datas.LoginDataWithTokens;
import com.schindler.todoapp.dto.user.datas.UserForRefreshToken;
import com.schindler.todoapp.repository.UserRepository;
import com.schindler.todoapp.security.authorization.TodoUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegisterNewUserCommand registerNewUserCommand) {
        registerNewUserCommand.setUserRole(registerNewUserCommand.getUserRole().toUpperCase());
        registerNewUserCommand.setUserPassword(passwordEncoder.encode(registerNewUserCommand.getUserPassword()));
        userRepository.save(new MyAppUser(registerNewUserCommand));
    }

    public UserForRefreshToken getUserForRefreshToken(String userName) {
        return new UserForRefreshToken(userRepository.findNonDeletedUserByUserName(userName).orElseThrow(EntityNotFoundException::new));
    }


    // LOGIN + AUTHENTICATION
/*    public LoginData login() {
        return new LoginData(getAuthenticatedUser());
    }

    public MyAppUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findNonDeletedUserByUserName(authentication.getName()).orElseThrow(EntityNotFoundException::new);
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws EntityNotFoundException {
        MyAppUser myAppUser = userRepository.findNonDeletedUserByUserName(username).orElseThrow(EntityNotFoundException::new);
        return new TodoUserDetails(myAppUser);
    }
}
