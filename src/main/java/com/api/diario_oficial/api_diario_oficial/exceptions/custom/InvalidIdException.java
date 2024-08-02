package com.api.diario_oficial.api_diario_oficial.exceptions.custom;

public class InvalidIdException extends RuntimeException {
    public InvalidIdException(String message) {
        super(message);
    }
}
