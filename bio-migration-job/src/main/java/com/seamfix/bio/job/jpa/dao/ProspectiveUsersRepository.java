package com.seamfix.bio.job.jpa.dao;

import com.seamfix.bio.entities.ProspectiveUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProspectiveUsersRepository extends JpaRepository<ProspectiveUsers, Long> {
    ProspectiveUsers findByEmail(String email);

    @Modifying
    @Query("UPDATE ProspectiveUsers p SET p.active = :active WHERE p.email = :email")
    int updateActiveStatus(@Param("active") boolean active, @Param("email") String email);
}
