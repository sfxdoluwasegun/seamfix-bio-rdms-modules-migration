package com.seamfix.bio.extended.mongodb.entities;

import com.sf.bioregistra.entity.subscription.SubscriptionTypeCode;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "subscription_type")
@Data
public class SubscriptionType extends BaseEntity {

    @Indexed(unique = true)
    private SubscriptionTypeCode code;

    private String name;

    private String description;

    private boolean defaultSubscription;

   
}
