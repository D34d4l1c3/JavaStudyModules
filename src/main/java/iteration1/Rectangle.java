package iteration1;

public class Rectangle extends Figure {
    private double width;
    private double length;

    public Rectangle(String name, double width, double length) {
        super(name);
        this.width = width;
        this.length = length;
    }
    public Rectangle(double width, double length) {
        super("Прямоугольник");
        this.width = width;
        this.length = length;
    }

    @Override
    public double getSquare() {
        return width * length;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + length);
    }
}
