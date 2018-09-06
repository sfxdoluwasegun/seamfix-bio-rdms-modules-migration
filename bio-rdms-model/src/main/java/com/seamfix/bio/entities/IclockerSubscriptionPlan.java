package com.seamfix.bio.entities;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Cacheable
@Table(name = "subscription_plan")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IclockerSubscriptionPlan extends BaseEntity {

    private String name;

    @Column(unique = true, name = "plan_id")
    private String planId;

    private String description;

    private double discount;

    @Column(name = "price_per_month")
    private double pricePerMonth;

    @Column(name = "price_per_annum")
    private double pricePerAnnum;

    @Column(name="max_attendee_threshold")
    private long maxAttendeeThreshold;
}
