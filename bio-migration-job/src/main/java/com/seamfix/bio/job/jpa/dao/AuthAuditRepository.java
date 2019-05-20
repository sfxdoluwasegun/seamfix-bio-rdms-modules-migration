package com.seamfix.bio.job.jpa.dao;

import com.seamfix.bio.entities.AuthAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AuthAuditRepository extends JpaRepository<AuthAudit, Long> {

    List<AuthAudit> findByProviderJobId(String providerJobId);

    AuthAudit findTopByOrderByLastModifiedDesc();

    @Modifying
    @Query("UPDATE AuthAudit e SET e.reconciled = :reconciled, e.lastModified =:lastModified  WHERE e.providerJobId = :providerJobId")
    int updateAuthAudit(@Param("reconciled") boolean reconciled, @Param("providerJobId") String providerJobId, @Param("lastModified") Date lastModified );
}
