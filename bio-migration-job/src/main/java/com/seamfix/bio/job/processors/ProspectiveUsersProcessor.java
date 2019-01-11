package com.seamfix.bio.job.processors;

import com.seamfix.bio.job.jpa.dao.ProspectiveUsersRepository;
import com.sf.biocloud.entity.ProspectiveUsers;
import org.springframework.batch.item.ItemProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class ProspectiveUsersProcessor implements ItemProcessor<ProspectiveUsers, com.seamfix.bio.entities.ProspectiveUsers> {

    private static final Logger logg = LoggerFactory.getLogger(EmployeeAttendanceLogProcessor.class);

    private final ProspectiveUsersRepository prospectiveUsersRepository;

    public ProspectiveUsersProcessor(ProspectiveUsersRepository prospectiveUsersRepository) {
        this.prospectiveUsersRepository = prospectiveUsersRepository;
    }

    @Override
    public com.seamfix.bio.entities.ProspectiveUsers process(ProspectiveUsers log) throws Exception {

        logg.info("Prospective users migration job is in progress!");

        com.seamfix.bio.entities.ProspectiveUsers existing = prospectiveUsersRepository.findByEmail(log.getEmail());


        if(existing == null) {
            com.seamfix.bio.entities.ProspectiveUsers converted = new com.seamfix.bio.entities.ProspectiveUsers();


            if(log.getEmail() != null) {
                converted.setEmail(log.getEmail());
            }
            converted.setExpTime(log.getExpTime());
            converted.setToken(log.getToken());
            converted.setActive(log.isActive());
            converted.setCreateDate(new Date(log.getCreated()));

            prospectiveUsersRepository.save(converted);
            return converted;
        } else {
            existing.setActive(log.isActive());
            prospectiveUsersRepository.updateActiveStatus(log.isActive(), log.getEmail());
            return existing;
        }

//        return null;
    }
}
