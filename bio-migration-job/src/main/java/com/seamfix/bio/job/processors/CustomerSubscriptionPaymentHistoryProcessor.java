package com.seamfix.bio.job.processors;

import org.slf4j.Logger;
import java.util.Date;
import org.springframework.batch.item.ItemProcessor;
import org.slf4j.LoggerFactory;
import com.sf.biocloud.entity.SubscriptionPaymentHistory;
import com.seamfix.bio.entities.CustomerSubscriptionPaymentHistory;
import com.seamfix.bio.jpa.dao.CustomerSubscriptionPaymentHistoryRespository;

import static com.seamfix.bio.util.StringUtil.isNullOrEmpty;


public class CustomerSubscriptionPaymentHistoryProcessor implements ItemProcessor<SubscriptionPaymentHistory, CustomerSubscriptionPaymentHistory> {

    private static final Logger log = LoggerFactory.getLogger(CustomerSubscriptionPaymentHistoryProcessor.class);
    private CustomerSubscriptionPaymentHistoryRespository repository;

    public CustomerSubscriptionPaymentHistoryProcessor(CustomerSubscriptionPaymentHistoryRespository repository) {
        this.repository = repository;
    }

    @Override
    public CustomerSubscriptionPaymentHistory process(SubscriptionPaymentHistory subscriptionPaymentHistory) throws Exception {
        CustomerSubscriptionPaymentHistory converted = new CustomerSubscriptionPaymentHistory();
        converted.setActive(subscriptionPaymentHistory.isActive());
        converted.setReference(isNullOrEmpty(subscriptionPaymentHistory.getReference()) ? "" : subscriptionPaymentHistory.getReference());
        converted.setTotalAmount(subscriptionPaymentHistory.getTotalAmount());
        converted.setAmountPaid(subscriptionPaymentHistory.getAmountPaid());
        converted.setOrgId(isNullOrEmpty(subscriptionPaymentHistory.getOrgId()) ? "" : subscriptionPaymentHistory.getOrgId());
        converted.setSubscriptionPlanId(isNullOrEmpty(subscriptionPaymentHistory.getSubscriptionPlanId()) ? "" : subscriptionPaymentHistory.getSubscriptionPlanId());
        converted.setCurrency(isNullOrEmpty(subscriptionPaymentHistory.getCurrency()) ? "" : subscriptionPaymentHistory.getCurrency());
        converted.setConversionRate(subscriptionPaymentHistory.getConversionRate());
        converted.setResponseStatus(isNullOrEmpty(subscriptionPaymentHistory.getResponseStatus()) ? "" : subscriptionPaymentHistory.getResponseStatus());
        converted.setResponseCode(isNullOrEmpty(subscriptionPaymentHistory.getResponseCode()) ? "" : subscriptionPaymentHistory.getResponseCode());

        if (subscriptionPaymentHistory.getEndDate() != null) {
            converted.setEndDate(new Date(subscriptionPaymentHistory.getEndDate()));
        }
        if (subscriptionPaymentHistory.getCreated() != null) {
            converted.setCreateDate(new Date(subscriptionPaymentHistory.getEndDate()));
        }
        if (subscriptionPaymentHistory.getBillingCycle() != null) {
            converted.setBillingCycle(subscriptionPaymentHistory.getBillingCycle());
        }
        if (subscriptionPaymentHistory.getPaymentMode() != null) {
            converted.setPaymentMode(subscriptionPaymentHistory.getPaymentMode());
        }
        if (subscriptionPaymentHistory.getCreated() != null) {
            converted.setCreateDate(new Date(subscriptionPaymentHistory.getEndDate()));
        }
        repository.save(converted);
        return converted;
    }

}
