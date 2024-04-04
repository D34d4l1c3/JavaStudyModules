package org.example.iteration3.version1.exception;

public class BaseException extends RuntimeException { // unchecked т.к RuntimeException а не
    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super("Произошла ошибка:\n" + message);
    }
}
