package com.seamfix.bio.job.processors;

import java.util.Date;

import org.slf4j.Logger;
import com.sf.biocloud.entity.Subscription;
import org.slf4j.LoggerFactory;
import com.seamfix.bio.entities.CustomerSubscription;
import org.springframework.batch.item.ItemProcessor;
import com.seamfix.bio.job.jpa.dao.CustomerSubscriptionRepository;
import static com.seamfix.bio.job.util.StringUtil.isNullOrEmpty;


public class CustomerSubscriptionProcessor implements ItemProcessor<Subscription, CustomerSubscription> {

    private static final Logger log = LoggerFactory.getLogger(CustomerSubscriptionProcessor.class);

    private final CustomerSubscriptionRepository customerSubscriptionRepository;

    public CustomerSubscriptionProcessor(CustomerSubscriptionRepository repository) {
        this.customerSubscriptionRepository = repository;
    }

    @Override
    public CustomerSubscription process(Subscription subscription) throws Exception {
        log.info("IClocker Subscription Migration Job in Progress");
        CustomerSubscription converted = new CustomerSubscription();
        CustomerSubscription existingRecord = customerSubscriptionRepository.findByOrgId(subscription.getOrgId());
        if (existingRecord != null) {
            setFieldsOnConverted(existingRecord, subscription);
            converted = existingRecord;
        } else {
            setFieldsOnConverted(converted, subscription);
        }
        customerSubscriptionRepository.save(converted);
        return converted;
    }

    private void setFieldsOnConverted(CustomerSubscription converted, Subscription subscription) {
        converted.setActivatedBy(subscription.getActivatedBy());
        converted.setActive(subscription.isActive());
        converted.setAutoRenew(subscription.isAutoRenew());
        converted.setAmountPaid(subscription.getAmountPaid());
        converted.setTotalAmount(subscription.getTotalAmount());
        converted.setActivatedBy(isNullOrEmpty(subscription.getActivatedBy()) ? "" : subscription.getActivatedBy());
        converted.setCurrency(isNullOrEmpty(subscription.getCurrency()) ? "" : subscription.getCurrency());
        converted.setOrgId(isNullOrEmpty(subscription.getOrgId()) ? "" : subscription.getOrgId());
        if (subscription.getBillingCycle() != null) {
            converted.setBillingCycle(subscription.getBillingCycle());
        }
        if (subscription.getPaymentMode() != null) {
            converted.setPaymentMode(subscription.getPaymentMode());
        }
        if (subscription.getSubscriptionMode() != null) {
            converted.setSubscriptionMode(subscription.getSubscriptionMode());
        }
        if (subscription.getSubscriptionStatus() != null) {
            converted.setSubscriptionStatus(subscription.getSubscriptionStatus());
        }
        if (subscription.getStartDate() != null) {
            converted.setStartDate(new Date(subscription.getStartDate()));
        }
        if (subscription.getEndDate() != null) {
            converted.setEndDate(new Date(subscription.getEndDate()));
        }
        if (subscription.getCreated() != null) {
            converted.setCreateDate(new Date(subscription.getCreated()));
        }
        converted.setSubscriptionPlanId(isNullOrEmpty(subscription.getSubscriptionPlanId()) ? "" : subscription.getSubscriptionPlanId());
    }
}
