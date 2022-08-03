package com.schindler.todoapp.domain.enums;

public enum UserRole {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private final String profileType;

    UserRole(String profileType) {
        this.profileType = profileType;
    }

    public String getProfileType() {
        return profileType;
    }
}
