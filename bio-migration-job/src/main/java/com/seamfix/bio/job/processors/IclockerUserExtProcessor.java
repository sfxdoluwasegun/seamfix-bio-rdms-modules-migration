package com.seamfix.bio.job.processors;

import com.seamfix.bio.entities.AppUser;
import com.sf.biocloud.entity.BioCloudUserExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import com.seamfix.bio.entities.IclockerUserExt;
import com.seamfix.bio.job.jpa.dao.UserRepository;
import java.util.Date;
import com.seamfix.bio.job.mongodb.dao.IclockerUserExtMongoRepository;

public class IclockerUserExtProcessor implements ItemProcessor<BioCloudUserExt, IclockerUserExt> {

    private static final Logger log = LoggerFactory.getLogger(IclockerUserExtProcessor.class);
    private final UserRepository userRepository;
    private final IclockerUserExtMongoRepository mongodbIclockerUserExtRepository;
    private final com.seamfix.bio.job.jpa.dao.IclockerUserExtRepository iclockerUserExtRepository;

    public IclockerUserExtProcessor(UserRepository userRepository, IclockerUserExtMongoRepository mongodbIclockerUserExtRepository, com.seamfix.bio.job.jpa.dao.IclockerUserExtRepository iclockerUserExtRepository) {
        this.userRepository = userRepository;
        this.mongodbIclockerUserExtRepository = mongodbIclockerUserExtRepository;
        this.iclockerUserExtRepository = iclockerUserExtRepository;
    }

    @Override
    public IclockerUserExt process(BioCloudUserExt userExt) throws Exception {
        log.info("IClocker user extension migration job is in progress!");
        IclockerUserExt converted = null;


        if (userExt.getUserId() != null && !userExt.getUserId().trim().isEmpty()) {
            AppUser dbUser = userRepository.findByUserId(userExt.getUserId());
            converted = iclockerUserExtRepository.findByUserId(userExt.getUserId());

            if(converted == null) {
                converted = new IclockerUserExt();
            }

            converted.setUserId(userExt.getUserId());
            converted.setEnrolled(userExt.isEnrolled());
            if (userExt.getEnrollTime() != null) {
                converted.setEnrolledTime(new Date(userExt.getEnrollTime()));
            }
            converted.setUserServiceProviderId(userExt.getSmileId() == null || userExt.getSmileId().trim().isEmpty() ? "" : userExt.getSmileId());
            if (dbUser != null) {
                converted.setUid(dbUser);
            }
            converted.setBio(userExt.getBio() == null || !userExt.getBio().trim().isEmpty() ? "" : userExt.getBio());
            converted.setActive(userExt.isActive());
            if (userExt.getCreated() != null) {
                converted.setCreateDate(new Date(userExt.getCreated()));
            }
            iclockerUserExtRepository.save(converted);

        }

        return converted;
    }
}