package com.aerodrome.aeroplane.models;

import java.util.Arrays;

public class Plane {
    Section[] sections;

    public boolean isFilled() {
        return Arrays.stream(sections)
                .allMatch(seat -> seat.isFilled());
    }
}
