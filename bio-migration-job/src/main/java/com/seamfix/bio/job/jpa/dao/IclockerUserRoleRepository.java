package com.seamfix.bio.job.jpa.dao;

import com.seamfix.bio.entities.IclockerUserRole;
import com.seamfix.bio.entities.IclockerUserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Repository
public interface IclockerUserRoleRepository extends JpaRepository<IclockerUserRole, IclockerUserRoleId> {

    public IclockerUserRole findByUserId(String userId);

}
