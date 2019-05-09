package com.seamfix.bio.job.jpa.dao;

import com.seamfix.bio.entities.ReEnrolmentLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReEnrolmentRepository extends JpaRepository<ReEnrolmentLog, Long>{

    public ReEnrolmentLog findTopByOrderByLastModifiedDesc();
}
