package com.schindler.todoapp.domain;

import com.schindler.todoapp.dto.user.commands.RegisterNewUserCommand;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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