package com.sabit.core.utils;

public enum Status {
    ACTIVE(1),
    PASSIVE(2),
    DELETED(3);

    int value;

    Status(int value) {
        this.value = value;
    }

    public int get() {
        return this.value;
    }
}
