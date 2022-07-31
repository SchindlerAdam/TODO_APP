package com.schindler.todoapp.domain.enums;

public enum Status {
    INPROGRESS("In progress"),
    DONE("Done");

    private String displayStatus;

    Status(String status) {
        this.displayStatus = status;
    }

    public String getDisplayStatus() {
        return this.displayStatus;
    }
}
