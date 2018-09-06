package com.seamfix.bio.job.jpa.dao;

import org.springframework.stereotype.Repository;
import com.seamfix.bio.entities.CustomerSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CustomerSubscriptionRepository extends JpaRepository<CustomerSubscription, Long> {

    CustomerSubscription findTopByOrderByCreateDateDesc();

    CustomerSubscription findByOrgId(String orgId);
}
