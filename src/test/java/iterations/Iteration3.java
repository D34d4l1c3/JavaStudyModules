package iterations;

import lombok.extern.log4j.Log4j2;
import org.example.iteration3.version1.model.ShapeConstants;
import org.example.iteration3.version1.model.*;
import org.example.iteration3.version1.exception.BadDataException;
import org.example.iteration3.version1.service.ShapeService;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Scanner;

@Log4j2
public class Iteration3 {
    @Test
    public void test3() {
        readWriteFile();
        log.info(itsTheWeekendOld(DayOfWeek.SATURDAY) ? "SATURDAY это выходной" : "SATURDAY не выходной");
        log.info(itsTheWeekendOld(DayOfWeek.MONDAY));
        if (itsTheWeekend(DayOfWeek.SATURDAY)) {
            log.info(DayOfWeek.SATURDAY +" Выходной");
        } else log.info(DayOfWeek.SATURDAY+" Не выходной");

    }

    static void readWriteFile() {
        try (Scanner scanner = new Scanner(new File("src/test/java/iterations/testRead.txt"));
             PrintWriter writer = new PrintWriter(new File("src/test/java/iterations/testWrite.txt"))) {
            while (scanner.hasNext()) {
                writer.print(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new BadDataException("Нет файла");
        }
        finally {
            log.info("А меня видно в конце всегда");
        }
        log.info("При ошибке меня не видно");
    }

    static boolean itsTheWeekendOld(DayOfWeek dow) {
        return switch (dow) {
            case SATURDAY:
            case SUNDAY:
                yield true;

            default:
                yield false;
        };
    }

    static boolean itsTheWeekend(DayOfWeek dow) {
        return switch (dow) {
            case SATURDAY, SUNDAY -> true;
            default -> false;
        };
    }

    @Test
    public void testShape() {
        try {
            final Circle circle = new Circle(BigDecimal.TEN, 2, 1);
            final Circle circleCopy = new Circle(BigDecimal.valueOf(-10), 2, 1);
            log.info(circle.equals(circleCopy)); // true Фигуры разные, но метод переопределен на сравнивание исключительно периметра и площади, где значение по модулю(от параметра)
            final Circle circle2 = new Circle(BigDecimal.valueOf(-33), 1, 1);
            // final Circle circle3 = new Circle(BigDecimal.valueOf(44), 1,null);
            final FlatShape square = new Square(BigDecimal.TEN, 1);
            final Sphere sphere = new Sphere(ShapeConstants.TWO, BigDecimal.valueOf(33), 1);
            final Sphere sphere2 = new Sphere(ShapeConstants.TWO, BigDecimal.valueOf(-33), 1);
            final Sphere sphere3 = new Sphere(ShapeConstants.TWO, BigDecimal.valueOf(-33), 2);
            log.info(sphere.equals(sphere2)); // true
            log.info(sphere.equals(sphere3)); // false - так как разный scale что влияет на точность рассчета периметра и площади из-за чего они отличаются
            final FlatShape parallelogram = new Parallelogram(BigDecimal.TEN, BigDecimal.ONE, ShapeConstants.TWO, 1);
            final VolumetricShape cube = new Cube(BigDecimal.TEN, BigDecimal.valueOf(15.123), 1);

            log.info("Summary area of all shapes: " + ShapeService.sumArea(circle, square, sphere, parallelogram, cube));
            log.info("Summary perimeter of flat shapes: " +
                    ShapeService.sumPerimeter(circle, square, parallelogram));
            log.info("Summary volume of volumetric shapes: " +
                    ShapeService.sumVolume(sphere, cube));
            log.info("Average radius of round shapes: " +
                    ShapeService.calculateAverageRadius(circle, sphere));
            log.info("Summary weight of volumetric shapes: " +
                    ShapeService.sumWeight(sphere, cube));
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            log.info("Конец тестирования");
        }
    }
}
