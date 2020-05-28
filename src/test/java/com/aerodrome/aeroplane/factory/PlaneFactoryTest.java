package com.aerodrome.aeroplane.factory;

import com.aerodrome.aeroplane.models.Row;
import com.aerodrome.aeroplane.models.Seat;
import com.aerodrome.aeroplane.models.SeatType;
import com.aerodrome.aeroplane.models.Section;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class PlaneFactoryTest {
    private PlaneFactory planeFactory;

    @Before
    public void setUp() {
        planeFactory = new PlaneFactory() {
            public Row createRow(int rowNumber, int columnSize) {
                return super.createRow(rowNumber, columnSize);
            }

            public Section createSection(int[] sectionDimensions, int sectionNumber) {
                return super.createSection(sectionDimensions, sectionNumber);
            }
        };
    }

    @Test
    public void createPlane() {
        int[][] input = new int[2][];
        input[0] = new int[]{3, 2};
        input[1] = new int[]{4, 3};

        final var plane = planeFactory.createPlane(input);

        assertEquals(2, plane.getSections().length);
        assertEquals(SeatType.WINDOW, plane.getSections()[0].getRows()[0].getSeats()[0].getType());
        assertEquals(SeatType.MIDDLE, plane.getSections()[0].getRows()[0].getSeats()[1].getType());
        assertEquals(SeatType.AISLE, plane.getSections()[0].getRows()[0].getSeats()[2].getType());
        assertEquals(SeatType.WINDOW, plane.getSections()[1].getRows()[0].getSeats()[3].getType());
    }

    @Test
    public void createSection() {
        final var columnSize = 3;
        final var rowSize = 2;
        final var sectionInput = new int[]{columnSize, rowSize};

        final var section = planeFactory.createSection(sectionInput, 1);

        final var expectedRows = new Row[]{createRow(3, 1), createRow(3, 2)};
        final var expectedSection = new Section(expectedRows, 1);
        assertEquals(expectedSection, section);
    }

    @Test
    public void createRow() {
        final var columnSize = 3;
        final var rowNumber = 1;

        final var row = planeFactory.createRow(rowNumber, columnSize);

        final Row expectedRow = createRow(columnSize, rowNumber);
        assertEquals(expectedRow, row);
    }

    private Row createRow(int columnSize, int rowNumber) {
        final var expectedSeats = new Seat[columnSize];

        for (int i = 0; i < columnSize; i++) {
            expectedSeats[i] = new Seat();
            expectedSeats[i].setNumber(i + 1);
            if (i == 0 || i == columnSize - 1)
                expectedSeats[i].setType(SeatType.AISLE);
            else
                expectedSeats[i].setType(SeatType.MIDDLE);
        }

        return new Row(rowNumber, expectedSeats);
    }
}