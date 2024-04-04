package org.example.iteration3.version1.model;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

public abstract class FlatShape extends Shape {
    @NotNull
    private final BigDecimal perimeter;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FlatShape flatShape = (FlatShape) o;
        return perimeter.equals(flatShape.perimeter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), perimeter);
    }

    public FlatShape(BigDecimal area, @NotNull BigDecimal perimeter, Integer scale) {
        super(area, scale);
        this.perimeter = perimeter.setScale(scale, MathContext.UNLIMITED.getRoundingMode());
    }

    public @NotNull BigDecimal getPerimeter() {
        return perimeter;
    }
}
