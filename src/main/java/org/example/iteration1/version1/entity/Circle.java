package org.example.iteration1.version1.entity;

import org.example.iteration1.version1.ShapeConstants;

import java.math.BigDecimal;

public class Circle extends FlatShape implements IRound {
    private final BigDecimal radius;

    public Circle(BigDecimal radius, Integer scale) {
        super(radius.pow(2).multiply(ShapeConstants.PI),
                radius.multiply(ShapeConstants.PI).multiply(ShapeConstants.TWO),scale);
        this.radius = radius;
    }

    @Override
    public BigDecimal getRadius() {
        return radius;
    }
}
