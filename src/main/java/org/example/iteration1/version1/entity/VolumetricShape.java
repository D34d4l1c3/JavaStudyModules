package org.example.iteration1.version1.entity;


import java.math.BigDecimal;

public abstract class VolumetricShape extends Shape {
    private final BigDecimal volume;
    private final BigDecimal weight;

    public VolumetricShape(BigDecimal area, BigDecimal volume, BigDecimal weight, Integer scale) {
        super(area, scale);
        this.volume = volume;
        this.weight = weight;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public BigDecimal getWeight() {
        return weight;
    }
}
