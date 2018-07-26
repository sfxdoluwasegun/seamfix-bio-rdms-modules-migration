/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.entities;

import com.sf.biocloud.entity.enums.BioCloudUserRole;
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
public class IclockerUserRoleId implements Serializable {
    
    private Long id;

    private long newUserId;

    private long newLocId;

    private long newOrgId;

    private BioCloudUserRole iclockerUserRole;

    public IclockerUserRoleId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IclockerUserRoleId)) {
            return false;
        }
        IclockerUserRoleId that = (IclockerUserRoleId) o;
        return Objects.equals(getNewUserId(), that.getNewUserId()) && Objects.equals(getNewOrgId(), that.getNewOrgId()) && Objects.equals(getNewLocId(), that.getNewLocId())
                && Objects.equals(getIclockerUserRole(), that.getIclockerUserRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNewUserId(), getNewOrgId(), getNewLocId(), getIclockerUserRole());
    }

}
