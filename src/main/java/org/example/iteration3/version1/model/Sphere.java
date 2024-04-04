package org.example.iteration3.version1.model;

import org.example.iteration3.version1.utils.Utils;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

public class Sphere extends VolumetricShape implements IRound {
    // Прим.: У сферы, как и у круга, есть радиус, но нет периметра
    // Поэтому не вполне корректно делать и наследование сферы от круга, и делать круг как сферу с нулевым объёмом
    @NotNull
    private final BigDecimal radius;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sphere sphere = (Sphere) o;
        return radius.equals(sphere.radius);
    }

    @Override
    public int hashCode() {
        return Objects.hash(radius);
    }

    public Sphere(@NotNull BigDecimal radius, @NotNull BigDecimal weight, Integer scale) {
        super(calcArea(radius),calcVolume(radius), weight, scale);
        this.radius = radius.setScale(scale,MathContext.UNLIMITED.getRoundingMode());
    }

    @Override
    public @NotNull BigDecimal getRadius() {
        return radius;
    }

    private static BigDecimal calcArea(BigDecimal radius) {
        return Utils.validNumber(radius).pow(3).multiply(ShapeConstants.PI);
    }
    private static BigDecimal calcVolume(BigDecimal radius) {
        return radius.pow(3).multiply(ShapeConstants.PI).multiply(BigDecimal.valueOf(4))
                .divide(BigDecimal.valueOf(3), MathContext.UNLIMITED.getRoundingMode());
    }
}
