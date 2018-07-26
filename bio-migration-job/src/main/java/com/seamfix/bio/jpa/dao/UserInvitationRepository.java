package com.seamfix.bio.jpa.dao;

import com.seamfix.bio.entities.UserInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Repository
public interface UserInvitationRepository extends JpaRepository<UserInvitation, Long> {

    public UserInvitation findTopByOrderByCreateDateDesc();
}
