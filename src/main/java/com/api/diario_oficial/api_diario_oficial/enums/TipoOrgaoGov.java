package com.api.diario_oficial.api_diario_oficial.enums;

public enum TipoOrgaoGov {
    PREFEITURA_MUNICIPAL("Prefeitura Municipal", "Prefeitura Municipal de "),
    GOVERNADORIA_ESTADUAL("Governadoria Estadual", "Governadoria Estadual de ");

    private final String nome;
    private final String descricao;

    TipoOrgaoGov(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

}
