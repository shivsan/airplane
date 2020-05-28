package com.aerodrome.aeroplane.service;

import com.aerodrome.aeroplane.models.Plane;
import com.aerodrome.aeroplane.models.Seat;
import com.aerodrome.aeroplane.models.SeatType;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlaneService {
    public void addCustomer(Plane plane, int customerId) {
        final var seatForCustomer = getSeatForCustomer(plane);
        seatForCustomer.get().seat.setCustomerNumber(customerId);
    }

    protected Optional<SeatPosition> getSeatForCustomer(Plane plane) {
        final var seatsWithPositions = new LinkedList<SeatPosition>();

        for (var section : plane.getSections()) {
            for (var row : section.getRows()) {
                for (var seat : row.getSeats()) {
                    if (!seat.isOccupied())
                        seatsWithPositions.add(new SeatPosition(seat, section.getNumber(), row.getNumber()));
                }
            }
        }

        // Filter by Aisle, if empty, filter by windows, if empty filter by middle seats
        List<SeatPosition> categorizedSeats = getSeatsFilteredByType(seatsWithPositions, SeatType.AISLE);

        if (categorizedSeats.isEmpty())
            categorizedSeats = getSeatsFilteredByType(seatsWithPositions, SeatType.WINDOW);

        if (categorizedSeats.isEmpty())
            categorizedSeats = getSeatsFilteredByType(seatsWithPositions, SeatType.MIDDLE);

        if (categorizedSeats.isEmpty())
            return Optional.empty();

        final var topAisleSeatPositions = getTopMostSeats(categorizedSeats);

        return getLeftMostSeat(topAisleSeatPositions);
    }

    private Optional<SeatPosition> getLeftMostSeat(List<SeatPosition> topAisleSeatPositions) {
        return topAisleSeatPositions.stream()
                .sorted((sp1, sp2) -> sp1.sectionNumber - sp1.sectionNumber)
                .findFirst();
    }

    private List<SeatPosition> getTopMostSeats(List<SeatPosition> seatPositions) {
        int topMostSeatRowNumber = seatPositions.stream().map(seatPosition -> seatPosition.rowNumber).min(Integer::compareTo).get();

        return seatPositions.stream()
                .filter(seatPosition -> seatPosition.rowNumber == topMostSeatRowNumber)
                .collect(Collectors.toList());
    }

    private List<SeatPosition> getSeatsFilteredByType(List<SeatPosition> seatsWithPositions, SeatType seatType) {
        return seatsWithPositions.stream()
                .filter(seatPosition -> seatPosition.seatType.equals(seatType))
                .collect(Collectors.toList());
    }

    class SeatPosition {
        Seat seat;
        SeatType seatType;
        int seatNumber;
        int sectionNumber;
        int rowNumber;

        public SeatPosition(Seat seat, int sectionNumber, int rowNumber) {
            this.seat = seat;
            this.seatType = seat.getType();
            this.seatNumber = seat.getNumber();
            this.sectionNumber = sectionNumber;
            this.rowNumber = rowNumber;
        }
    }
}
