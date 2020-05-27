package com.aerodrome.aeroplane.models;

import java.util.Arrays;

public class Row {
    Seat[] seats;

    public boolean isFilled() {
        return Arrays.stream(seats)
                .allMatch(seat -> seat.isOccupied());
    }
}
