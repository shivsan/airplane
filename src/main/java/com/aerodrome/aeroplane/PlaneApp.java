package com.aerodrome.aeroplane;

import com.aerodrome.aeroplane.factory.PlaneFactory;
import com.aerodrome.aeroplane.output.ConsoleOutputter;
import com.aerodrome.aeroplane.service.PlaneService;

import java.util.stream.IntStream;

public class PlaneApp {
    public static void main(String[] args) {
        int input[][] = new int[][]{new int[]{3, 2}, new int[]{4, 3}, new int[]{2, 3}, new int[]{3, 4}};
        int customerCount = 30;

        final var plane = new PlaneFactory().createPlane(input);
        final var planeService = new PlaneService();


        IntStream.rangeClosed(1,customerCount).forEach(customer -> planeService.addCustomer(plane, customer));

        final var consoleOutputter = new ConsoleOutputter();
        final var output = consoleOutputter.getPlaneStringOutput(plane);

        System.out.println(output);
    }
}
