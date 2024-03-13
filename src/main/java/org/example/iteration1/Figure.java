package org.example.iteration1;

public abstract class Figure {
    private String name;

    public Figure(String name) {
        this.name = name;
        System.out.println("Я абстрактный конструктор фигур и создаю : " + name);
    }

    public abstract double getSquare();

    public abstract double getPerimeter();

    public String getName() {
        return name;
    }

}
