package com.aerodrome.aeroplane.models;

import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.Arrays;

public class Plane {
    Section[] sections;

    public Plane(Section[] sections) {
        this.sections = sections;
    }

    public Section[] getSections() {
        return sections;
    }

    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    public int maxCapacity() {
        return (int) Arrays.stream(getSections())
                .flatMap(section -> Arrays.stream(section.getRows()))
                .flatMap(row -> Arrays.stream(row.getSeats()))
                .count();
    }

    public int currentCapacity() {
        return (int) Arrays.stream(getSections())
                .flatMap(section -> Arrays.stream(section.getRows()))
                .flatMap(row -> Arrays.stream(row.getSeats()).filter(seat -> !seat.isOccupied()))
                .count();
    }

}
