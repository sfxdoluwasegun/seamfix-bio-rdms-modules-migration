package com.seamfix.bio.job.jpa.dao;

import com.seamfix.bio.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    public Project findByPid(String pid);
    
    public Project findTopByOrderByCreateDateDesc();

}
