package com.api.diario_oficial.api_diario_oficial.database.repository;

import com.api.diario_oficial.api_diario_oficial.entity.Endereco;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IEnderecoRepository extends IRepositoryBase<Endereco, Long>, JpaSpecificationExecutor<Endereco> {
}
