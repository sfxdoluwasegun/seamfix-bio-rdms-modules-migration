package com.seamfix.bio.entities;

import com.sf.bioregistra.entity.subscription.SubscriptionTypeCode;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author Uchechukwu Onuoha
 */
@javax.persistence.Entity
@Table(name = "SUBSCRIPTION_TYPE", indexes = {
    @Index(name = "SubscriptionTypeIndex", columnList = "id,code,name")})
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class SubscriptionType extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private SubscriptionTypeCode code;

    private String name;

    private String oldId;

    private String description;

    private boolean defaultSubscription;

    @OneToMany(mappedBy = "newSubscripTypeId")
    private List<Organisation> orgs = new ArrayList<>();

}
