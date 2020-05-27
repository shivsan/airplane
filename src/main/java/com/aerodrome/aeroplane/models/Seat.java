package com.aerodrome.aeroplane.models;

import java.util.Optional;

public class Seat {
    int number;
    SeatType type;
    Optional<Integer> customerNumber;

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = Optional.of(customerNumber);
    }

    public void setType(SeatType type) {
        this.type = type;
    }

    public boolean isOccupied() {
        return customerNumber.isPresent();
    }
}
