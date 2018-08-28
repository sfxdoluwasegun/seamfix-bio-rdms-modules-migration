package com.seamfix.bio.jpa.dao;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.seamfix.bio.entities.CustomerSubscriptionPaymentHistory;

@Repository
public interface CustomerSubscriptionPaymentHistoryRespository  extends
        JpaRepository<CustomerSubscriptionPaymentHistory, Long> {

    CustomerSubscriptionPaymentHistory findTopByOrderByCreateDateDesc();
}
