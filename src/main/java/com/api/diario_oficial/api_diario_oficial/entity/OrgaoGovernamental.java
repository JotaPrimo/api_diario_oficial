package com.api.diario_oficial.api_diario_oficial.entity;


import com.api.diario_oficial.api_diario_oficial.enums.SimNao;
import com.api.diario_oficial.api_diario_oficial.utils.DataUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orgao_governamentals")
public class OrgaoGovernamental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cnpj;

    @OneToOne(mappedBy = "orgaoGovernamental", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Endereco endereco;

    @OneToMany(mappedBy = "orgaoGovernamental", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diario> diarios = new ArrayList<>();

    @OneToMany(mappedBy = "orgaoGovernamental", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Arquivo> arquivos = new ArrayList<>();

    @OneToMany(mappedBy = "orgaoGovernamental", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cliente> clientes = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "varchar(255) default 'NAO'")
    @Enumerated(EnumType.STRING)
    private SimNao deletado;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public OrgaoGovernamental() {
    }

    public OrgaoGovernamental(String nome, String cnpj) {
        this.nome = nome;
        this.cnpj = cnpj;
    }

    public OrgaoGovernamental(Long id, String nome, String cnpj) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        setDeletado(SimNao.NAO);
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Diario> getDiarios() {
        return diarios;
    }

    public void setDiarios(List<Diario> diarios) {
        this.diarios = diarios;
    }

    public List<Arquivo> getArquivos() {
        return arquivos;
    }

    public void setArquivos(List<Arquivo> arquivos) {
        this.arquivos = arquivos;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public SimNao getDeletado() {
        return deletado;
    }

    public void setDeletado(SimNao deletado) {
        this.deletado = deletado;
    }

    public String getCreatedAtFormatado() {
        return DataUtil.retornaDataFormatadaDMY(this.getCreatedAt());
    }

    public boolean isDeletado() {
        return SimNao.NAO.name().equals(this.deletado.name());
    }

}