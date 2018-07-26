package com.seamfix.bio.job.processors;

import com.seamfix.bio.entities.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import com.seamfix.bio.entities.IclockerUserExt;
import com.seamfix.bio.jpa.dao.UserRepository;
import com.seamfix.bio.extended.mongodb.entities.BioCloudUserExt;
import com.sf.bioregistra.entity.BioUser;
import java.util.Date;
import com.seamfix.bio.mongodb.dao.IclockerUserExtMongoRepository;

public class IclockerUserExtProcessor implements ItemProcessor<BioUser, IclockerUserExt> {

    private static final Logger log = LoggerFactory.getLogger(IclockerUserExtProcessor.class);
    private final UserRepository userRepository;
    private final IclockerUserExtMongoRepository mongodbIclockerUserExtRepository;
    private final com.seamfix.bio.jpa.dao.IclockerUserExtRepository iclockerUserExtRepository;

    public IclockerUserExtProcessor(UserRepository userRepository, IclockerUserExtMongoRepository mongodbIclockerUserExtRepository, com.seamfix.bio.jpa.dao.IclockerUserExtRepository iclockerUserExtRepository) {
        this.userRepository = userRepository;
        this.mongodbIclockerUserExtRepository = mongodbIclockerUserExtRepository;
        this.iclockerUserExtRepository = iclockerUserExtRepository;
    }

    @Override
    public IclockerUserExt process(BioUser user) throws Exception {
        log.info("IClocker user extension migration job is in progress!");
        IclockerUserExt converted = new IclockerUserExt();
        if (user.getUserId() != null && !user.getUserId().trim().isEmpty()) {
            AppUser dbUser = userRepository.findByUserId(user.getUserId());
            BioCloudUserExt userExt = mongodbIclockerUserExtRepository.findByUserIdQuery(user.getUserId());
            if (userExt != null) {
                converted.setUserId(user.getUserId());
                converted.setEnrolled(userExt.isEnrolled());
                converted.setUserServiceProviderId(userExt.getSmileId() == null || !userExt.getSmileId().trim().isEmpty() ? "" : userExt.getSmileId());
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

        }

        return converted;
    }
}
