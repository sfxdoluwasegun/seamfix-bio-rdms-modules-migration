package com.seamfix.bio.job.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import com.seamfix.bio.entities.AppUser;
import com.seamfix.bio.jpa.dao.UserRepository;
import com.sf.bioregistra.entity.BioUser;
import java.util.Date;

public class BioUserProcessor implements ItemProcessor<BioUser, AppUser> {

    private static final Logger log = LoggerFactory.getLogger(BioUserProcessor.class);
    private final UserRepository userRepository;

    public BioUserProcessor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AppUser process(BioUser user) throws Exception {
          log.info("BioRegistra Users migration job is in progress!");
        AppUser converted = new AppUser();
        converted.setAddress(user.getAddress() == null || user.getAddress().trim().isEmpty() ? "N/A" : user.getAddress());
        converted.setAppNotif(user.getAppNotif() == null ? Boolean.FALSE : user.getAppNotif());
        if (user.getClientFirstLogin() != null) {
            converted.setClientFirstLogin(new Date(user.getClientFirstLogin()));
        }
        converted.setCompanyName(user.getCompanyName() == null || user.getCompanyName().trim().isEmpty() ? "N/A" : user.getCompanyName());
        if (user.getCreated() != null) {
            converted.setCreateDate(new Date(user.getCreated()));
        }
        converted.setCreatedBy(user.getCreator() == null || user.getCreator().trim().isEmpty() ? "N/A" : user.getCreator());
        converted.setCreator(user.getCreator() == null || user.getCreator().trim().isEmpty() ? "N/A" : user.getCreator());
        converted.setEmail(user.getEmail() == null || user.getEmail().trim().isEmpty() ? "N/A" : user.getEmail());
        converted.setFailedLoginAttempts(user.getFailedLoginAttempts() == null ? 0 : user.getFailedLoginAttempts());
        converted.setFirstName(user.getfName() == null || user.getfName().trim().isEmpty() ? "N/A" : user.getfName());
        converted.setLastName(user.getlName() == null || user.getlName().trim().isEmpty() ? "N/A" : user.getlName());
        converted.setMiddleName(user.getmName() == null || user.getmName().trim().isEmpty() ? "N/A" : user.getmName());
        converted.setGender(user.getGender() == null || user.getGender().trim().isEmpty() ? "N/A" : user.getGender());
        if (user.getLastFailedLogin() != null) {
            converted.setCreateDate(new Date(user.getLastFailedLogin()));
        }
        if (user.getLastPasswordChange() != null) {
            converted.setLastPasswordChange(new Date(user.getLastPasswordChange()));
        }
        if (user.getLastSuccessfulLogin() != null) {
            converted.setLastSuccessfulLogin(new Date(user.getLastSuccessfulLogin()));
        }
        converted.setPasswordReset(user.isPasswordReset());
        converted.setPhone(user.getPhone() == null || user.getPhone().trim().isEmpty() ? "N/A" : user.getPhone());
        converted.setPhoneCode(user.getPhoneCode() == null || user.getPhoneCode().trim().isEmpty() ? "N/A" : user.getPhoneCode());
        converted.setPromoNotif(user.getPromoNotif() == null ? Boolean.FALSE : user.getPromoNotif());
        converted.setPw(user.getPw() == null || user.getPw().trim().isEmpty() ? "N/A" : user.getPw());
        converted.setUserId(user.getUserId() == null || user.getUserId().trim().isEmpty() ? "N/A" : user.getUserId());
        converted.setUserType(user.getUserType() == null || user.getUserType().trim().isEmpty() ? "N/A" : user.getUserType());
        userRepository.save(converted);
        return converted;
    }
}
