/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.entities;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Entity
@Table(name = "CAPTURED_DATA_DEMOGRAPHICS", indexes = {
    @Index(name = "captured_data_demo_Index", columnList = "id,uniqueId,capture_data_id")})
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class CapturedDataDemoGraphics extends BaseEntity {

    @Column(nullable = false)
    private String uniqueId;
    
    @Column
    private String textId;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, name = "capture_data_id")
    private CapturedData capturedDataId;

    private String label;

    @Column(length = 5000)
    private String value;

    @Column(length = 5000)
    private boolean newValue;

    private String type;

    private String ext;

    private String threshold;

    private String deviceName;

    private String deviceRes;

    private String skipReason;

}
