/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.entities;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
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
@Table(name = "STATE", indexes = {
    @Index(name = "stateIndex", columnList = "id,code")})
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class CountryStateObj extends BaseEntity {

    private String name;

    private String code;

    private Integer stateId;

    @ManyToOne(optional = true)
    @JoinColumn(name = "COUNTRY_ID", nullable = true)
    private CountryObj country;

}
