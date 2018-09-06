package com.seamfix.bio.job.processors;

import com.seamfix.bio.entities.OrgType;
import com.seamfix.bio.entities.Organisation;
import com.seamfix.bio.job.jpa.dao.OrgTypeRepository;
import com.seamfix.bio.proxy.Organization;
import com.sf.bioregistra.entity.EmployeeRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import java.util.Date;

public class OrgProcessor implements ItemProcessor<Organization, Organisation> {

    private final OrgTypeRepository orgTypeRepository;

    public OrgProcessor(OrgTypeRepository orgTypeRepository) {
        this.orgTypeRepository = orgTypeRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(OrgProcessor.class);

    @Override
    public Organisation process(Organization org) throws Exception {
         log.info("IClocker Organisation migration job is in progress!");
        Organisation converted = new Organisation();
        converted.setOrgId(org.getOrgId());
        if (org.getOrgType() != null && !org.getOrgType().trim().isEmpty()) {
            OrgType orgtype = orgTypeRepository.findByName(org.getOrgType());
            if (orgtype != null) {
                converted.setOrgType(orgtype);
            }

        }
        converted.setCreatedBy(org.getCreatedBy() == null || org.getCreatedBy().trim().isEmpty() ? "" : org.getCreatedBy());
        converted.setLogo(org.getLogo() == null || org.getLogo().trim().isEmpty() ? "" : org.getLogo());
        converted.setName(org.getName() == null || org.getName().trim().isEmpty() ? "" : org.getName());
        converted.setOrgCode(org.getOrgCode() == null || org.getOrgCode().trim().isEmpty() ? "" : org.getOrgCode());
        converted.setWalletId(org.getWalletId() == null || org.getWalletId().trim().isEmpty() ? "" : org.getWalletId());
        if (org.getPaymentPlan() != null) {
            converted.setPaymentPlan(org.getPaymentPlan());
        }
        if (org.getProductName() != null) {
            converted.setProductName(org.getProductName());
        }
        if (org.getCreated() != null) {
            converted.setCreateDate(new Date(org.getCreated()));
        }

        EmployeeRange employeeRange = org.getEmployeeRange();
        if (employeeRange != null) {
            converted.setMinEmployeeSize(employeeRange.getLowerLimit());
            converted.setMaxEmployeeSize(employeeRange.getUpperLimit());
        }
        return converted;
    }

}
