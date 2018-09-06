package com.seamfix.bio.job.jpa.dao;

import com.seamfix.bio.entities.OrgType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Repository
public interface OrgTypeRepository extends JpaRepository<OrgType, Long> {

    public OrgType findByName(String name);

}
