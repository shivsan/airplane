package com.aerodrome.aeroplane.models;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Row {
    Seat[] seats;
    int number;

    public int getNumber() {
        return number;
    }

    public Row(int rowNumber, Seat[] seats) {
        this.seats = seats;
        this.number = rowNumber;
    }

    public Seat[] getSeats() {
        return seats;
    }

    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
