/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.entities;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Entity
@Table(name = "ICLOCKER_USER_EXT", indexes = {
    @Index(name = "iclockerUserIndex", columnList = "id,uid")})
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class IclockerUserExt extends BaseEntity {

    @OneToOne(optional = true)
    @JoinColumn(nullable = false, name = "uid")
    private AppUser uid;

    private String userServiceProviderId;

    private boolean enrolled;

    private String bio;

    private String userId;

}
