package com.api.diario_oficial.api_diario_oficial.validation.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class ExistsInDatabaseValidator implements ConstraintValidator<ExistsInDatabase, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<?> entityClass;

    @Override
    public void initialize(ExistsInDatabase annotation) {
        this.entityClass = annotation.entityClass();
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // pode mudar para false se não permitir nulos
        }

        Object entity = entityManager.find(entityClass, value);
        return entity != null;
    }
}
