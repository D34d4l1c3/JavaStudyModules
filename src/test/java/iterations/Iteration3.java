package iterations;

import org.example.iteration1.version1.ShapeConstants;
import org.example.iteration1.version1.entity.*;
import org.example.iteration1.version1.service.ShapeService;
import org.example.iteration1.version1.service.Utils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class Iteration3 {
    @Test
    public void testShape() {
        System.out.println(Utils.validNumber(BigDecimal.TEN));
        System.out.println(Utils.validNumber(BigDecimal.valueOf(33)));
//        System.out.println(Utils.validNumber(BigDecimal.valueOf(-657)));
        System.out.println(Utils.validNumber(null));

//        final Circle circle = new Circle(BigDecimal.TEN);
//        final FlatShape square = new Square(BigDecimal.TEN);
//        final Sphere sphere = new Sphere(ShapeConstants.TWO, BigDecimal.valueOf(33));
//        // Есть взаимная зависимость между высотой и боковой стороной (высота <= боковой стороны),
//        // поэтому вместо задания трёх длин лучше использовать длины двух сторон и угол,
//        // здесь сделано для простоты
//        final FlatShape parallelogram = new Parallelogram(BigDecimal.TEN, BigDecimal.ONE, ShapeConstants.TWO);
//        final VolumetricShape cube = new Cube(BigDecimal.TEN, BigDecimal.valueOf(15.123));
//
//        System.out.println("Summary area of all shapes: " + ShapeService.sumArea(circle, square, sphere, parallelogram, cube));
//        System.out.println("Summary perimeter of flat shapes: " +
//                ShapeService.sumPerimeter(circle, square, parallelogram));
//        System.out.println("Summary volume of volumetric shapes: " +
//                ShapeService.sumVolume(sphere, cube));
//        System.out.println("Average radius of round shapes: " +
//                ShapeService.calculateAverageRadius(circle, sphere));
//        System.out.println("Summary weight of volumetric shapes: " +
//                ShapeService.sumWeight(sphere, cube));
    }
}
