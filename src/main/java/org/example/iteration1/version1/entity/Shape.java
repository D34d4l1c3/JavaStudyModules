package org.example.iteration1.version1.entity;

import lombok.Getter;

import java.math.BigDecimal;


public abstract class Shape {
    private final BigDecimal area;
    private final int scale;

    Shape(BigDecimal area, int scale) {
        this.area = area;
        this.scale = scale;
    }

    public BigDecimal getArea() {
        return area;
    }
}
