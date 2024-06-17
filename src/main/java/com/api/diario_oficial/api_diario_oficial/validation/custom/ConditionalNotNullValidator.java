package com.api.diario_oficial.api_diario_oficial.validation.custom;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


import java.lang.reflect.Field;

public class ConditionalNotNullValidator implements ConstraintValidator<ConditionalNotNull, Object> {

    private String field;
    private String dependsOnField;
    private String dependsOnFieldValue;

    @Override
    public void initialize(ConditionalNotNull constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.dependsOnField = constraintAnnotation.dependsOnField();
        this.dependsOnFieldValue = constraintAnnotation.dependsOnFieldValue();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Field dependsOnField = value.getClass().getDeclaredField(this.dependsOnField);
            dependsOnField.setAccessible(true);
            Object dependsOnFieldValue = dependsOnField.get(value);

            Field field = value.getClass().getDeclaredField(this.field);
            field.setAccessible(true);
            Object fieldValue = field.get(value);

            if (this.dependsOnFieldValue.equals(dependsOnFieldValue)) {
                return fieldValue != null;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}