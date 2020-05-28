package com.aerodrome.aeroplane.models;

import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.Optional;

public class Seat {
    int number;
    SeatType type;
    Optional<Integer> customerNumber;

    public Seat() {
        this.customerNumber = Optional.empty();
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = Optional.of(customerNumber);
    }

    public void setType(SeatType type) {
        this.type = type;
    }

    public boolean isOccupied() {
        return customerNumber.isPresent();
    }

    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    public SeatType getType() {
        return type;
    }

    public Optional<Integer> getCustomerNumber() {
        return customerNumber;
    }

    public int getNumber() {
        return number;
    }
}
