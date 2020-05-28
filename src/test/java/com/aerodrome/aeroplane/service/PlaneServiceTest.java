package com.aerodrome.aeroplane.service;

import com.aerodrome.aeroplane.factory.PlaneFactory;
import com.aerodrome.aeroplane.models.Plane;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class PlaneServiceTest {
    private PlaneService planeService;

    @Before
    public void setUp() {
        planeService = new PlaneService() {
            public Optional<SeatPosition> getSeatForCustomer(Plane plane) {
                return super.getSeatForCustomer(plane);
            }
        };
    }

    @Test
    public void shouldAddFirstCustomerToFirstSectionFirstRowAisleSeat() {
        final var plane = new PlaneFactory().createPlane(new int[][]{new int[]{2, 2}, new int[]{3, 3}});
        final var firstCustomerId = 1;

        planeService.addCustomer(plane, firstCustomerId);

        final var firstSectionFirstRowAisleSeat = plane.getSections()[0].getRows()[0].getSeats()[1];
        assertTrue(firstSectionFirstRowAisleSeat.isOccupied());
        assertEquals(firstSectionFirstRowAisleSeat.getNumber(), firstCustomerId);
    }

    @Test
    public void shouldGetFirstSectionFirstRowAisleSeat() {
        final var plane = new PlaneFactory().createPlane(new int[][]{new int[]{2, 2}, new int[]{3, 3}});

        final var selectedSeatPositionForCustomer = planeService.getSeatForCustomer(plane);

        final var firstSectionFirstRowAisleSeat = plane.getSections()[0].getRows()[0].getSeats()[1];
        assertEquals(firstSectionFirstRowAisleSeat, selectedSeatPositionForCustomer.get().seat);
    }
}