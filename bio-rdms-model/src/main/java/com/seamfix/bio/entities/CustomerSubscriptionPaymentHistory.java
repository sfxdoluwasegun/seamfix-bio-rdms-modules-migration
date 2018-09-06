package com.seamfix.bio.entities;

import lombok.Data;
import java.util.Date;
import javax.persistence.*;
import com.sf.biocloud.entity.enums.BillingCycle;
import com.sf.biocloud.entity.enums.ModeOfPayment;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@Cacheable
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "SUBSCRIPTION_PAYMENT_HISTORY",
        uniqueConstraints={
                @UniqueConstraint(columnNames = {"org_id", "reference"})
        },
        indexes = {
        @Index(name = "subPaymentHistoryIdx", columnList = "org_id,reference")
})
public class CustomerSubscriptionPaymentHistory extends BaseEntity {

    @Column()
    private String reference;

    @Column(name = "org_id")
    private String orgId;

    @Column(name = "billing_cycle")
    @Enumerated(EnumType.STRING)
    private BillingCycle billingCycle;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "amount_paid")
    private double amountPaid;

    @Column(name = "subscription_plan_id")
    private String subscriptionPlanId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode")
    private ModeOfPayment paymentMode;

    private String currency;

    @Column(name = "conversion_rate")
    private double conversionRate;

    @Column(name = "response_code")
    private String responseCode;

    @Column(name = "response_status")
    private String responseStatus;

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

}
