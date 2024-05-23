package org.example.iteration5.WarSimulate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


public class Archer extends Solder {
    Integer cntArrow;
    public Archer(Integer damage, Integer cntArrow) {
        super(damage);
        this.cntArrow = cntArrow;
    }

    @Override
    public Integer attack() {
//        if(cntArrow <= 0)
        return 1;
    }
}
