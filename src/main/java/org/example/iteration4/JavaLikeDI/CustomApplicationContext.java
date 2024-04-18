package org.example.iteration4.JavaLikeDI;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.example.iteration4.exception.CriticalException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Log4j2
@UtilityClass
public class CustomApplicationContext { //Что-то вроде инверсии управления теперь он управляет за создание объектов
    private final Map<String, Object> storage = new HashMap<>();
    private final Map<String, Map<Class<?>, Boolean>> context = CustomConfigurationContext.context;


    public <T> T getCustomBean(String name, Class<T> requiredType) {
        Optional<Map<Class<?>, Boolean>> beanOpt = Optional.ofNullable(context.get(name));
        beanOpt.orElseThrow(() -> new CriticalException("Бин с именем " + name + " не найден"));
        Object result;
        if (beanOpt.get().get(requiredType)) {
            result = getNewInstance(requiredType);
        } else {
            result = Optional.ofNullable(storage.get(name)).orElseGet(() -> {
                storage.put(name, getNewInstance(requiredType));
                return storage.get(name);
            });
        }
        return requiredType.cast(result);
    }

    private <T> T getNewInstance(Class<T> requiredType) {
        try {
            return requiredType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
