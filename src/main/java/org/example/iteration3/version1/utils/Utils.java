package org.example.iteration3.version1.utils;


import lombok.SneakyThrows;
import org.example.iteration3.version1.model.ShapeConstants;
import org.example.iteration3.version1.exception.BadDataException;
import org.example.iteration3.version1.exception.NullParamException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Optional;

public final class Utils {
    /**
     *
     * @param number
     * @return
     * @param <T>
     */
    @SneakyThrows(NullParamException.class)
    @SuppressWarnings("unchecked")
    public static <T extends Number> T validNumber(@Nullable T number) {
        return BigDecimal.valueOf(Optional.ofNullable(number).orElseThrow(() -> new NullParamException("Пустой параметр")).doubleValue()).signum() == -1 ?
                (T) validNegative(BigDecimal.valueOf(number.doubleValue())) : number;
    }

    @SneakyThrows(BadDataException.class)
    public static BigDecimal validNegative(@NotNull BigDecimal number) {
        if (ShapeConstants.absNegative) {
//            return number.abs().setScale(number.scale(),MathContext.UNLIMITED.getRoundingMode());
            return number.abs();
        } else throw new BadDataException("Отрицательное значение параметра");
    }
}
