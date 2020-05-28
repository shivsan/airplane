package com.aerodrome.aeroplane.models;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Section {
    Row[] rows;
    int number;

    public Section(Row[] rows, int number) {
        this.rows = rows;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public Row[] getRows() {
        return rows;
    }

    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
