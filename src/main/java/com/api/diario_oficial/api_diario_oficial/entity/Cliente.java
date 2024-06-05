package com.api.diario_oficial.api_diario_oficial.entity;
import com.api.diario_oficial.api_diario_oficial.utils.DataUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @NotBlank(message = "Nome é um campo obrigatório")
    @Size(min = 5, max = 250, message = "Nome deve ter entre {min} e {max} caracteres")
    private String nome;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Arquivo> arquivos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orgao_governamental_id")
    private OrgaoGovernamental orgaoGovernamental;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Cliente() {
    }

    public Cliente(Usuario usuario, List<Arquivo> arquivos) {
        this.usuario = usuario;
        this.arquivos = arquivos;
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

    public List<Arquivo> getArquivos() {
        return arquivos;
    }

    public void setArquivos(List<Arquivo> arquivos) {
        this.arquivos = arquivos;
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

    public String getCreatedAtFormatado() {
        return DataUtil.retornaDataFormatadaDMY(getCreatedAt());
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public OrgaoGovernamental getOrgaoGovernamental() {
        return orgaoGovernamental;
    }

    public void setOrgaoGovernamental(OrgaoGovernamental orgaoGovernamental) {
        this.orgaoGovernamental = orgaoGovernamental;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}