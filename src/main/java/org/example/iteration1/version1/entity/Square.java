package org.example.iteration1.version1.entity;


import java.math.BigDecimal;

public class Square extends Parallelogram {
    public Square(BigDecimal side, Integer scale) {
        super(side, side, side, scale);
    }
}
