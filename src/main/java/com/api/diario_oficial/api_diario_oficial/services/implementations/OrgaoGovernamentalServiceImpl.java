package com.api.diario_oficial.api_diario_oficial.services.implementations;

import com.api.diario_oficial.api_diario_oficial.database.repository.IOrgaoGovernamentalRepository;
import com.api.diario_oficial.api_diario_oficial.entity.OrgaoGovernamental;
import com.api.diario_oficial.api_diario_oficial.exceptions.custom.EntityNotFoundException;
import com.api.diario_oficial.api_diario_oficial.services.interfaces.IOrgaoGovernamentalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OrgaoGovernamentalServiceImpl implements IOrgaoGovernamentalService {

    private IOrgaoGovernamentalRepository orgaoGovernamentalRepository;

    public OrgaoGovernamentalServiceImpl(IOrgaoGovernamentalRepository orgaoGovernamentalRepository) {
        this.orgaoGovernamentalRepository = orgaoGovernamentalRepository;
    }

    @Override
    public List<OrgaoGovernamental> findAll() {
        return this.orgaoGovernamentalRepository.findAll();
    }

    @Override
    public Page<OrgaoGovernamental> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<OrgaoGovernamental> findAllSortedById(Pageable pageable) {
        return null;
    }

    @Override
    public OrgaoGovernamental findById(Long id) {
        return null;
    }

    @Override
    public OrgaoGovernamental findOrFail(Long id) throws EntityNotFoundException {
        return null;
    }

    @Override
    public OrgaoGovernamental save(OrgaoGovernamental entity) {
        Objects.requireNonNull(entity, "Objeto de orgaoGovernamental não pode ser nulo");

        return orgaoGovernamentalRepository.save(entity);
    }

    @Override
    public OrgaoGovernamental update(OrgaoGovernamental entity) throws EntityNotFoundException {
        return null;
    }

    @Override
    public void delete(OrgaoGovernamental entity) throws EntityNotFoundException {

    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public boolean notExistsById(Long id) {
        return false;
    }
}
