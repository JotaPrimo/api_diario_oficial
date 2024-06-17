package com.api.diario_oficial.api_diario_oficial.validation.custom;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, String> {

    private ValueOfEnum annotation;

    @Override
    public void initialize(ValueOfEnum annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // pode mudar para false se não permitir nulos
        }

        boolean result = Arrays.stream(annotation.enumClass().getEnumConstants())
                .anyMatch(e -> e.name().equals(value));

        if (!result) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(annotation.message())
                    .addConstraintViolation();
        }

        return result;
    }
}