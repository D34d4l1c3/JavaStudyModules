package org.example.iteration5.WarSimulate;

public interface IUnit {
    Integer getHealth();
    Integer attack();
    Boolean tryKill(Integer damage);
    Boolean getAlive();
}
