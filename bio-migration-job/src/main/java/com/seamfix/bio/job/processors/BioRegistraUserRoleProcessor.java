package com.seamfix.bio.job.processors;

import com.seamfix.bio.entities.AppUser;
import com.seamfix.bio.entities.BioRegistraUserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import com.seamfix.bio.entities.Organisation;
import com.seamfix.bio.entities.Project;
import com.seamfix.bio.enums.RoleCategory;
import com.seamfix.bio.jpa.dao.BioRegistraUserRoleRepository;
import com.seamfix.bio.jpa.dao.OrganisationRepository;
import com.seamfix.bio.jpa.dao.ProjectRepository;
import com.seamfix.bio.jpa.dao.UserRepository;
import com.sf.bioregistra.entity.BioUser;
import com.sf.bioregistra.entity.ProjectRole;
import com.sf.bioregistra.entity.enums.OrgRole;
import java.util.ArrayList;

public class BioRegistraUserRoleProcessor implements ItemProcessor<BioUser, BioRegistraUserRole> {

    private static final Logger log = LoggerFactory.getLogger(BioRegistraUserRoleProcessor.class);
    private final UserRepository userRepository;
    private final BioRegistraUserRoleRepository bioRegistraUserRoleRepository;
    private final ProjectRepository projectRepository;
    private final OrganisationRepository organisationRepository;

    public BioRegistraUserRoleProcessor(UserRepository userRepository, BioRegistraUserRoleRepository bioRegistraUserRoleRepository, ProjectRepository projectRepository, OrganisationRepository organisationRepository) {
        this.userRepository = userRepository;
        this.bioRegistraUserRoleRepository = bioRegistraUserRoleRepository;
        this.projectRepository = projectRepository;
        this.organisationRepository = organisationRepository;
    }

    @Override
    public BioRegistraUserRole process(BioUser user) throws Exception {
         log.info("BioRegistra User roles migration job is in progress!");
        BioRegistraUserRole converted = null;
        String userId = user.getUserId();
        if (userId != null && !userId.trim().isEmpty()) {
            AppUser dbUser = userRepository.findByUserId(user.getUserId());
            if (user.getRole() != null && !user.getRole().isEmpty() && dbUser != null) {
                converted = new BioRegistraUserRole();
                converted.setOrgId(user.getOrgId() == null || user.getOrgId().trim().isEmpty() ? "" : user.getOrgId());
                converted.setProjectId("");
                converted.setRoleCategory(RoleCategory.ORG.getName());
                converted.setUserRole(user.getRole());
                converted.setUserId(userId);
                long orgId = 0;
                Organisation org = organisationRepository.findByOrgId(user.getOrgId());
                if (org != null) {
                    orgId = org.getId();
                }

                converted.setNewUserId(dbUser.getId());
                converted.setNewProjectId(0);
                converted.setNewOrgId(orgId);
                converted.setNewUserRole(OrgRole.from(user.getRole()));
                converted.setNewRoleCategory(RoleCategory.ORG);
                bioRegistraUserRoleRepository.save(converted);

                ArrayList<ProjectRole> projectRoles = user.getProjectRoles();
                if (projectRoles != null && !projectRoles.isEmpty() && dbUser != null) {
                    for (ProjectRole role : projectRoles) {
                        if (role.getpId() != null || !role.getpId().trim().isEmpty()) {
                            Project project = projectRepository.findByPid(role.getpId());
                            if (project != null) {
                                converted = new BioRegistraUserRole();
                                converted.setUserId(userId);
                                converted.setOrgId(user.getOrgId() == null || user.getOrgId().trim().isEmpty() ? "" : user.getOrgId());
                                converted.setRoleCategory(RoleCategory.PROJECT.getName());
                                converted.setUserRole(role.getRole());
                                converted.setProjectId(role.getpId());
                                converted.setNewUserId(dbUser.getId());
                                converted.setNewProjectId(project.getId());
                                converted.setNewOrgId(orgId);
                                converted.setNewUserRole(OrgRole.from(user.getRole()));
                                converted.setNewRoleCategory(RoleCategory.PROJECT);
                                //converted.setCompositeId(nid);
                                bioRegistraUserRoleRepository.save(converted);
                            }
                        }
                    }

                }

            }
        }

        return converted;
    }

}
