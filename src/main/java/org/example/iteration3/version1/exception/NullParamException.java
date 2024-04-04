package org.example.iteration3.version1.exception;

public class NullParamException extends BaseException {
    public NullParamException(String message) {
        super("Параметр не передан или равен null");
    }
}
