package com.api.diario_oficial.api_diario_oficial.validation.services;

import com.api.diario_oficial.api_diario_oficial.entity.Endereco;
import com.api.diario_oficial.api_diario_oficial.utils.UtilsValidators;
import org.springframework.stereotype.Service;

@Service
public class ServiceVerification {

    public static void validateIdIsNull(Long id) {
        if (UtilsValidators.longIsNullOrZero(id)) {
            throw new IllegalArgumentException("O ID do endereço não pode ser nulo ou zero");
        }
    }

    public static void validateEntityNull(Endereco endereco) {
        if (endereco == null) {
            throw new IllegalArgumentException("Endereço não podem ser nulo");
        }
    }

    public static void validateEntityOrIdNull(Endereco endereco) {
        if (endereco == null || UtilsValidators.longIsNullOrZero(endereco.getId())) {
            throw new IllegalArgumentException("Endereço e/ou id não podem ser nulo");
        }
    }
}
