package com.seamfix.bio.job.jpa.dao;

import com.seamfix.bio.entities.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Long> {

    public Organisation findByOrgId(String orgId);

}
