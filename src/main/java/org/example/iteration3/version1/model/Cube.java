package org.example.iteration3.version1.model;

import lombok.Getter;
import org.example.iteration3.version1.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.MathContext;

@Getter
public class Cube extends VolumetricShape {
    @NotNull
    private final BigDecimal side;

    public Cube(@NotNull BigDecimal side, @Nullable BigDecimal weight, Integer scale) {
        super(calcArea(side),calcVolume(side), weight, scale);
        this.side = side.setScale(scale, MathContext.UNLIMITED.getRoundingMode());
    }
    private static BigDecimal calcArea(@NotNull BigDecimal side){
        return Utils.validNumber(side).pow(2).multiply(BigDecimal.valueOf(6));
    }
    private static BigDecimal calcVolume(@NotNull BigDecimal side){
        return Utils.validNumber(side).pow(3);
    }
}
