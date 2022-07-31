package com.schindler.todoapp.service;


import com.schindler.todoapp.domain.User;
import com.schindler.todoapp.dto.user.commands.RegisterNewUserCommand;
import com.schindler.todoapp.dto.user.datas.LoginData;
import com.schindler.todoapp.repository.UserRepository;
import com.schindler.todoapp.security.authorization.TodoUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.persistence.EntityExistsException;
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
        userRepository.save(new User(registerNewUserCommand));
    }


    // LOGIN + AUTHENTICATION
    public LoginData login() {
        return new LoginData(getAuthenticatedUser());
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findNonDeletedUserByUserName(authentication.getName()).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws EntityNotFoundException {
        User user = userRepository.findNonDeletedUserByUserName(username).orElseThrow(EntityNotFoundException::new);
        return new TodoUserDetails(user);
    }
}
