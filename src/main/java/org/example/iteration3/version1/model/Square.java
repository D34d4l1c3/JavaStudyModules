package org.example.iteration3.version1.model;


import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class Square extends Parallelogram {
    public Square(@NotNull BigDecimal side, Integer scale) {
        super(side, side, side, scale);
    }
}
