package com.aerodrome.aeroplane.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class SeatTypeTest {

    @Test
    public void getSuffix() {
        final var enumConstantMiddle = SeatType.MIDDLE;

        assertEquals('m', enumConstantMiddle.getSuffix());
    }
}