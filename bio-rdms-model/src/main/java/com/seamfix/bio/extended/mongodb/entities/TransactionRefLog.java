/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.extended.mongodb.entities;

import com.seamfix.bio.enums.TransactionStatusEnum;
import com.sf.bioregistra.entity.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Document(collection = "transaction_ref_log")
public class TransactionRefLog extends BaseEntity {

    private String cipher;
    private double amount;
    private String transactionRef;
    private String currency;
    private String orgId;
    private String txMode;
    private TransactionStatusEnum status;

    public TransactionRefLog(String orgId, String cipher, double amount, String transactionRef, String currency, String txMode) {
        this.orgId = orgId;
        this.cipher = cipher;
        this.amount = amount;
        this.transactionRef = transactionRef;
        this.currency = currency;
        this.txMode = txMode;
        this.status = TransactionStatusEnum.PENDING;
    }

    public TransactionRefLog() {
    }

    public String getCipher() {
        return cipher;
    }

    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionRef() {
        return transactionRef;
    }

    public void setTransactionRef(String transactionRef) {
        this.transactionRef = transactionRef;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getTxMode() {
        return txMode;
    }

    public void setTxMode(String txMode) {
        this.txMode = txMode;
    }

    public TransactionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TransactionStatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TransactionRefLog{"
                + "cipher='" + cipher + '\''
                + ", amount=" + amount
                + ", transactionRef='" + transactionRef + '\''
                + ", currency='" + currency + '\''
                + ", orgId='" + orgId + '\''
                + ", txMode='" + txMode + '\''
                + ", status=" + status
                + '}';
    }
}
