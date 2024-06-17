package com.api.diario_oficial.api_diario_oficial.services.filters;

import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioSearchDTO;
import org.springframework.data.jpa.domain.Specification;

public class UsuarioSearchSpecification {

    public static Specification<Usuario> toSpecification(UsuarioSearchDTO dto) {
        return Specification.where(hasId(dto.id()))
                .and(hasUsername(dto.username()))
                .and(hasEmail(dto.email()))
                .and(hasStatusUsuario(dto.statusUsuario()))
                .and(hasRole(dto.role()));
    }

    private static Specification<Usuario> hasId(Long id) {
        return (root, query, cb) -> (id == null) ? cb.conjunction() : cb.equal(root.get("id"), id);
    }

    private static Specification<Usuario> hasUsername(String username) {
        return (root, query, cb) ->
                (username == null || username.isEmpty())
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get("username")), "%" + username.toLowerCase() + "%");
    }

    private static Specification<Usuario> hasEmail(String email) {
        return (root, query, cb) ->
                (email == null || email.isEmpty())
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");    }

    private static Specification<Usuario> hasStatusUsuario(String statusUsuario) {
        return (root, query, cb) -> (statusUsuario == null || statusUsuario.isEmpty()) ? cb.conjunction() : cb.equal(root.get("statusUsuario"), statusUsuario);
    }

    private static Specification<Usuario> hasRole(String role) {
        return (root, query, cb) -> (role == null || role.isEmpty()) ? cb.conjunction() : cb.equal(root.get("role"), role);
    }

}
