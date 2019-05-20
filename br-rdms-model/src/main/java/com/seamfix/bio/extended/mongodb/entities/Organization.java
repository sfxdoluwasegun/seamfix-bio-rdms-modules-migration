/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.extended.mongodb.entities;

import com.sf.bioregistra.entity.enums.ProductName;
import com.sf.bioregistra.entity.subscription.SubscriptionStatus;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Data
@Document(collection = "organization")
public class Organization extends BaseEntity {

    private String orgId;
    private String name;
    private String orgType;
    @Field("wId")
    private String walletId;
    private ProductName productName;
    private String logo;
    private String createdBy;
    private SubscriptionStatus subscriptionStatus;
    @DBRef
    private ObjectId subscriptionType;

}
