package com.seamfix.bio.job.jpa.dao;

import com.seamfix.bio.entities.AppUser;
import com.seamfix.bio.entities.CapturedDataDemoGraphics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Repository
public interface CapturedDataDemoGraphicsRepository extends JpaRepository<CapturedDataDemoGraphics, Long> {

    public AppUser findByUniqueId(String uniqueId);

    public AppUser findTopByOrderByCreateDateDesc();
}
