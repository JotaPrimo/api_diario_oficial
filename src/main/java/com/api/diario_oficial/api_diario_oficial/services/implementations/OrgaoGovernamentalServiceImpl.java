package com.api.diario_oficial.api_diario_oficial.services.implementations;

import com.api.diario_oficial.api_diario_oficial.database.repository.IOrgaoGovernamentalRepository;
import com.api.diario_oficial.api_diario_oficial.entity.OrgaoGovernamental;
import com.api.diario_oficial.api_diario_oficial.exceptions.custom.EntityNotFoundException;
import com.api.diario_oficial.api_diario_oficial.services.filters.OrgaoGovernamentalSearchSpecification;
import com.api.diario_oficial.api_diario_oficial.services.interfaces.IOrgaoGovernamentalService;
import com.api.diario_oficial.api_diario_oficial.web.dtos.orgao_governamental.OrgaoGovernamentalSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class OrgaoGovernamentalServiceImpl implements IOrgaoGovernamentalService {

    private final IOrgaoGovernamentalRepository repository;

    @Autowired
    public OrgaoGovernamentalServiceImpl(IOrgaoGovernamentalRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<OrgaoGovernamental> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<OrgaoGovernamental> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<OrgaoGovernamental> findAllSortedById(org.springframework.data.domain.Pageable pageable) {
        Pageable sortedById = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.ASC);
        return repository.findAll(sortedById);
    }

    @Override
    public OrgaoGovernamental findById(Long id) {
        Objects.requireNonNull(id, "Id não pode ser null");
        Optional<OrgaoGovernamental> optionalOrgaoGovernamental = repository.findById(id);
        return optionalOrgaoGovernamental.orElse(null);
    }

    @Override
    public OrgaoGovernamental findOrFail(Long id) throws EntityNotFoundException {
        Objects.requireNonNull(id, "Id não pode ser null");
        return repository.findById(id).orElseThrow( () -> new EntityNotFoundException("Orgão governamental com id "+id+" não foi encontrado"));
    }

    @Override
    public OrgaoGovernamental save(OrgaoGovernamental entity) {
        Objects.requireNonNull(entity, "Objeto nullo durante tentativa de persistencia");
        return repository.save(entity);
    }

    @Override
    public OrgaoGovernamental update(OrgaoGovernamental entity) throws EntityNotFoundException {
        Objects.requireNonNull(entity, "Objeto nullo durante tentativa de persistencia");
        return repository.save(entity);
    }

    @Override
    public void delete(OrgaoGovernamental entity) throws EntityNotFoundException {
        Objects.requireNonNull(entity.getId(), "Falha ao tentar deletar. Id o objeto null");
        repository.delete(entity);
    }

    @Override
    public boolean existsById(Long id) {
        Objects.requireNonNull(id, "Id null");
        return repository.existsById(id);
    }

    @Override
    public boolean notExistsById(Long id) {
        Objects.requireNonNull(id, "Id null");
        return !repository.existsById(id);
    }

    @Override
    public Page<OrgaoGovernamental> search(OrgaoGovernamentalSearchDto searchDto, Pageable pageable) {
        Objects.requireNonNull(searchDto, "Objeto de pesquisa não pode ser null");
        return repository.findAll(OrgaoGovernamentalSearchSpecification.toSpecification(searchDto), pageable);
    }
}
