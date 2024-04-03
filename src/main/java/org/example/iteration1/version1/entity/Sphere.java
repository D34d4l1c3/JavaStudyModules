package org.example.iteration1.version1.entity;

import org.example.iteration1.version1.ShapeConstants;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Sphere extends VolumetricShape implements IRound {
    // Прим.: У сферы, как и у круга, есть радиус, но нет периметра
    // Поэтому не вполне корректно делать и наследование сферы от круга, и делать круг как сферу с нулевым объёмом
    @NotNull
    private final BigDecimal radius;

    public Sphere(BigDecimal radius, BigDecimal weight, Integer scale) {
        super(radius.pow(3).multiply(ShapeConstants.PI),
                radius.pow(3).multiply(ShapeConstants.PI).multiply(BigDecimal.valueOf(4)).divide(BigDecimal.valueOf(3), RoundingMode.HALF_UP),
                weight, scale);
        this.radius = radius;
    }

    @Override
    public BigDecimal getRadius() {
        return radius;
    }
}
