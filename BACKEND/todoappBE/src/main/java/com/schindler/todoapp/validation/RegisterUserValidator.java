package com.schindler.todoapp.validation;

import com.schindler.todoapp.domain.enums.UserRole;
import com.schindler.todoapp.dto.user.commands.RegisterNewUserCommand;
import com.schindler.todoapp.service.UserValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;


@Component
public class RegisterUserValidator implements Validator {

    private final UserValidatorService userValidatorService;


    @Autowired
    public RegisterUserValidator(UserValidatorService userValidatorService) {
        this.userValidatorService = userValidatorService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterNewUserCommand.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        RegisterNewUserCommand registerNewUserCommand = (RegisterNewUserCommand) target;

        if(!userValidatorService.checkExistsUserName(registerNewUserCommand.getUserName())) {
            errors.rejectValue("userName", "username.already.exists");
        }

        if(!userValidatorService.checkExistsUserEmail(registerNewUserCommand.getUserEmail())) {
            errors.rejectValue("userName", "email.already.exists");
        }

        if(registerNewUserCommand.getUserName().equals("") || registerNewUserCommand.getUserName().isEmpty()) {
            errors.rejectValue("userName", "username.empty");
        }
        if(registerNewUserCommand.getUserEmail().equals("") || registerNewUserCommand.getUserEmail().isEmpty()) {
            errors.rejectValue("userEmail", "email.empty");
        }
        if(registerNewUserCommand.getUserPassword().equals("") || registerNewUserCommand.getUserPassword().isEmpty()){
            errors.rejectValue("userRole", "password.empty");
        }
        if(registerNewUserCommand.getUserRole().equals("") || registerNewUserCommand.getUserRole().isEmpty()){
            errors.rejectValue("userRole", "role.empty");
        }

        // Annak ellenőrzése, hogy a jelszó tartalmaz-e legalább 3 számot
        String password = registerNewUserCommand.getUserPassword();
        int numberCounter = 0;
        for(int i = 0; i < password.length(); i++) {
            if(Character.isDigit(password.charAt(i))) {
                numberCounter++;
            }
        }
        // Annak ellenőrzése, hogy a jelszó tartalmaz-e nagy betűt
        int uppercaseCharCounter = 0;
        for (int i = 0; i < password.length(); i++) {
            if(Character.isUpperCase(password.charAt(i))) {
                uppercaseCharCounter++;
            }
        }

        if(numberCounter < 3 || uppercaseCharCounter < 1) {
            errors.rejectValue("userPassword", "invalid.password");
        }

        if(!Objects.equals(registerNewUserCommand.getUserRole(), UserRole.ROLE_USER.getProfileType().toUpperCase())) {
            errors.rejectValue("userRole", "invalid.role");
        }



    }
}
