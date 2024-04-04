package org.example.iteration3.version1.model;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.MathContext;

public abstract class VolumetricShape extends Shape {
    @NotNull
    private final BigDecimal volume;
    @NotNull
    private final BigDecimal weight;

    public VolumetricShape(BigDecimal area, @NotNull BigDecimal volume, @NotNull BigDecimal weight, Integer scale) {
        super(area, scale);
        this.volume = volume.setScale(scale, MathContext.UNLIMITED.getRoundingMode());
        this.weight = weight.setScale(scale, MathContext.UNLIMITED.getRoundingMode());
    }

    public @NotNull BigDecimal getVolume() {
        return volume;
    }

    public @NotNull BigDecimal getWeight() {
        return weight;
    }
}
