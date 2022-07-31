package com.schindler.todoapp.domain.enums;

public enum UserRole {
    ROLE_USER("user"),
    ROLE_ADMIN("admin");

    private final String profileType;

    UserRole(String profileType) {
        this.profileType = profileType;
    }

    public String getProfileType() {
        return profileType;
    }
}
