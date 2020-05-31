package com.aerodrome.aeroplane.factory;

import com.aerodrome.aeroplane.exception.CapacityExceededException;
import com.aerodrome.aeroplane.models.*;

import java.util.Arrays;
import java.util.stream.IntStream;

public class PlaneFactory {
    public Plane createPlane(int[][] input) {
        final var sections = new Section[input.length];
        final var plane = new Plane(sections);

        for (int sectionNumber = 0; sectionNumber < input.length; sectionNumber++) {
            sections[sectionNumber] = createSection(input[sectionNumber], sectionNumber + 1);
        }

        final var leftWallIndex = 0;
        setColumnInSectionAsWindow(sections[leftWallIndex], WallSide.LEFT);
        final var rightWallIndex = sections.length - 1;
        setColumnInSectionAsWindow(sections[rightWallIndex], WallSide.RIGHT);

        return plane;
    }

    public Section createSection(int[] sectionDimensions, int sectionNumber) {
        int columnSize = sectionDimensions[0];
        int rowSize = sectionDimensions[1];
        final var rows = new Row[rowSize];
        var section = new Section(rows, sectionNumber);

        for (int row = 0; row < rowSize; row++) {
            rows[row] = createRow(row + 1, columnSize);
        }

        return section;
    }

    protected Row createRow(int rowNumber, int columnSize) {
        final var seats = new Seat[columnSize];
        IntStream.range(0, columnSize).forEach(columnNumber -> {
            seats[columnNumber] = new Seat();
            seats[columnNumber].setNumber(columnNumber + 1);
        });
        setSeatType(seats);
        return new Row(rowNumber, seats);
    }

    private void setSeatType(Seat[] seats) {
        seats[0].setType(SeatType.AISLE);

        IntStream.range(1, seats.length - 1)
                .forEach(i -> seats[i].setType(SeatType.MIDDLE));

        seats[seats.length - 1].setType(SeatType.AISLE);
    }

    protected void setColumnInSectionAsWindow(Section section, WallSide side) {
        final var columnNumber = getWindowColumnForSection(section, side);

        Arrays.stream(section.getRows())
                .flatMap(row -> Arrays.stream(row.getSeats()))
                .filter(seat -> seat.getNumber() == columnNumber + 1)
                .forEach(seat -> seat.setType(SeatType.WINDOW));
    }

    private int getWindowColumnForSection(Section section, WallSide side) {
        switch (side) {
            case LEFT:
                return 0;
            case RIGHT:
                return section.getRows()[0].getSeats().length - 1; // TODO: Write this is a more clearer way.
            default:
                return -1;
        }
    }
    enum WallSide {
        LEFT,
        RIGHT
    }
}
