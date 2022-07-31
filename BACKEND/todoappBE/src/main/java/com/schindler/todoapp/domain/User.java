package com.schindler.todoapp.domain;

import com.schindler.todoapp.domain.enums.UserRole;
import com.schindler.todoapp.dto.user.commands.RegisterNewUserCommand;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_password")
    private String userPassword;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role")
    private List<UserRole> userRole = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Todo> todoList = new ArrayList<>();

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "deleted")
    private Boolean isDeleted;

    public User() {
    }

    public User(RegisterNewUserCommand registerNewUserCommand) {
        this.userName = registerNewUserCommand.getUserName();
        this.userEmail = registerNewUserCommand.getUserEmail();
        this.userPassword = registerNewUserCommand.getUserPassword();
        this.registrationDate = LocalDate.parse(registerNewUserCommand.getRegistrationDate());
        if(registerNewUserCommand.getUserRole().equals("ADMIN")) {
            this.userRole.addAll(List.of(UserRole.ROLE_USER, UserRole.ROLE_ADMIN));
        } else {
            this.userRole.add(UserRole.ROLE_USER);
        }
        this.isDeleted = false;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public List<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(List<UserRole> userRole) {
        this.userRole = userRole;
    }

    public List<String> getUserRolesAsString() {
        return userRole.stream().map(Enum::toString).collect(Collectors.toList());
    }

    public List<Todo> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<Todo> todoList) {
        this.todoList = todoList;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
