package com.schindler.todoapp.domain.enums;

public enum Priority {
    HIGH("High"),
    MEDIUM("Medium"),
    SMALL("Small");

    private final String displayPriority;

    Priority(String priority) {
        this.displayPriority = priority;
    }

    public String getPriority() {
        return this.displayPriority;
    }


}
