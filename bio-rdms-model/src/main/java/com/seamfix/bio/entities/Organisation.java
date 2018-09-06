/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.entities;

import com.sf.bioregistra.entity.enums.PaymentPlan;
import com.sf.bioregistra.entity.enums.ProductName;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Entity
@Table(name = "ORGANISATION", indexes = {
    @Index(name = "orgIndex", columnList = "id,orgId,name")})
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Organisation extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String orgId;
    
   // @Column(unique = true)
    private String name;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, name = "org_type")
    private OrgType orgType;

    @Column(name="min_employees")
    private long minEmployeeSize;

    @Column(name="max_employees")
    private long maxEmployeeSize;

    private String walletId;
    @Enumerated(EnumType.STRING)
    private ProductName productName;
    @Column(columnDefinition = "TEXT")
    private String logo;
    private String createdBy;
    private String orgCode;
    @Enumerated(EnumType.STRING)
    private PaymentPlan paymentPlan;

    @OneToMany(mappedBy = "newOrgId")
    private List<Project> projects = new ArrayList<>();

}
