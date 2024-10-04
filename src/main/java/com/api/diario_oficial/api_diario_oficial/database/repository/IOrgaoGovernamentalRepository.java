package com.api.diario_oficial.api_diario_oficial.database.repository;

import com.api.diario_oficial.api_diario_oficial.entity.OrgaoGovernamental;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrgaoGovernamentalRepository extends IRepositoryBase<OrgaoGovernamental, Long>, JpaSpecificationExecutor<OrgaoGovernamental> {
}
