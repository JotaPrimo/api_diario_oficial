package com.api.diario_oficial.api_diario_oficial.database.repository;

import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.enums.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends IRepositoryBase<Usuario, Long>, JpaSpecificationExecutor<Usuario> {

    Optional<Usuario> findByUsername(String username);

    @Query("select u.role from Usuario u where u.username like :username")
    Role findRoleByUsername(String username);

    Optional<Usuario> findByEmail(String email);

    @Query("SELECT COUNT(u) > 0 FROM Usuario u WHERE u.id <> :id AND u.username = :username")
    boolean existsByUsernameAndIdNot(@Param("username") String username, @Param("id") Long id);

    @Query("SELECT COUNT(u) > 0 FROM Usuario u WHERE u.id <> :id AND u.email = :username")
    boolean existsByEmailAndIdNot(String email, Long id);
}
