package com.api.diario_oficial.api_diario_oficial.services.interfaces;

import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.enums.Role;
import com.api.diario_oficial.api_diario_oficial.enums.StatusUsuario;
import com.api.diario_oficial.api_diario_oficial.exceptions.custom.EntityNotFoundException;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IUsuarioService extends IServiceBase<Usuario, Long> {

    PasswordEncoder passwordEncoder();

    Page<Usuario> search(UsuarioSearchDTO searchDto, Pageable pageable);

    void inativarUsuario(Long id) throws EntityNotFoundException;

    void ativarUsuario(Long id) throws EntityNotFoundException;

    Usuario findByEmail(String email);

    List<StatusUsuario> returnAllStatusUsuario();

    Usuario buscarPorUsername(String username);

    Role buscarRolePorUsername(String username);

}