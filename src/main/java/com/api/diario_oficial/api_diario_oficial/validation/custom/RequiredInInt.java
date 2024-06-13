package com.api.diario_oficial.api_diario_oficial.validation.custom;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RequiredInIntValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredInInt {
    String message() default "must be one of the specified values";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int[] values();
}
