package iteration1;

public class Triangle extends Figure {

    public Triangle(String name) {
        super(name);
        System.out.println("Я конструктор треугольников и создаю треугольник " + name);
    }

    @Override
    public double getSquare() {
        return 0;
    }

    @Override
    public double getPerimeter() {
        return 0;
    }
}
