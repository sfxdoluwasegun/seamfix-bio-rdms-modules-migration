package com.seamfix.bio.entities;

import lombok.Data;
import java.util.Date;
import javax.persistence.*;
import com.sf.biocloud.entity.enums.ModeOfPayment;
import com.sf.biocloud.entity.enums.SubscriptionMode;
import com.sf.biocloud.entity.enums.SubscriptionStatus;
import org.hibernate.annotations.Cache;
import com.sf.biocloud.entity.enums.BillingCycle;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@Entity
@Table(name ="SUBSCRIPTION")
@Cacheable()
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerSubscription extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String orgId;

    @Column(name = "subscription_plan_id")
    private String subscriptionPlanId;

    @Column(name = "billing_cycle")
    @Enumerated(EnumType.STRING)
    private BillingCycle billingCycle;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "amount_paid")
    private double amountPaid;

    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode")
    private ModeOfPayment paymentMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_status")
    private SubscriptionStatus subscriptionStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_mode")
    private SubscriptionMode subscriptionMode;

    @Column(name = "auto_renew")
    private boolean autoRenew;

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "activated_by")
    private String activatedBy;

}
