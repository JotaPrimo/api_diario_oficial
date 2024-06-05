package com.api.diario_oficial.api_diario_oficial.exceptions.custom;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message) {
        super(message);
    }
}