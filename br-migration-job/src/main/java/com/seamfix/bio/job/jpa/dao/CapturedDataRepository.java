package com.seamfix.bio.job.jpa.dao;

import com.seamfix.bio.entities.CapturedData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Repository
public interface CapturedDataRepository extends JpaRepository<CapturedData, Long> {

    public CapturedData findByUniqueId(String uniqueId);

    public CapturedData findTopByOrderByCreateDateDesc();
}
