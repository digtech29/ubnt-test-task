package com.ubnt.testTask.entities;

public enum EventType {

    SUBMISSION("rs"), COMMENT("rc");

    private String value;

    EventType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
