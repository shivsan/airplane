package com.aerodrome.aeroplane.models;

import org.apache.commons.lang3.builder.EqualsBuilder;

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
}
