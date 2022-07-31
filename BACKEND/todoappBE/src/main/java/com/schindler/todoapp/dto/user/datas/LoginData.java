package com.schindler.todoapp.dto.user.datas;

import com.schindler.todoapp.domain.User;

public class LoginData {

    private Long userId;
    private String userName;

    public LoginData(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
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
}
