package com.api.diario_oficial.api_diario_oficial.validation.custom;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class RequiredInStringValidator implements ConstraintValidator<RequiredInString, String> {

    private String[] values;

    @Override
    public void initialize(RequiredInString annotation) {
        this.values = annotation.values();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // pode mudar para false se não permitir nulos
        }

        boolean result = Arrays.asList(values).contains(value);

        if (!result) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addConstraintViolation();
        }

        return result;
    }
}