package com.api.diario_oficial.api_diario_oficial.validation.custom;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class RequiredInIntValidator implements ConstraintValidator<RequiredInInt, Integer> {

    private int[] values;

    @Override
    public void initialize(RequiredInInt annotation) {
        this.values = annotation.values();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        boolean result = Arrays.stream(values).anyMatch(v -> v == value);

        if (!result) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addConstraintViolation();
        }

        return result;
    }
}