package com.seamfix.bio.entities;

import com.sf.biocloud.entity.enums.ClockInLocationStatus;
import com.sf.biocloud.entity.enums.ClockInMode;
import com.sf.biocloud.entity.enums.PunctualityType;
import com.sf.biocloud.entity.enums.SecondLevelAuthMode;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Table(name = "AUTH_AUDIT")
public class AuthAudit extends BaseEntity{

        private String email;

        private String deviceId;

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

        private String locId;

        private String orgId;

        private String jobId;

        private String status;

        @Column(name = "provider_job_id")
        private String providerJobId;

        private boolean reconciled;
}
