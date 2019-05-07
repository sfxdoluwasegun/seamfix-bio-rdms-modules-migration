package com.seamfix.bio.job.processors;

import com.seamfix.bio.entities.Organisation;
import com.seamfix.bio.entities.SubscriptionType;
import com.seamfix.bio.extended.mongodb.entities.Organization;
import com.seamfix.bio.job.jpa.dao.SubscriptionTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import java.util.Date;

public class OrgProcessor implements ItemProcessor<Organization, Organisation> {

    private static final Logger log = LoggerFactory.getLogger(OrgProcessor.class);

    private final SubscriptionTypeRepository subscriptionTypeRepository;

    public OrgProcessor(SubscriptionTypeRepository subscriptionTypeRepository) {
        this.subscriptionTypeRepository = subscriptionTypeRepository;
    }

    @Override
    public Organisation process(Organization org) throws Exception {
        log.info("BioRegistra Organisation migration job is in progress!");
        Organisation converted = new Organisation();
        converted.setOrgId(org.getOrgId());

        converted.setCreatedBy(org.getCreatedBy() == null || org.getCreatedBy().trim().isEmpty() ? "" : org.getCreatedBy());

        converted.setName(org.getName() == null || org.getName().trim().isEmpty() ? "" : org.getName());
        converted.setWalletId(org.getWalletId() == null || org.getWalletId().trim().isEmpty() ? "" : org.getWalletId());

        if (org.getProductName() != null) {
            converted.setProductName(org.getProductName());
        }
        if (org.getCreated() != null) {
            converted.setCreateDate(new Date(org.getCreated()));
        }

        if (org.getSubscriptionStatus() != null) {
            converted.setSubscriptionStatus(org.getSubscriptionStatus());
        }

        if (org.getSubscriptionType() != null) {
            converted.setOldSubscriptionTypeId(org.getSubscriptionType().toHexString());
            SubscriptionType st = subscriptionTypeRepository.findByOldId(org.getSubscriptionType().toHexString());
            if (st != null) {
                converted.setNewSubscripTypeId(st);
            }
        }

        return converted;
    }

}
