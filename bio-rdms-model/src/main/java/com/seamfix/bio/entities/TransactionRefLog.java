/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.entities;

import com.seamfix.bio.enums.TransactionStatusEnum;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Entity
@Table(name = "TRANSACTION_REF_LOG")
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class TransactionRefLog extends BaseEntity {

    private String cipher;
    @Column(unique = true)
    private String oldId;
    private double amount;
    private String transactionRef;
    private String currency;
    private String orgId;
    private String txMode;
    @Enumerated(EnumType.STRING)
    private TransactionStatusEnum status;

}
