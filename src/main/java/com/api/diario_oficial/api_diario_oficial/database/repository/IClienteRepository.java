package com.api.diario_oficial.api_diario_oficial.database.repository;

import com.api.diario_oficial.api_diario_oficial.entity.Cliente;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IClienteRepository extends IRepositoryBase<Cliente, Long>, JpaSpecificationExecutor<Cliente> {
}
