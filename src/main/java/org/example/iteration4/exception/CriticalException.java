package org.example.iteration4.exception;

import lombok.extern.log4j.Log4j2;
import org.example.iteration3.version1.exception.BaseException;
@Log4j2
public class CriticalException extends BaseException {
    public CriticalException(String message) {
        super("Критическая ошибка: "+message);
        log.error(super.getMessage());
    }
}
