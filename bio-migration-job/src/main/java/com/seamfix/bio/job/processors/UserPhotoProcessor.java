package com.seamfix.bio.job.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import com.seamfix.bio.entities.AppUser;
import com.seamfix.bio.entities.UserPhoto;
import com.seamfix.bio.jpa.dao.UserPhotoRepository;
import com.seamfix.bio.jpa.dao.UserRepository;
import com.sf.bioregistra.entity.BioUser;

public class UserPhotoProcessor implements ItemProcessor<BioUser, UserPhoto> {

    private static final Logger log = LoggerFactory.getLogger(UserPhotoProcessor.class);

    private final UserRepository userRepository;

    private final UserPhotoRepository userPhotoRepository;

    public UserPhotoProcessor(UserRepository userRepository, UserPhotoRepository userPhotoRepository) {
        this.userRepository = userRepository;
        this.userPhotoRepository = userPhotoRepository;
    }

    @Override
    public UserPhoto process(BioUser user) throws Exception {
        log.info("IClocker and BioRegistra Photo migration job is in progress!");
        UserPhoto converted = new UserPhoto();
        boolean r1 = (user.getImg() != null && !user.getImg().trim().isEmpty());
        boolean r2 = (user.getVerifiableImg() != null && !user.getVerifiableImg().trim().isEmpty());
        if (user.getUserId() != null && !user.getUserId().trim().isEmpty()) {
            AppUser dbUser = userRepository.findByUserId(user.getUserId());
            if (dbUser != null) {
                if (r1 || r2) {
                    if (r1) {
                        converted.setProfilePhoto(user.getImg());
                    } else if (r2) {
                        converted.setVerifiablePhoto(user.getVerifiableImg());
                    }
                    converted.setUserId(user.getUserId());
                    converted.setNewUserId(dbUser);
                    userPhotoRepository.save(converted);

                }

            }
        }
        return converted;
    }
}
