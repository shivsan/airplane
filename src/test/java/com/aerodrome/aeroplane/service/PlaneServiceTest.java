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

    @Test
    public void shouldGetNextSectionFirstRowAisleSeatIfFirstSectionFirstRowIsOccupied() {
        final var plane = new PlaneFactory().createPlane(new int[][]{new int[]{2, 2}, new int[]{3, 3}});
        plane.getSections()[0].getRows()[0].getSeats()[1].setCustomerNumber(1);

        final var selectedSeatPositionForCustomer = planeService.getSeatForCustomer(plane);

        final var nextSectionFirstRowAisleSeat = plane.getSections()[1].getRows()[0].getSeats()[0];
        assertEquals(nextSectionFirstRowAisleSeat, selectedSeatPositionForCustomer.get().seat);
    }

    @Test
    public void shouldGetLowerRowAisleSeatIfFirstRowAisleSeatsAreAllOccupied() {
        final var plane = new PlaneFactory().createPlane(new int[][]{new int[]{2, 2}, new int[]{3, 3}});
        plane.getSections()[0].getRows()[0].getSeats()[1].setCustomerNumber(1);
        plane.getSections()[1].getRows()[0].getSeats()[0].setCustomerNumber(2);

        final var selectedSeatPositionForCustomer = planeService.getSeatForCustomer(plane);

        final var nextRowAisleSeat = plane.getSections()[0].getRows()[1].getSeats()[1];
        assertEquals(nextRowAisleSeat, selectedSeatPositionForCustomer.get().seat);
    }

    @Test
    public void shouldFirstSectionWindowSeatIfAllAisleSeatsAreAllOccupied() {
        final var plane = new PlaneFactory().createPlane(new int[][]{new int[]{2, 2}, new int[]{3, 3}});
        plane.getSections()[0].getRows()[0].getSeats()[1].setCustomerNumber(1);
        plane.getSections()[0].getRows()[1].getSeats()[1].setCustomerNumber(2);
        plane.getSections()[1].getRows()[0].getSeats()[0].setCustomerNumber(3);
        plane.getSections()[1].getRows()[1].getSeats()[0].setCustomerNumber(4);
        plane.getSections()[1].getRows()[2].getSeats()[0].setCustomerNumber(5);

        final var selectedSeatPositionForCustomer = planeService.getSeatForCustomer(plane);

        final var firstWindowSeat = plane.getSections()[0].getRows()[1].getSeats()[0];
        assertEquals(firstWindowSeat, selectedSeatPositionForCustomer.get().seat);
    }

    @Test
    public void shouldFirstSectionMiddleSeatIfAllAisleAndWindowSeatsAreAllOccupied() {
        final var plane = new PlaneFactory().createPlane(new int[][]{new int[]{2, 2}, new int[]{3, 3}});
        plane.getSections()[0].getRows()[0].getSeats()[1].setCustomerNumber(1);
        plane.getSections()[0].getRows()[1].getSeats()[1].setCustomerNumber(2);
        plane.getSections()[1].getRows()[0].getSeats()[0].setCustomerNumber(3);
        plane.getSections()[1].getRows()[1].getSeats()[0].setCustomerNumber(4);
        plane.getSections()[1].getRows()[2].getSeats()[0].setCustomerNumber(5);

        plane.getSections()[0].getRows()[0].getSeats()[0].setCustomerNumber(6);
        plane.getSections()[0].getRows()[1].getSeats()[0].setCustomerNumber(7);
        plane.getSections()[1].getRows()[0].getSeats()[2].setCustomerNumber(8);
        plane.getSections()[1].getRows()[1].getSeats()[2].setCustomerNumber(9);
        plane.getSections()[1].getRows()[2].getSeats()[2].setCustomerNumber(10);

        final var selectedSeatPositionForCustomer = planeService.getSeatForCustomer(plane);

        final var firstMiddleSeat = plane.getSections()[1].getRows()[0].getSeats()[1];
        assertEquals(firstMiddleSeat, selectedSeatPositionForCustomer.get().seat);
    }

    @Test
    public void shouldReturnEmptyIfAllSeatsAreOccupied() {
        final var plane = new PlaneFactory().createPlane(new int[][]{new int[]{2, 2}, new int[]{3, 3}});
        for (var section : plane.getSections()) {
            for (var row : section.getRows()) {
                for (var seat : row.getSeats()) {
                    seat.setCustomerNumber(1);
                }
            }
        }

        final var returnedSeat = planeService.getSeatForCustomer(plane);

        assertEquals(Optional.empty(), returnedSeat);
    }
}