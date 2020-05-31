package com.aerodrome.aeroplane.models;

public enum SeatType {
    WINDOW('w'),
    AISLE('a'),
    MIDDLE('m');

    private final char suffixValue;

    SeatType(char suffix) {
        this.suffixValue = suffix;
    }

    public char getSuffix() {
        return this.suffixValue;
    }
}
