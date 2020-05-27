package com.aerodrome.aeroplane.models;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class RowTest {

    @Test
    public void isFilledWhenAllSeatsHaveACustomerNumberAssigned() {
        final var filledRow = new Row();
        filledRow.seats = new Seat[] {new Seat(), new Seat()};
        Arrays.stream(filledRow.seats)
                .forEach(seat -> seat.setCustomerNumber(0));

        assertEquals(true, filledRow.isFilled());
    }


}