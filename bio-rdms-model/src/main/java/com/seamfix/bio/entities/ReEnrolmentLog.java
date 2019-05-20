package com.seamfix.bio.entities;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "REENROLMENT_LOG")
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class ReEnrolmentLog extends BaseEntity{

    private String jobId;

    private String userId;

    private String email;

    @Column(name = "re_enrolment_time", insertable = true, updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date reEnrolmentTime;

}
