/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.entities;

import com.sf.biocloud.entity.enums.BioCloudUserRole;
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
@Table(name = "ICLOCKER_USER_ROLE")
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@IdClass(IclockerUserRoleId.class)
public class IclockerUserRole extends EmbeddedBaseEntity {

    @Id
    @SequenceGenerator(name = "iclocker_user_role_seq", sequenceName = "iclocker_user_role_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "iclocker_user_role_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Id
    @Column(name = "new_user_id")
    private long newUserId;
    @Id
    @Column(name = "new_loc_id")
    private long newLocId;
    @Id
    @Column(name = "new_org_id")
    private long newOrgId;
    @Id
    @Enumerated(EnumType.STRING)
    private BioCloudUserRole iclockerUserRole;

    private String userId;

    private String locId;

    private String orgId;

}
