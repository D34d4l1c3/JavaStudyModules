package org.example.iteration5.WarSimulate;

import lombok.Data;

@Data
public class Solder implements IUnit{
    Integer health;
    final Integer damage;
    Boolean alive = true;

    @Override
    public Integer attack() {
        return damage;
    }

    @Override
    public Boolean tryKill(Integer damage) {
       int res = health - damage;
       if(res <= 0) {
           alive = false;
           health = 0;
       } else {
           health =  health - res;
       }
      return alive;
    }
}
