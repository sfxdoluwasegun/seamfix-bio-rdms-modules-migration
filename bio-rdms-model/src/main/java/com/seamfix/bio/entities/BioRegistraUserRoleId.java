/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.entities;

import com.seamfix.bio.enums.RoleCategory;
import com.sf.bioregistra.entity.enums.OrgRole;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.Data;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Embeddable
@Data
public class BioRegistraUserRoleId implements Serializable {

    private Long id;

    private Long newUserId;

    private long newProjectId;

    private long newOrgId;

    private OrgRole newUserRole;

    private RoleCategory newRoleCategory;

    public BioRegistraUserRoleId(long newUserId, long newProjectId, long newOrgId, OrgRole newUserRole, RoleCategory newRoleCategory) {
        this.newUserId = newUserId;
        this.newProjectId = newProjectId;
        this.newOrgId = newOrgId;
        this.newUserRole = newUserRole;
        this.newRoleCategory = newRoleCategory;
    }

    public BioRegistraUserRoleId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BioRegistraUserRoleId)) {
            return false;
        }
        BioRegistraUserRoleId that = (BioRegistraUserRoleId) o;
        return Objects.equals(getNewUserId(), that.getNewUserId()) && Objects.equals(getNewOrgId(), that.getNewOrgId()) && Objects.equals(getNewProjectId(), that.getNewProjectId())
                && Objects.equals(getNewUserRole(), that.getNewUserRole()) && Objects.equals(getNewRoleCategory(), that.getNewRoleCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNewUserId(), getNewProjectId(), getNewOrgId(), getNewUserRole(), getNewRoleCategory());
    }

}
