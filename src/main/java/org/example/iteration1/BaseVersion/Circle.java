package org.example.iteration1.BaseVersion;

public class Circle extends Figure {
    private double radius;

    public Circle(double radius) {
        super("Круг");
        this.radius = radius;
    }
    public Circle(String name,double radius) {
        super(name);
        this.radius = radius;
    }

    @Override
    public double getSquare() {
        return Math.PI * Math.pow(radius, 2);
    }

    @Override
    public double getPerimeter() {
        return 2 * radius * Math.PI;
    }
    public double getRadius(){
        return radius;
    }
}
