package org.example.iteration3.version1.service;


import org.example.iteration3.version1.model.Shape;
import org.example.iteration3.version1.model.IRound;
import org.example.iteration3.version1.model.FlatShape;
import org.example.iteration3.version1.model.VolumetricShape;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;

@Service
public class ShapeService {
    /**
     *
     * @param shapes
     * @return
     */
    public static BigDecimal sumArea(@NotNull Shape... shapes) {
        return Arrays.stream(shapes)
                .map(Shape::getArea)
                .reduce(BigDecimal::add)
                .orElseThrow();
    }

    public static BigDecimal sumPerimeter(@NotNull FlatShape... shapes) {
        return Arrays.stream(shapes)
                .map(FlatShape::getPerimeter)
                .reduce(BigDecimal::add)
                .orElseThrow();
    }

    public static BigDecimal sumVolume(@NotNull VolumetricShape... shapes) {
        return Arrays.stream(shapes)
                .map(VolumetricShape::getVolume)
                .reduce(BigDecimal::add)
                .orElseThrow();
    }

    public static BigDecimal calculateAverageRadius(@NotNull IRound... shapes) {
        return Arrays.stream(shapes)
                .map(IRound::getRadius)
                .reduce(BigDecimal::add)
                .map(sum -> sum.divide(BigDecimal.valueOf(shapes.length), MathContext.UNLIMITED.getRoundingMode()))
                .orElseThrow();
    }

    public static BigDecimal sumWeight(@NotNull VolumetricShape... shapes) {
        return Arrays.stream(shapes)
                .map(VolumetricShape::getWeight)
                .reduce(BigDecimal::add)
                .orElseThrow();
    }
}
