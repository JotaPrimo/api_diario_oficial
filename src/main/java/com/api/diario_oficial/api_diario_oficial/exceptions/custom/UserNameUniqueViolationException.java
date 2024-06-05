package com.api.diario_oficial.api_diario_oficial.exceptions.custom;

public class UserNameUniqueViolationException extends RuntimeException {
    public UserNameUniqueViolationException(String message) {
        super(message);
    }
}