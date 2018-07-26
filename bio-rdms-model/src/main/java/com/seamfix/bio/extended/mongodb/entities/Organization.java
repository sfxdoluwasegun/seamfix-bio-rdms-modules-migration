/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.extended.mongodb.entities;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author Uchechukwu Onuoha
 */
public class Organization extends com.sf.bioregistra.entity.Organization {

    @Field("wId")
    private String walletId;

    @Override
    public String getWalletId() {
        return walletId;
    }

    @Override
    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

}
