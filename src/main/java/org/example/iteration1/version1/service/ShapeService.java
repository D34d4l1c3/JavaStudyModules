package org.example.iteration1.version1.service;


import org.example.iteration1.version1.entity.Shape;
import org.example.iteration1.version1.entity.IRound;
import org.example.iteration1.version1.entity.FlatShape;
import org.example.iteration1.version1.entity.VolumetricShape;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

@Service
public class ShapeService {
    public static BigDecimal sumArea(Shape... shapes) {
        return Arrays.stream(shapes)
                .map(Shape::getArea)
                .reduce(BigDecimal::add)
                .orElseThrow();
    }

    public static BigDecimal sumPerimeter(FlatShape... shapes) {
        return Arrays.stream(shapes)
                .map(FlatShape::getPerimeter)
                .reduce(BigDecimal::add)
                .orElseThrow();
    }

    public static BigDecimal sumVolume(VolumetricShape... shapes) {
        return Arrays.stream(shapes)
                .map(VolumetricShape::getVolume)
                .reduce(BigDecimal::add)
                .orElseThrow();
    }

    public static BigDecimal calculateAverageRadius(IRound... shapes) {
        return Arrays.stream(shapes)
                .map(IRound::getRadius)
                .reduce(BigDecimal::add)
                .map(sum -> sum.divide(BigDecimal.valueOf(shapes.length), RoundingMode.HALF_UP))
                .orElseThrow();
    }

    public static BigDecimal sumWeight(VolumetricShape... shapes) {
        return Arrays.stream(shapes)
                .map(VolumetricShape::getWeight)
                .reduce(BigDecimal::add)
                .orElseThrow();
    }
}
