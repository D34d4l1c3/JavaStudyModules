package iteration1;

public class Parallelepiped extends Rectangle implements Volumable {
    private double height;

    public Parallelepiped(double width, double length, double height) {
        super("Параллепипед", width, length);
        this.height = height;
    }

    @Override
    public double getSquare() {
        return (getPerimeter() * height) + 2 * super.getSquare();
    }

    @Override
    public double getVolume() {
        return getSquare() * height;
    }
}
