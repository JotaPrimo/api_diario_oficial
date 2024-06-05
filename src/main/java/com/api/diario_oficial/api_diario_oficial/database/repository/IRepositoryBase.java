package com.api.diario_oficial.api_diario_oficial.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface IRepositoryBase<T, ID> extends JpaRepository<T, ID> {
    @Query("SELECT u FROM #{#entityName} u ORDER BY u.id")
    List<T> findAllOrderById();
}