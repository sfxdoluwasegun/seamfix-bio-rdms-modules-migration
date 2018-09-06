package com.seamfix.bio.job.processors;

import com.seamfix.bio.entities.Location;
import com.seamfix.bio.entities.Organisation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import com.seamfix.bio.entities.UserInvitation;
import com.seamfix.bio.job.jpa.dao.LocationRepository;
import com.seamfix.bio.job.jpa.dao.OrganisationRepository;
import com.seamfix.bio.job.jpa.dao.UserInvitationRepository;
import com.sf.biocloud.entity.BioCloudUserInvite;
import java.util.Date;

public class UserInviteProcessor implements ItemProcessor<BioCloudUserInvite, UserInvitation> {

    private static final Logger log = LoggerFactory.getLogger(UserInviteProcessor.class);

    private final LocationRepository locationRepository;
    private final OrganisationRepository organisationRepository;
    private final UserInvitationRepository userInvitationRepository;

    public UserInviteProcessor(LocationRepository locationRepository, OrganisationRepository organisationRepository, UserInvitationRepository userInvitationRepository) {
        this.locationRepository = locationRepository;
        this.organisationRepository = organisationRepository;
        this.userInvitationRepository = userInvitationRepository;
    }

    @Override
    public UserInvitation process(BioCloudUserInvite invite) throws Exception {
        log.info("IClocker users invitations migration job is in progress!");
        UserInvitation converted = null;
        Organisation org = organisationRepository.findByOrgId(invite.getOrgId());
        long orgId = 0;
        Date d = new Date();
        if (org != null) {
            orgId = org.getId();
        }
        if (invite.getCreated() != null) {
            d = new Date(invite.getCreated());
        }
        if (invite.getLocIds() == null || invite.getLocIds().isEmpty()) {
            converted = new UserInvitation();
            converted.setEmail(invite.getEmail() == null || invite.getEmail().trim().isEmpty() ? "" : invite.getEmail());
            converted.setInviteId(invite.getInviteId() == null || invite.getInviteId().trim().isEmpty() ? "" : invite.getInviteId());
            converted.setLocId("");
            converted.setOrgId(invite.getOrgId() == null || invite.getOrgId().trim().isEmpty() ? "" : invite.getOrgId());
            converted.setRole(invite.getRole());
            converted.setStatus(invite.getStatus());
            converted.setNewLocId(0);

            converted.setCreateDate(d);

            converted.setNewOrgId(orgId);
            userInvitationRepository.save(converted);
        } else if (invite.getLocIds() != null || !invite.getLocIds().isEmpty()) {
            for (String locId : invite.getLocIds()) {
                long locaId = 0;
                Location loc = locationRepository.findByLocId(locId);
                if (loc != null) {
                    locaId = loc.getId();
                }

                converted = new UserInvitation();
                converted.setEmail(invite.getEmail() == null || invite.getEmail().trim().isEmpty() ? "" : invite.getEmail());
                converted.setInviteId(invite.getInviteId() == null || invite.getInviteId().trim().isEmpty() ? "" : invite.getInviteId());
                converted.setLocId(locId);
                converted.setOrgId(invite.getOrgId() == null || invite.getOrgId().trim().isEmpty() ? "" : invite.getOrgId());
                converted.setRole(invite.getRole());
                converted.setStatus(invite.getStatus());
                converted.setNewLocId(locaId);
                converted.setNewOrgId(orgId);
                converted.setCreateDate(d);
                userInvitationRepository.save(converted);
            }

        }

        return converted;
    }

}
