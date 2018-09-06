/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.entities;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "USER_PHOTO", indexes = {
    @Index(name = "userPhotoIndex", columnList = "id,new_user_id")})
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class UserPhoto extends BaseEntity {

    @OneToOne(optional = true)
    @JoinColumn(nullable = false, unique = true, name = "new_user_id")
    private AppUser newUserId;

    private String userId;

    @Column(columnDefinition = "TEXT")
    private String profilePhoto;

    @Column(columnDefinition = "TEXT")
    private String verifiablePhoto;

}
