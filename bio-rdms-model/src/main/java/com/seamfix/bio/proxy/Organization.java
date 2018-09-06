package com.seamfix.bio.proxy;

import com.sf.bioregistra.entity.BaseEntity;
import com.sf.bioregistra.entity.EmployeeRange;
import com.sf.bioregistra.entity.enums.PaymentPlan;
import com.sf.bioregistra.entity.enums.ProductName;
import com.sf.bioregistra.entity.subscription.SubscriptionStatus;
import com.sf.bioregistra.entity.subscription.SubscriptionType;
import org.springframework.data.mongodb.core.mapping.Field;

public class Organization extends BaseEntity {
    private String orgId;
    private String name;
    private String orgType;
    private ProductName productName;
    private String logo;
    private String createdBy;
    private String orgCode;
    private EmployeeRange employeeRange;
    private PaymentPlan paymentPlan;

    @Field("wId")
    private String walletId;

    public Organization() {
    }

    public Organization(String orgId, String name) {
        this.orgId = orgId;
        this.name = name;
    }

    public Organization(String orgId, String name, String walletId) {
        this.orgId = orgId;
        this.name = name;
        this.walletId = walletId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getWalletId() {
        return this.walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public ProductName getProductName() {
        return this.productName;
    }

    public void setProductName(ProductName productName) {
        this.productName = productName;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public PaymentPlan getPaymentPlan() {
        return this.paymentPlan;
    }

    public void setPaymentPlan(PaymentPlan paymentPlan) {
        this.paymentPlan = paymentPlan;
    }

    public EmployeeRange getEmployeeRange() {
        return this.employeeRange;
    }

    public void setEmployeeRange(EmployeeRange employeeRange) {
        this.employeeRange = employeeRange;
    }
}
