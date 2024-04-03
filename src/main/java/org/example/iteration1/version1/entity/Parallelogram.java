package org.example.iteration1.version1.entity;

import org.example.iteration1.version1.ShapeConstants;

import java.math.BigDecimal;

public class Parallelogram extends FlatShape {
    private final BigDecimal base; // Основание: __
    private final BigDecimal height; // Высота: |
    private final BigDecimal side; // Боковая грань: /

    public Parallelogram(BigDecimal base, BigDecimal height, BigDecimal side, Integer scale) {
        super(base.multiply(height),
                base.add(side).multiply(ShapeConstants.TWO),scale);
        this.base = base;
        this.height = height;
        this.side = side;
    }
}
