package org.example.iteration3.version1.model;

import lombok.Getter;
import org.example.iteration3.version1.utils.Utils;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;

@Getter
public class Parallelogram extends FlatShape {
    @NotNull
    private final BigDecimal base; // Основание: __
    @NotNull
    private final BigDecimal height; // Высота: |
    @NotNull
    private final BigDecimal side; // Боковая грань: /

    public Parallelogram(@NotNull BigDecimal base, @NotNull BigDecimal height, @NotNull BigDecimal side, Integer scale) {
        super(calcArea(base,height),calcPerimeter(base,side),scale);
        this.base = base.setScale(scale, MathContext.UNLIMITED.getRoundingMode());
        this.height = height.setScale(scale, MathContext.UNLIMITED.getRoundingMode());
        this.side = side.setScale(scale, MathContext.UNLIMITED.getRoundingMode());
    }
    private static BigDecimal calcArea(@NotNull BigDecimal base, @NotNull BigDecimal height){
        return Utils.validNumber(base).multiply(Utils.validNumber(height));
    }
    private static BigDecimal calcPerimeter(@NotNull BigDecimal base, @NotNull BigDecimal side){
        return Utils.validNumber(base).add(Utils.validNumber(side)).multiply(ShapeConstants.TWO);
    }
}
