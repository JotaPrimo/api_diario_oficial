package com.api.diario_oficial.api_diario_oficial.services.implementations;

import com.api.diario_oficial.api_diario_oficial.database.repository.IUsuarioRepository;
import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.enums.Role;
import com.api.diario_oficial.api_diario_oficial.enums.StatusUsuario;
import com.api.diario_oficial.api_diario_oficial.exceptions.custom.EntityNotFoundException;
import com.api.diario_oficial.api_diario_oficial.services.filters.UsuarioSearchSpecification;
import com.api.diario_oficial.api_diario_oficial.services.interfaces.IUsuarioService;
import com.api.diario_oficial.api_diario_oficial.utils.UtilsValidators;
import com.api.diario_oficial.api_diario_oficial.validation.rules.usuario.store.GerenciadorUsuarioValidators;
import com.api.diario_oficial.api_diario_oficial.validation.rules.usuario.update.GerenciadorUsuarioUpdateValidators;
import com.api.diario_oficial.api_diario_oficial.web.dtos.usuarios.UsuarioSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public UsuarioServiceImpl(IUsuarioRepository IUsuarioRepository, PasswordEncoder passwordEncoder, GerenciadorUsuarioValidators usuarioValidators, GerenciadorUsuarioUpdateValidators updateValidators) {
        this.usuarioRepository = IUsuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.storeValidators = usuarioValidators;
        this.updateValidators = updateValidators;
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
    public boolean existsByUsernameAndIdNot(String username, Long id) {
        return usuarioRepository.existsByUsernameAndIdNot(username, id);
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
        List<Usuario> usuariosFIltrados = usuarioRepository.findAll(UsuarioSearchSpecification.toSpecification(searchDto));

        return PaginationService.paginate(usuariosFIltrados, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> findAllSortedById(Pageable pageable) {
        Pageable sortedById = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id"));
        return usuarioRepository.findAll(sortedById);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findById(Long id) {

        if (UtilsValidators.longIsNullOrZero(id)) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo");
        }

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        return usuarioOptional.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findOrFail(Long id) throws EntityNotFoundException {

        if (UtilsValidators.longIsNullOrZero(id)) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo");
        }

        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario com id '%s' não foi encontrado", id))
        );
    }

    @Override
    @Transactional
    public Usuario save(Usuario usuario) {

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public Usuario update(Usuario usuario) {
        if (usuario == null || UtilsValidators.longIsNullOrZero(usuario.getId())) {
            throw new IllegalArgumentException("Usuário ou id não pode ser nulo");
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public void validateBeforeSave(Usuario usuario) {
        storeValidators
                .getUsuarioValidators()
                .forEach(validators -> validators.validate(usuario));
    }

    @Override
    public Usuario findOrFailByUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(String.format("Usuario com username '%s' não foi encontrado", username)));
    }

    @Override
    public Usuario findOrFailByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(String.format("Usuario com email '%s' não foi encontrado", email)));
    }

    @Override
    @Transactional(readOnly = true)
    public void validateBeforeUpdate(Usuario usuario) {
        updateValidators
                .getUsuarioValidators()
                .forEach(validators -> validators.validate(usuario));
    }

    @Override
    @Transactional
    public void delete(Usuario entity) throws EntityNotFoundException {

        if (entity == null || UtilsValidators.longIsNullOrZero(entity.getId())) {
            throw new IllegalArgumentException("Usuario ou ID do cliente não pode ser nulo");
        }

        Usuario usuarioForDelete = findOrFail(entity.getId());
        usuarioRepository.delete(usuarioForDelete);
    }

    @Override
    public boolean existsById(Long id) {
        if (UtilsValidators.longIsNullOrZero(id)) {
            throw new IllegalArgumentException("Id do usuário não pode ser nulo");
        }

        return usuarioRepository.existsById(id);
    }

    @Override
    public boolean notExistsById(Long id) {

        if (UtilsValidators.longIsNullOrZero(id)) {
            throw new IllegalArgumentException("Id do usuário não pode ser nulo");
        }

        return !usuarioRepository.existsById(id);
    }
}
