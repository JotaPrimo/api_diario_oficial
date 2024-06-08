package com.api.diario_oficial.api_diario_oficial.services.implementations;

import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.enums.Role;
import com.api.diario_oficial.api_diario_oficial.enums.StatusUsuario;
import com.api.diario_oficial.api_diario_oficial.exceptions.custom.EntityNotFoundException;
import com.api.diario_oficial.api_diario_oficial.database.repository.IUsuarioRepository;
import com.api.diario_oficial.api_diario_oficial.services.interfaces.IUsuarioService;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(IUsuarioRepository IUsuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = IUsuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public PasswordEncoder passwordEncoder() {
        return null;
    }

    @Override
    public List<Usuario> search(UsuarioSearchDTO searchDto) {
        return List.of();
    }

    @Override
    public void inativarUsuario(Long id) throws EntityNotFoundException {

    }

    @Override
    public void ativarUsuario(Long id) throws EntityNotFoundException {

    }

    @Override
    public Usuario findByEmail(String email) {
        return null;
    }

    @Override
    public List<StatusUsuario> returnAllStatusUsuario() {
        return List.of();
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario com '%s' não encontrado", username))
        );
    }

    @Override
    public Role buscarRolePorUsername(String username) {
        return usuarioRepository.findRoleByUsername(username);
    }


    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Page<Usuario> findAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public Page<Usuario> findAllSortedById(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public Usuario findById(Long id) {
        return null;
    }

    @Override
    public Usuario findOrFail(Long id) throws EntityNotFoundException {
        return null;
    }

    @Override
    public Usuario save(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario update(Usuario entity, Long id) throws EntityNotFoundException {
        return null;
    }

    @Override
    public void delete(Usuario entity) throws EntityNotFoundException {

    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }
}
