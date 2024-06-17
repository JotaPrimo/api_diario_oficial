package com.api.diario_oficial.api_diario_oficial.services.implementations;

import com.api.diario_oficial.api_diario_oficial.database.repository.IEnderecoRepository;
import com.api.diario_oficial.api_diario_oficial.entity.Endereco;
import com.api.diario_oficial.api_diario_oficial.exceptions.custom.EntityNotFoundException;
import com.api.diario_oficial.api_diario_oficial.services.filters.EnderecoSearchSpecification;
import com.api.diario_oficial.api_diario_oficial.services.interfaces.IEnderecoService;
import com.api.diario_oficial.api_diario_oficial.utils.UtilsValidators;
import com.api.diario_oficial.api_diario_oficial.web.dtos.enderecos.EnderecoSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoServiceImpl implements IEnderecoService {

    private final IEnderecoRepository enderecoRepository;

    public EnderecoServiceImpl(IEnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @Override
    public List<Endereco> findAll() {
        return enderecoRepository.findAll();
    }

    @Override
    public Page<Endereco> findAll(Pageable pageable) {
        return enderecoRepository.findAll(pageable);
    }

    @Override
    public Page<Endereco> findAllSortedById(Pageable pageable) {
        Pageable sortedById = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id"));
        return enderecoRepository.findAll(sortedById);
    }

    @Override
    public Endereco findById(Long id) {

        validateIdIsNull(id);

        Optional<Endereco> endereco = enderecoRepository.findById(id);
        return endereco.orElse(null);
    }

    @Override
    public Endereco findOrFail(Long id) throws EntityNotFoundException {
        validateIdIsNull(id);

        return enderecoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Usuario com username '%s' não foi encontrado", id)));
    }

    @Override
    public Endereco save(Endereco entity) {
        validateEntityNull(entity);
        return enderecoRepository.save(entity);
    }

    @Override
    public Endereco update(Endereco entity) throws EntityNotFoundException {
        validateEntityOrIdNull(entity);

        return enderecoRepository.save(entity);
    }

    @Override
    public void delete(Endereco entity) throws EntityNotFoundException {
        validateEntityOrIdNull(entity);

        Endereco enderecoForDelete = findOrFail(entity.getId());
        enderecoRepository.delete(enderecoForDelete);
    }

    @Override
    public boolean existsById(Long id) {
        validateIdIsNull(id);

        return enderecoRepository.existsById(id);
    }

    @Override
    public boolean notExistsById(Long id) {
        validateIdIsNull(id);

        return !enderecoRepository.existsById(id);
    }

    @Override
    public Page<Endereco> search(EnderecoSearchDTO searchDto, Pageable pageable) {
        return enderecoRepository.findAll(EnderecoSearchSpecification.toSpecification(searchDto), pageable);
    }

    private void validateIdIsNull(Long id) {
        if (UtilsValidators.longIsNullOrZero(id)) {
            throw new IllegalArgumentException("O ID do endereço não pode ser nulo ou zero");
        }
    }

    private void validateEntityNull(Endereco endereco) {
        if (endereco == null) {
            throw new IllegalArgumentException("Endereço não podem ser nulo");
        }
    }

    private void validateEntityOrIdNull(Endereco endereco) {
        if (endereco == null || UtilsValidators.longIsNullOrZero(endereco.getId())) {
            throw new IllegalArgumentException("Endereço e/ou id não podem ser nulo");
        }
    }
}
