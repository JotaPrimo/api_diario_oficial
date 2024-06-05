package com.api.diario_oficial.api_diario_oficial.entity;

import com.api.diario_oficial.api_diario_oficial.enums.SimNao;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "arquivos")
@EntityListeners(AuditingEntityListener.class)
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orgao_governamental_id")
    private OrgaoGovernamental orgaoGovernamental;

    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "NAO")
    private SimNao baixado;

    private String path;

    private String fileName;

    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "NAO")
    private SimNao deletado;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Arquivo() {
    }

    @PrePersist
    protected void onCreate() {
        setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    protected void onUpdate() {
        setUpdatedAt(LocalDateTime.now());
    }

    public boolean isBaixado() {
        return SimNao.SIM.equals(this.getBaixado());
    }

    public boolean isNaoBaixado() {
        return SimNao.NAO.equals(this.getBaixado());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SimNao getBaixado() {
        return baixado;
    }

    public void setBaixado(SimNao baixado) {
        this.baixado = baixado;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public SimNao getDeletado() {
        return deletado;
    }

    public void setDeletado(SimNao deletado) {
        this.deletado = deletado;
    }
}