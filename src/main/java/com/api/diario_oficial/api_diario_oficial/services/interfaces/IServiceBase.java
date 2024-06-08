package com.api.diario_oficial.api_diario_oficial.services.interfaces;

import com.api.diario_oficial.api_diario_oficial.exceptions.custom.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IServiceBase<E, Long> {

    List<E> findAll();

    Page<E> findAll(Pageable pageable);

    Page<E> findAllSortedById(Pageable pageable);

    E findById(Long id);

    E findOrFail(Long id)
            throws EntityNotFoundException;
    E save(E entity);

    E update(E entity, Long id) throws EntityNotFoundException;

    void delete(E entity) throws EntityNotFoundException;

    boolean existsById(Long id);
}