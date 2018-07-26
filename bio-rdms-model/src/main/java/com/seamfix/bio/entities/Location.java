/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.entities;

import com.sf.biocloud.entity.enums.LocationType;
import java.time.ZonedDateTime;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Entity
@Table(name = "Locations", indexes = {
    @Index(name = "locIndex", columnList = "id,locId,name")})
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Location extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String locId;

    private Double longitude;

    private Integer radiusThreshold;

    private Double latitude;

    private String orgId;
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, name = "new_org_id")
    private Organisation newOrgId;

    private String name;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, name = "country")
    private CountryObj country;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, name = "state")
    private CountryStateObj state;

    private String address;

    private int gracePeriodInMinutes;
    @Enumerated(EnumType.STRING)
    private LocationType locationType;

    @Column(name = "RESUMPTION", nullable = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime resumption;

    private String resumptionTimezoneId;

    @Column(name = "CLOCK_OUT_TIME", nullable = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime clockOutTime;

}
