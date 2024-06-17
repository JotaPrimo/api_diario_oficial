package com.api.diario_oficial.api_diario_oficial.services.implementations;

import com.api.diario_oficial.api_diario_oficial.database.repository.IClienteRepository;
import com.api.diario_oficial.api_diario_oficial.entity.Cliente;
import com.api.diario_oficial.api_diario_oficial.exceptions.custom.EntityNotFoundException;
import com.api.diario_oficial.api_diario_oficial.services.interfaces.IClienteService;
import com.api.diario_oficial.api_diario_oficial.utils.UtilsValidators;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements IClienteService {

    private IClienteRepository clienteRepository;

    public ClienteServiceImpl(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    @Override
    public Page<Cliente> findAllSortedById(Pageable pageable) {
        Pageable sortedById = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id"));
        return clienteRepository.findAll(sortedById);
    }

    @Override
    public Cliente findById(Long id) {

        if (UtilsValidators.longIsNullOrZero(id)) {
            throw new IllegalArgumentException("ID do cliente não pode ser nulo");
        }

        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElse(null);
    }

    @Override
    public Cliente findOrFail(Long id) throws EntityNotFoundException {
        if (UtilsValidators.longIsNullOrZero(id)) {
            throw new IllegalArgumentException("ID do cliente não pode ser nulo");
        }

        return clienteRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Cliente com id '%s' não foi encontrado", id))
        );
    }

    @Override
    public Cliente save(Cliente entity) {
        return clienteRepository.save(entity);
    }

    @Override
    public Cliente update(Cliente entity) throws EntityNotFoundException {
        if (entity == null || UtilsValidators.longIsNullOrZero(entity.getId())) {
            throw new IllegalArgumentException("Cliente ou ID do cliente não pode ser nulo");
        }
        return clienteRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Cliente entity) throws EntityNotFoundException {

        if (entity == null || UtilsValidators.longIsNullOrZero(entity.getId())) {
            throw new IllegalArgumentException("Cliente ou ID do cliente não pode ser nulo");
        }

        Cliente clienteForDelete = findOrFail(entity.getId());
        clienteRepository.delete(clienteForDelete);
    }

    @Override
    public boolean existsById(Long id) {
        return clienteRepository.existsById(id);
    }

    @Override
    public boolean notExistsById(Long id) {
        return !clienteRepository.existsById(id);
    }
}
