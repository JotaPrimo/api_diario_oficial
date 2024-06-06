package com.api.diario_oficial.api_diario_oficial.entity;

import com.api.diario_oficial.api_diario_oficial.enums.SimNao;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "diarios")
public class Diario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orgao_governamental_id")
    private OrgaoGovernamental orgaoGovernamental;

    @Column(nullable = false, columnDefinition = "varchar(255) default 'NAO'")
    @Enumerated(EnumType.STRING)
    private SimNao deletado;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Diario() {
    }

    @PrePersist
    protected void onCreate() {
        setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    protected void onUpdate() {
        setUpdatedAt(LocalDateTime.now());
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public OrgaoGovernamental getOrgaoGovernamental() {
        return orgaoGovernamental;
    }

    public void setOrgaoGovernamental(OrgaoGovernamental orgaoGovernamental) {
        this.orgaoGovernamental = orgaoGovernamental;
    }

    @Override
    public String toString() {
        return "Diario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", path='" + path + '\'' +
                ", usuario=" + usuario +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Diario diario = (Diario) o;
        return Objects.equals(id, diario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}