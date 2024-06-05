package com.api.diario_oficial.api_diario_oficial.enums;

public enum SimNao {
    SIM("SIM"),
    NAO("NAO");

    private final String value;

    SimNao(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}