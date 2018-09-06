/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.entities;

import com.sf.biocloud.entity.enums.BioCloudUserRole;
import com.sf.biocloud.entity.enums.InvitationStatus;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Entity
@Table(name = "USER_INVITATIONS")
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@IdClass(UserInvitationId.class)
public class UserInvitation extends EmbeddedBaseEntity {

    @Id
    @SequenceGenerator(name = "user_invitation_seq", sequenceName = "user_invitation_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_invitation_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Id
    private String inviteId;
    @Id
    private String email;
    @Id
    @Column(name = "new_loc_id")
    private long newLocId;
    @Id
    @Column(name = "new_org_id")
    private long newOrgId;

    private String locId;

    private String orgId;
    @Enumerated(EnumType.STRING)
    private BioCloudUserRole role;
    @Enumerated(EnumType.STRING)
    private InvitationStatus status = InvitationStatus.SENT;

}
