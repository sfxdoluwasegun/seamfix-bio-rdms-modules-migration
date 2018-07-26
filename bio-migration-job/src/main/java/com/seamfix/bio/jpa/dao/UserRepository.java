package com.seamfix.bio.jpa.dao;

import com.seamfix.bio.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    public AppUser findByUserId(String userId);

    public AppUser findTopByOrderByCreateDateDesc();
}
