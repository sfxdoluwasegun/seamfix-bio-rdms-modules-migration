package com.seamfix.bio.job.jpa.dao;

import com.seamfix.bio.entities.ProspectiveUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProspectiveUsersRepository extends JpaRepository<ProspectiveUsers, Long> {
}
