package com.ubnt.testTask.entities;

public enum TimeRange {

    MIN(60000), MIN_5(60000 * 5), HOUR(60000 * 60), DAY(60000 * 60 * 24), ALL_TIME(0);

    private long value;

    TimeRange(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

}
