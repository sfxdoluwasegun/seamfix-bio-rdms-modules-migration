/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.entities;

import com.sf.biocloud.entity.enums.ClockInLocationStatus;
import com.sf.biocloud.entity.enums.ClockInMode;
import com.sf.biocloud.entity.enums.PunctualityType;
import com.sf.biocloud.entity.enums.SecondLevelAuthMode;
import java.time.ZonedDateTime;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "EMPLOYEE_ATTENDANCE_LOG")
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class EmployeeAttendanceLog extends BaseEntity {

    private String email;

    private String deviceId;

    @Column(unique = true)
    private String oldId;

    private double longitude;

    private double matchConfidenceLevel;

    private double latitude;

    @Enumerated(EnumType.STRING)
    private SecondLevelAuthMode authMode;

    @Enumerated(EnumType.STRING)
    private ClockInMode clockInMode;

    @Enumerated(EnumType.STRING)
    private ClockInLocationStatus clockInLocationStatus;

    @Enumerated(EnumType.STRING)
    private PunctualityType punctualityType;

    private String clockInAddress;

    private boolean match;

    @Column(name = "CLOCK_IN_TIME", nullable = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime time;

    private String actionType;

    private String description;

    @ManyToOne(optional = true)
    @JoinColumn(nullable = true, name = "location_id")
    private Location location;

    @ManyToOne(optional = true)
    @JoinColumn(nullable = true, name = "ORGANISATION_ID")
    private Organisation organisation;

    private String locId;

    private String orgId;

    private String jobId;

    private String status;

}
