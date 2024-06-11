package com.api.diario_oficial.api_diario_oficial.services.implementations;

import com.api.diario_oficial.api_diario_oficial.database.repository.IUsuarioRepository;
import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.enums.Role;
import com.api.diario_oficial.api_diario_oficial.enums.StatusUsuario;
import com.api.diario_oficial.api_diario_oficial.exceptions.custom.EntityNotFoundException;
import com.api.diario_oficial.api_diario_oficial.services.filters.UsuarioSearchSpecification;
import com.api.diario_oficial.api_diario_oficial.services.interfaces.IUsuarioService;
import com.api.diario_oficial.api_diario_oficial.validation.rules.usuario.store.GerenciadorUsuarioValidators;
import com.api.diario_oficial.api_diario_oficial.validation.rules.usuario.update.GerenciadorUsuarioUpdateValidators;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private final IUsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    private GerenciadorUsuarioValidators storeValidators;

    private GerenciadorUsuarioUpdateValidators updateValidators;

    public UsuarioServiceImpl(IUsuarioRepository IUsuarioRepository, PasswordEncoder passwordEncoder, GerenciadorUsuarioValidators usuarioValidators) {
        this.usuarioRepository = IUsuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.storeValidators = usuarioValidators;
    }

    @Override
    public PasswordEncoder passwordEncoder() {
        return null;
    }


    @Override
    @Transactional
    public void inativarUsuario(Long id) throws EntityNotFoundException {
        Usuario usuario = findOrFail(id);
        usuario.setStatusUsuario(StatusUsuario.INATIVO);
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void ativarUsuario(Long id) throws EntityNotFoundException {
        Usuario usuario = findOrFail(id);
        usuario.setStatusUsuario(StatusUsuario.ATIVO);
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findByEmail(String email) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
        return usuarioOptional.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatusUsuario> returnAllStatusUsuario() {
        return List.of();
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario com '%s' não encontrado", username))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Role buscarRolePorUsername(String username) {
        return usuarioRepository.findRoleByUsername(username);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> findAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public Page<Usuario> search(UsuarioSearchDTO searchDto, Pageable pageable) {
        return usuarioRepository.findAll(UsuarioSearchSpecification.toSpecification(searchDto), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> findAllSortedById(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findById(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        return usuarioOptional.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findOrFail(Long id) throws EntityNotFoundException {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario com id '%s' não foi encontrado", id))
        );
    }

    @Override
    @Transactional
    public Usuario save(Usuario usuario) {

       storeValidators
               .getUsuarioValidators()
               .forEach(iUsuarioValidator -> iUsuarioValidator.validate(usuario));

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public Usuario update(Usuario usuario) throws EntityNotFoundException {

        updateValidators
                .getUsuarioValidators()
                .forEach(validators -> validators.validar(usuario));

        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void delete(Usuario entity) throws EntityNotFoundException {
        usuarioRepository.delete(entity);
    }

    @Override
    public boolean existsById(Long id) {
        return usuarioRepository.existsById(id);
    }

    @Override
    public boolean notExistsById(Long id) {
        return !usuarioRepository.existsById(id);
    }
}
