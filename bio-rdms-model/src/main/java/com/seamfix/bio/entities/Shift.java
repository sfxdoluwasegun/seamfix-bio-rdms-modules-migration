package com.seamfix.bio.entities;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;
import java.time.DayOfWeek;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "SHIFT", indexes = {
        @Index(name = "shiftIdex", columnList = "shiftId,locId,orgId")})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Shift extends BaseEntity {

    private String shiftId;

    private String name;

    private String locId;

    private String orgId;

    @Column(name = "resumption")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime resumption;

    @Column(name = "grace_period_in_min")
    private int gracePeriodInMinutes;

    @Column(name = "clock_out_time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime clockOutTime;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "break_time_start")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime breakTimeStart;

    @Column(name = "break_time_end")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime breakTimeEnd;

    @Column(name = "working_days")
    private String workingDays;

}
