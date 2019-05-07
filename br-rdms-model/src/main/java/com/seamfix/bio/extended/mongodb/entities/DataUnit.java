/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.extended.mongodb.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author uonuoha
 */
@Data
public class DataUnit {

    @Field(value = "id")
    private String id;
    private String label;
    private String value;
    private boolean newValue;
    private String type;
    private String ext;
    private String threshold;
    private String deviceName;
    private String deviceRes;
    private String skipReason;

}
