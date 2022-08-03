package com.schindler.todoapp.dto.user.datas;

import com.schindler.todoapp.domain.MyAppUser;
import com.schindler.todoapp.domain.enums.UserRole;

import java.util.ArrayList;
import java.util.List;

public class UserForRefreshToken {

    private String userName;
    private List<UserRole> userRole = new ArrayList<>();

    public UserForRefreshToken(MyAppUser user) {
        this.userName = user.getUserName();
        this.userRole = user.getUserRole();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(List<UserRole> userRole) {
        this.userRole = userRole;
    }
}
