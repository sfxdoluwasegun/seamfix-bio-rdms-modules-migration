package com.seamfix.bio.entities;

import lombok.Data;
import java.util.List;
import java.time.DayOfWeek;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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

    private Long resumption;

    @Column(name = "grace_period_in_min")
    private int gracePeriodInMinutes;

    @Column(name = "clock_out_time")
    private Long clockOutTime;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "break_time_start")
    private Long breakTimeStart;

    @Column(name = "break_time_end")
    private Long breakTimeEnd;

    @Column(name = "working_days")
    private List<DayOfWeek> workingDays;
}
