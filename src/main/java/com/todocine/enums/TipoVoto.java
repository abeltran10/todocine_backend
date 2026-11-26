package com.todocine.enums;

public enum TipoVoto {
    DISLIKE(0, "DISLIKE"),
    LIKE(1, "LIKE");

    private final int value;
    private final String name;

    TipoVoto(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
