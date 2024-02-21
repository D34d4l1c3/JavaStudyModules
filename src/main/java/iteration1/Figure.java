package iteration1;

public abstract class Figure {
    private String name;

    public Figure(String name) {
        this.name = name;
    }

    public abstract double getSquare();

    public abstract double getPerimeter();

    public String getName() {
        return name;
    }

}
