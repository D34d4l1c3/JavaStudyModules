package org.example.iteration1.version1.service;


import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.util.Optional;

public final class Utils {
    @SneakyThrows
    public static <T extends Number> T validNumber(T number) {
//        Comparator<Number> numberComparator = Comparator.comparing(Number::intValue);
//        System.out.println("результат сравнения c 0 числа " + number + " " + numberComparator.compare(number, 0));
//        numberComparator.thenComparing(num -> num.intValue() > 0)

        if (BigDecimal.valueOf(Optional.ofNullable(number).orElseThrow(() -> new RuntimeException("Пустой аргумент")).doubleValue()).signum() == -1) {
            throw new RuntimeException("Негативное значение");
        } else return number;

    }
}
