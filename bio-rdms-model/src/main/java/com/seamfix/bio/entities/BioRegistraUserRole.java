/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.entities;

import com.seamfix.bio.enums.RoleCategory;
import com.sf.bioregistra.entity.enums.OrgRole;
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
@Table(name = "BIO_REGISTRA_USER_ROLE")
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@IdClass(BioRegistraUserRoleId.class)
public class BioRegistraUserRole extends EmbeddedBaseEntity {

    @Id
    @SequenceGenerator(name = "bio_registra_user_role_seq", sequenceName = "bio_registra_user_role_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "bio_registra_user_role_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Id

    @Column(name = "new_user_id")
    private long newUserId;
    @Id
    @Column(name = "new_project_id")
    private long newProjectId;
    @Id
    @Column(name = "new_org_id")
    private long newOrgId;
    @Id
    @Enumerated(EnumType.STRING)
    private OrgRole newUserRole;
    @Id
    @Enumerated(EnumType.STRING)
    private RoleCategory newRoleCategory;

    private String userId;

    private String projectId;

    private String orgId;

    private String userRole;

    private String roleCategory;

}
