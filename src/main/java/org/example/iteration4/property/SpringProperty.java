package org.example.iteration4.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "application2.properties") //Работает но идея упорно твердит что там нет
@Getter
public class SpringProperty {
    @Value("${test-property}")
    Boolean testProperty;
}
