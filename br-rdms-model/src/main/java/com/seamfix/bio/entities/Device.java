/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.entities;

import java.util.Date;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Entity
@Table(name = "DEVICES", indexes = {
    @Index(name = "devices_Index", columnList = "id")})
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Device extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String uniqueId;
    private String tag;
    private String type;
    @Column(name = "MODIFIED", nullable = true, insertable = true, updatable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modified;
    private String state;
    private String lga;

}
