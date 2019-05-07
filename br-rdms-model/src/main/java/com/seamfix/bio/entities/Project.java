/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.entities;

import java.util.Date;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Entity
@Table(name = "PROJECTS", indexes = {
    @Index(name = "projectIndex", columnList = "id,pid,ppid,name")})
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Project extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String pid;
    private String ppId;
    private String name;
    private String type;
    private String orgId;
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, name = "new_org_id")
    private Organisation newOrgId;
    @Column(columnDefinition = "TEXT")
    private String logo;
    private String creatorEmail;
    private Long captureEstimate;
    private String useCases;
    private String qParams;
    private String cycle;
    private Boolean webEnrollment;
    private Boolean remoteDataWareHouse;
    @Column(name = "START_DATE", nullable = true, insertable = true, updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "END_DATE", nullable = true, insertable = true, updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    private Boolean useId;
    private Double version;
    private double cost;
    private String host;
    private int port;
    private Double sVersion;
    private String description;
    private String category;
    private long textSyncCount;
    private long fingerPrintSyncCount;
    private long passportSyncCount;
    private Boolean firstSyncCountStatus;
    private String locSetting;
    private Boolean enablePreviewFullDetails;
    private Boolean enablePreviewCapture;
    private Boolean enableLoc;
    private Boolean valRequired;
    private Boolean enableBvn;
    private String bvnFields;
    private String bvnMode;
    private Boolean skipBvnVal;
    private Boolean offlineVal;
    private long bvnCount;
    private Boolean allowOcr;
    private Boolean allowMap;
    private Boolean allowPrintIdCard;
    private long syncCount;
    private Boolean GeoFenceEnabled;
}
