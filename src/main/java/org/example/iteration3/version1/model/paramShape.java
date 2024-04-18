package org.example.iteration3.version1.model;

public class paramShape<T extends Circle> {
    T arg;

    public paramShape(T arg) {
        this.arg = arg;
    }
}
