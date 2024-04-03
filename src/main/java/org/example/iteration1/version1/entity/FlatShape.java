package org.example.iteration1.version1.entity;

import java.math.BigDecimal;

public abstract class FlatShape extends Shape {
    private final BigDecimal perimeter;

    public FlatShape(BigDecimal area, BigDecimal perimeter, Integer scale) {
        super(area, scale);
        this.perimeter = perimeter;
    }

    public BigDecimal getPerimeter() {
        return perimeter;
    }
}
