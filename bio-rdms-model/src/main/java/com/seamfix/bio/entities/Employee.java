/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.entities;

import com.sf.biocloud.entity.enums.AttendeeApprovalStatus;
import com.sf.biocloud.entity.enums.AttendeeStatus;
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
@Table(name = "EMPLOYEES")
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@IdClass(EmployeeId.class)
public class Employee extends EmbeddedBaseEntity {

    @Id
    @SequenceGenerator(name = "employee_seq", sequenceName = "employee_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "employee_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

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
    private AttendeeStatus attendeeStatus;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String gender;
    @Enumerated(EnumType.STRING)
    private AttendeeApprovalStatus approvalStatus = AttendeeApprovalStatus.PENDING;

}
