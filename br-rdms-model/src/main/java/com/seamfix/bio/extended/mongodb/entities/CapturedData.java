/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.extended.mongodb.entities;

import java.util.ArrayList;
import lombok.Data;

/**
 *
 * @author uonuoha
 */
@Data
public class CapturedData extends ProxyBaseEntity {

    private String tag;

    private ArrayList<DataUnit> text;

    private String createdBy;

    private Boolean verified;

    private String preId;

    private double cost;

    private String machineId;

    private String machineSpecs;

    private String longitude;

    private String latitude;

    private String location;

    private String customId;

    private String flag = "ACTIVE";

    private String useCase;

    private String prevId;

    private String sField;

    private String captureAddr;

    private Long modified;

    private Double appVersion;

    private String reason;

    private boolean pushStatus;

    private String pushRef;

    private String geofenceStatus;

    private String geoFenceId;

    private String pId;

    private String ppId;

    private String cId;

    private Long captured;

    private String uniqueId;

    private String puniqueId;

    private String euniqueId;

}
