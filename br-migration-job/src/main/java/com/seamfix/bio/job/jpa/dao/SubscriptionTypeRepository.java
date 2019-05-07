package com.seamfix.bio.job.jpa.dao;

import com.seamfix.bio.entities.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Repository
public interface SubscriptionTypeRepository extends JpaRepository<SubscriptionType, Long> {

    public SubscriptionType findByOldId(String oldId);

    public SubscriptionType findTopByOrderByCreateDateDesc();

}
