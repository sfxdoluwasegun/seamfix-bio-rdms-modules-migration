package com.seamfix.bio.job.jpa.dao;

import com.seamfix.bio.entities.IclockerSubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IclockerSubscriptionPlanRepository extends JpaRepository<IclockerSubscriptionPlan, Long> {

    IclockerSubscriptionPlan findByPlanId(String planId);
}
