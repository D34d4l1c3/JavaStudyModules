package org.example.iteration3.version1.model;

import org.example.iteration3.version1.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;


public abstract class Shape {
    @NotNull
    private final BigDecimal area;
    @Nullable
    private final Integer scale;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shape shape = (Shape) o;
        return area.equals(shape.area) && Objects.equals(scale, shape.scale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(area, scale);
    }

    Shape(@NotNull BigDecimal area, int scale) {
        this.scale = scale;
        this.area = area.setScale(scale, MathContext.UNLIMITED.getRoundingMode());
    }
    Shape(@NotNull BigDecimal area) {
        this.area = area;
        this.scale = 1;
    }

        public @NotNull BigDecimal getArea() {
        return area;
    }
}
