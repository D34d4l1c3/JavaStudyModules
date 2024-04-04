package org.example.iteration3.version1.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.math.BigDecimal;
@Configuration
@Getter
@Setter
//@PropertySource("classpath:application.properties")
public class ShapeConstants {
    public static final BigDecimal PI = BigDecimal.valueOf(3.14159);
    public static final BigDecimal TWO = BigDecimal.valueOf(2);
//    @Value(value = "${app.abs-negative}")
    public static boolean absNegative = true;
}
