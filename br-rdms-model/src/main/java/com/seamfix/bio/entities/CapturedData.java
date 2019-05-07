/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Entity
@Table(name = "CAPTURED_DATA", indexes = {
    @Index(name = "captured_data_Index", columnList = "id,pid,uniqueId,euniqueId")})
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class CapturedData extends BaseEntity {

    private String pId;

    private String ppId;

    private String cId;

    private Long captured;

    @Column(unique = true)
    private String uniqueId;

    private String puniqueId;

    private String euniqueId;

    private String tag;

    private String createdBy;

    private Boolean verified;

    private String preId;

    private double cost;

    private String machineId;

    private String machineSpecs;

    private String longitude;

    private String latitude;

    private String location;

    private String customId;

    private String flag;

    private String useCase;

    private String prevId;

    private String sField;

    private String captureAddr;

    @Column(name = "MODIFIED", nullable = true, insertable = true, updatable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modified = new Date();

    private Double appVersion;

    private String reason;

    private boolean pushStatus;

    private String pushRef;

    private String geofenceStatus;

    private String geoFenceId;

    @OneToMany(mappedBy = "capturedDataId")
    private List<CapturedDataDemoGraphics> demoGraphics = new ArrayList<>();

}
