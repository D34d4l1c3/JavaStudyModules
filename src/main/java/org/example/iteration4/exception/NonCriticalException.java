package org.example.iteration4.exception;

import lombok.extern.log4j.Log4j2;
import org.example.iteration3.version1.exception.BaseException;
@Log4j2
public class NonCriticalException extends BaseException {
    public NonCriticalException(String message) {
        super("Не критичная ошибка: " + message);
        log.info(super.getMessage());
    }
}
