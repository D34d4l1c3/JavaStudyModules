package org.example.iteration3.version1.model;

import org.example.iteration3.version1.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.math.BigDecimal;

public class Circle extends FlatShape implements IRound {
    @NotNull
    private final BigDecimal radius;
    @Nullable
    private Integer testNull;

    public Circle(@NotNull BigDecimal radius, Integer scale, @Nullable Integer testNull) {
        super(calcArea(radius),calcPerimeter(radius), scale);
        this.radius = radius;
    }

    @Override
    public @NotNull BigDecimal getRadius() {
        return radius;
    }

    private static @NotNull BigDecimal calcArea(BigDecimal radius) {
        return Utils.validNumber(radius).pow(2).multiply(ShapeConstants.PI);
    }

    private static @NotNull BigDecimal calcPerimeter(BigDecimal radius) {
        return Utils.validNumber(radius).multiply(ShapeConstants.PI).multiply(ShapeConstants.TWO);
    }
}
