package com.seamfix.bio.job.processors;

import com.seamfix.bio.entities.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import com.seamfix.bio.entities.IclockerUserRole;
import com.seamfix.bio.entities.Location;
import com.seamfix.bio.entities.Organisation;
import com.seamfix.bio.extended.mongodb.entities.BioCloudUserExt;
import com.seamfix.bio.job.jpa.dao.IclockerUserRoleRepository;
import com.seamfix.bio.job.jpa.dao.LocationRepository;
import com.seamfix.bio.job.jpa.dao.OrganisationRepository;
import com.seamfix.bio.job.jpa.dao.UserRepository;
import com.sf.bioregistra.entity.BioUser;
import com.seamfix.bio.job.mongodb.dao.IclockerUserExtMongoRepository;
import com.sf.biocloud.entity.LocationRole;
import com.sf.biocloud.entity.enums.BioCloudUserRole;
import com.sf.bioregistra.entity.OrgRole;
import java.util.ArrayList;
import java.util.List;

public class IclockerUserRoleProcessor implements ItemProcessor<BioUser, IclockerUserRole> {

    private static final Logger log = LoggerFactory.getLogger(IclockerUserRoleProcessor.class);
    private final UserRepository userRepository;
    private final IclockerUserRoleRepository iclockerUserRoleRepository;
    private final LocationRepository locationRepository;
    private final OrganisationRepository organisationRepository;
    private final IclockerUserExtMongoRepository mongodbIclockerUserExtRepository;

    public IclockerUserRoleProcessor(UserRepository userRepository, IclockerUserRoleRepository iclockerUserRoleRepository, LocationRepository locationRepository, OrganisationRepository organisationRepository, IclockerUserExtMongoRepository mongodbIclockerUserExtRepository) {
        this.userRepository = userRepository;
        this.iclockerUserRoleRepository = iclockerUserRoleRepository;
        this.locationRepository = locationRepository;
        this.organisationRepository = organisationRepository;
        this.mongodbIclockerUserExtRepository = mongodbIclockerUserExtRepository;
    }

    @Override
    public IclockerUserRole process(BioUser user) throws Exception {
        log.info("IClocker user roles migration job is in progress!");
        IclockerUserRole converted = null;
        String userId = user.getUserId();
        List<Long> orgIds = new ArrayList<>();
        if (userId != null && !userId.trim().isEmpty()) {
            AppUser dbUser = userRepository.findByUserId(user.getUserId());
            ArrayList<OrgRole> orgRoles = user.getOrgRoles();
            if (orgRoles != null && !orgRoles.isEmpty() && dbUser != null) {
                for (OrgRole role : orgRoles) {
                    converted = new IclockerUserRole();
                    converted.setUserId(userId);
                    converted.setOrgId(role.getOrgId());
                    converted.setNewUserId(dbUser.getId());
                    converted.setNewOrgId(0);

                    Organisation org = organisationRepository.findByOrgId(role.getOrgId());
                    if (org != null) {
                        converted.setNewOrgId(org.getId());
                        orgIds.add(org.getId());
                    }
                    converted.setNewLocId(0);
                    converted.setIclockerUserRole(BioCloudUserRole.valueOf(role.getRole()));
                    if (dbUser != null && org != null) {
                        iclockerUserRoleRepository.save(converted);
                    }

                }

            } else  {

            }

            /////////////////
            BioCloudUserExt userExt = mongodbIclockerUserExtRepository.findByUserIdQuery(user.getUserId());
            if (userExt != null && userExt.getLocationRoles() != null && userExt.getLocationRoles().isEmpty()) {
                List<LocationRole> locRoles = userExt.getLocationRoles();
                for (LocationRole locRole : locRoles) {
                    Organisation dbOrg = organisationRepository.findByOrgId(locRole.getOrgId());
                    long orgid = 0;
                    if (dbOrg != null) {
                        orgid = dbOrg.getId();
                    }
                    if (!orgIds.contains(orgid)) {
                        Location dbLocation = locationRepository.findByLocId(locRole.getLocId());
                        if (dbLocation != null) {
                            converted = new IclockerUserRole();
                            converted.setUserId(userId);
                            converted.setOrgId(locRole.getOrgId());
                            converted.setLocId(locRole.getLocId());
                            converted.setActive(locRole.isActive());
                            converted.setIclockerUserRole(locRole.getRole());
                            converted.setNewLocId(dbLocation.getId());
                            converted.setNewOrgId(orgid);
                            converted.setNewUserId(dbUser.getId());
                             iclockerUserRoleRepository.save(converted);
                           
                        }

                    }

                }

            }

            //////////////////
        }

        return converted;
    }

}
