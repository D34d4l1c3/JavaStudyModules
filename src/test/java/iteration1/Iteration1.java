package iteration1;

import org.example.iteration1.Ball;
import org.example.iteration1.Circle;
import org.example.iteration1.Figure;
import org.example.iteration1.Parallelepiped;
import org.example.iteration1.Rectangle;
import org.example.iteration1.Triangle;
import org.example.iteration1.Volumable;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Iteration1 {
    @Test
    public void testFigures(){
        Triangle triangle = new Triangle("Banana");

        Circle circle = new Circle(3d);
        Ball ball = new Ball(3d);
        Rectangle rectangle = new Rectangle(5d, 3d);
        Parallelepiped parallelepiped = new Parallelepiped(5d, 3d, 2d);

        List<Figure> figures = List.of(circle, ball, rectangle, parallelepiped);
        List volumeFigures = List.of(ball, parallelepiped); // Raw use of parameterized class - ему не нравится но работает
        List<Volumable> volumeFiguresI = List.of(ball, parallelepiped);
//        List<? extends Figure & Volumable> lists = new ArrayList<>();
        printAllPerimeters(figures);
        printAllSquares(figures);

        printAllVolumes(volumeFigures);
        printVolumes(volumeFiguresI);

        System.out.println("Суммарная площадь всех фигур: " + getAllSquaresSum(figures));
    }
    public static void printAllPerimeters(List<? extends Figure> figures) {
        figures.forEach(t -> System.out.println("Периметр " + t.getName() + " " + t.getPerimeter()));
    }

    public static void printAllSquares(List<Figure> figures) {
        figures.forEach(t -> System.out.println("Площадь " + t.getName() + " " + t.getSquare()));
    }

    public static double getAllSquaresSum(List<Figure> figures) {
        return figures.stream().map(Figure::getSquare).reduce(0d, Double::sum);
    }

    public static void printVolumes(List<Volumable> figures) {
        figures.forEach(t -> System.out.println("Объем " + t.getVolume())); //Тут не вытащу свойства фигуры
    }

    public static <T extends Figure & Volumable> void printAllVolumes(List<T> figures) {
        figures.forEach(t -> System.out.println("Объем " + t.getName() + " " + t.getVolume()));
    }
}
