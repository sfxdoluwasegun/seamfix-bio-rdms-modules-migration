package com.seamfix.bio.job.jpa.dao;

import com.seamfix.bio.entities.AppUser;
import com.seamfix.bio.entities.IclockerUserExt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Repository
public interface IclockerUserExtRepository extends JpaRepository<IclockerUserExt, Long> {

    public IclockerUserExt findByUid(AppUser uid);

    public IclockerUserExt findByUserId(String userId);
}
