package com.seamfix.bio.job.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import com.sf.bioregistra.entity.subscription.SubscriptionType;

public class SubscriptionTypeProcessor implements ItemProcessor<SubscriptionType, com.seamfix.bio.entities.SubscriptionType> {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionTypeProcessor.class);

    @Override
    public com.seamfix.bio.entities.SubscriptionType process(SubscriptionType subscripType) throws Exception {
        log.info("BioRegistra Subscription Types migration job is in progress!");
        com.seamfix.bio.entities.SubscriptionType converted = new com.seamfix.bio.entities.SubscriptionType();
        if (subscripType.getCode() != null) {
            converted.setCode(subscripType.getCode());
        }
        converted.setName(subscripType.getName() == null ? "" : subscripType.getName());
        converted.setOldId(subscripType.getId().toHexString());
        converted.setDefaultSubscription(subscripType.isDefaultSubscription());
        converted.setDescription(subscripType.getDescription() == null ? "" : subscripType.getDescription());
        return converted;
    }

}
