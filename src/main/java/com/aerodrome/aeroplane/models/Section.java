package com.aerodrome.aeroplane.models;

import java.util.Arrays;

public class Section {
    Row[] rows;

    public boolean isFilled() {
        return Arrays.stream(rows)
                .allMatch(seat -> seat.isFilled());
    }
}
