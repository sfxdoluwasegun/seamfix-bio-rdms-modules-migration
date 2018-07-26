package com.seamfix.bio.jpa.dao;

import com.seamfix.bio.entities.BioRegistraUserRole;
import com.seamfix.bio.entities.BioRegistraUserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Repository
public interface BioRegistraUserRoleRepository extends JpaRepository<BioRegistraUserRole, BioRegistraUserRoleId> {

    public BioRegistraUserRole findByUserId(String userId);

}
