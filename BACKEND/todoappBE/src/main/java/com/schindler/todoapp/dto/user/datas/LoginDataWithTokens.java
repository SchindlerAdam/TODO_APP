package com.schindler.todoapp.dto.user.datas;

import com.schindler.todoapp.security.authorization.TodoUserDetails;

import java.util.HashMap;
import java.util.Map;

public class LoginDataWithTokens {

    private Map<String, String> tokens = new HashMap<>();

    private Long userId;
    private String userName;

    public LoginDataWithTokens(TodoUserDetails todoUserDetails, Map<String, String> tokens) {
        this.userId = todoUserDetails.getUserId();
        this.userName = todoUserDetails.getUsername();
        this.tokens = tokens;
    }

    public Map<String, String> getTokens() {
        return tokens;
    }

    public void setTokens(Map<String, String> tokens) {
        this.tokens = tokens;
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
