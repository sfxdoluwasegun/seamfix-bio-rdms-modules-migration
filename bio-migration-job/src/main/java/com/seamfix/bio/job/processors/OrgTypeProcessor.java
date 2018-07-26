package com.seamfix.bio.job.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import com.sf.bioregistra.entity.OrgType;

public class OrgTypeProcessor implements ItemProcessor<OrgType, com.seamfix.bio.entities.OrgType> {

    private static final Logger log = LoggerFactory.getLogger(OrgTypeProcessor.class);

    @Override
    public com.seamfix.bio.entities.OrgType process(OrgType orgType) throws Exception {
         log.info("IClocker Organisation Type migration job is in progress!");
        com.seamfix.bio.entities.OrgType converted = new com.seamfix.bio.entities.OrgType();
        converted.setName(orgType.getName());
        converted.setDescription("");
        return converted;
    }
}
