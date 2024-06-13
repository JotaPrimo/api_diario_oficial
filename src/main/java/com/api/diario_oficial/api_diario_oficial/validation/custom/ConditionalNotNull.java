package com.api.diario_oficial.api_diario_oficial.validation.custom;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ConditionalNotNullValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ConditionalNotNull {
    String message() default "Field must not be null under condition";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String field();
    String dependsOnField();
    String dependsOnFieldValue();

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        ConditionalNotNull[] value();
    }
}