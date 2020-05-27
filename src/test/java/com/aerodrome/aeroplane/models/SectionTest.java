package com.aerodrome.aeroplane.models;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class SectionTest {
    @Test
    public void isFilledWhenAllRowsHaveAreFilled() {
        final var filledSection = new Section();
        final var filledRow = new Row();
        filledRow.seats = new Seat[]{new Seat(), new Seat()};
        Arrays.stream(filledRow.seats)
                .forEach(seat -> seat.setCustomerNumber(0));
        filledSection.rows = new Row[]{filledRow};

        assertEquals(true, filledSection.isFilled());
    }
}