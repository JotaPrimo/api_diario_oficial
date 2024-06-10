package com.api.diario_oficial.api_diario_oficial.exceptions.custom;

public class EmailAlreadyRegistered extends RuntimeException {
    public EmailAlreadyRegistered(String message) {
        super(message);
    }
}
