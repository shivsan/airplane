package com.aerodrome.aeroplane.output;

import com.aerodrome.aeroplane.factory.PlaneFactory;
import com.aerodrome.aeroplane.service.PlaneService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertEquals;

public class ConsoleOutputterTest {
    private ConsoleOutputter consoleOutputter;

    @Before
    public void setUp() throws Exception {
        this.consoleOutputter = new ConsoleOutputter();
    }

    @Test
    public void printPlane() {
        final var planeInputWithTripleSection = new int[][]{new int[]{4, 2}, new int[]{3, 3}, new int[]{2, 4}};
        final var plane = new PlaneFactory().createPlane(planeInputWithTripleSection);
        final var planeService = new PlaneService();

        int noOfCustomers = 8;
        IntStream
                .rangeClosed(1, noOfCustomers)
                .forEach(customerNumber -> planeService.addCustomer(plane, customerNumber));

        final var output = consoleOutputter.printPlane(plane);

        assertEquals(outputForPlane(), output);
    }


    @Test
    public void printSection() {
        final var planeInputWithSingleSection = new int[][]{new int[]{4, 2}};
        final var plane = new PlaneFactory().createPlane(planeInputWithSingleSection);
        final var planeService = new PlaneService();

        int noOfCustomers = 5;
        IntStream
                .rangeClosed(1, noOfCustomers)
                .forEach(customerNumber -> planeService.addCustomer(plane, customerNumber));

        final List<String> output = consoleOutputter.printSection(plane.getSections()[0]);

        assertEquals(outputForSingleSection(), output.stream().reduce((row1, row2) -> row1 + "\n" + row2).get());
    }

    @Test
    public void printRow() {
        final var planeInputWithSingleSection = new int[][]{new int[]{4, 2}};
        final var plane = new PlaneFactory().createPlane(planeInputWithSingleSection);
        final var planeService = new PlaneService();

        int noOfCustomers = 5;
        IntStream
                .rangeClosed(1, noOfCustomers)
                .forEach(customerNumber -> planeService.addCustomer(plane, customerNumber));

        final var output = consoleOutputter.getRowText(plane.getSections()[0].getRows()[0]);

        assertEquals("w | 1 | 5 |   | 2 | w", output);
    }

    private String outputForSingleSection() {
        final var stringBuilder = new StringBuilder();
        stringBuilder.append("  ┌---------------┐  ");
        stringBuilder.append("\n");
        stringBuilder.append("w | 1 | 5 |   | 2 | w");
        stringBuilder.append("\n");
        stringBuilder.append("w | 3 |   |   | 4 | w");
        stringBuilder.append("\n");
        stringBuilder.append("  └---------------┘  ");
        return stringBuilder.toString();
    }

    private String outputForPlane() {
        final var stringBuilder = new StringBuilder();
        stringBuilder.append("  ┌---------------┐       ┌-----------┐       ┌-------┐  ");
        stringBuilder.append("\n");
        stringBuilder.append("w |   |   |   | 1 | a   a | 2 |   | 3 | a   a | 4 |   | w");
        stringBuilder.append("\n");
        stringBuilder.append("w |   |   |   | 5 | a   a | 6 |   | 7 | a   a | 8 |   | w");
        stringBuilder.append("\n");
        stringBuilder.append("  └---------------┘     a |   |   |   | a   a |   |   | w");
        stringBuilder.append("\n");
        stringBuilder.append("                          └-----------┘     a |   |   | w");
        stringBuilder.append("\n");
        stringBuilder.append("                                              └-------┘  ");
        return stringBuilder.toString();
    }
}