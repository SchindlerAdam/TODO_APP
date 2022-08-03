package com.schindler.todoapp.security.authorization;

import com.schindler.todoapp.domain.MyAppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class TodoUserDetails implements UserDetails {

    private final MyAppUser myAppUser;


    public TodoUserDetails(MyAppUser myAppUser) {
        this.myAppUser = myAppUser;
    }

    public Long getUserId() {
        return myAppUser.getUserId();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] userRoles = myAppUser.getUserRolesAsString().toArray(String[]::new);
        return AuthorityUtils.createAuthorityList(userRoles);
    }

    @Override
    public String getPassword() {
        return myAppUser.getUserPassword();
    }

    @Override
    public String getUsername() {
        return myAppUser.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
