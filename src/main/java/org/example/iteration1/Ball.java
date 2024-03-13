package org.example.iteration1;

public class Ball extends Circle implements Volumable {
    public Ball(double radius) {
        super("Шар", radius);
    }

    @Override
    public double getSquare() {
        return 4 * super.getSquare();
    }

    @Override
    public double getVolume() {
        return 4d / 3d * super.getSquare();
    }
}
