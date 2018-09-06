/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.entities;

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
public class UserInvitationId implements Serializable {

    private Long id;

    private String email;

    private String inviteId;

    private long newLocId;

    private long newOrgId;

    public UserInvitationId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserInvitationId)) {
            return false;
        }
        UserInvitationId that = (UserInvitationId) o;
        return Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getInviteId(), that.getInviteId()) && Objects.equals(getNewOrgId(), that.getNewOrgId()) && Objects.equals(getNewLocId(), that.getNewLocId());

    }

    @Override
    public int hashCode() {
        return Objects.hash(getInviteId(), getEmail(), getNewOrgId(), getNewLocId());
    }

}
