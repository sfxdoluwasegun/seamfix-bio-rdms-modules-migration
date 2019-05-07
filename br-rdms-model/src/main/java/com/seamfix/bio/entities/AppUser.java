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
import javax.persistence.Table;
import javax.persistence.Index;
import javax.persistence.Temporal;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author UCHECHUKWU
 */
@Entity
@Table(name = "APP_USER", indexes = {
    @Index(name = "appuserIdex", columnList = "id,email")})
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class AppUser extends BaseEntity {

    private static final long serialVersionUID = 5877673919423786302L;

    @Column(unique = true)
    private String email;

    private String pw;

    private String phone;

    private String phoneCode;

    private String firstName;

    private String lastName;

    private String middleName;

    private String gender;

    @Column(name = "CLIENT_FIRST_LOGIN")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date clientFirstLogin;

    @Column(name = "LAST_SUCCESSFUL_LOGIN")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date lastSuccessfulLogin;

    @Column(name = "LAST_FAILED_LOGIN")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date lastFailedLogin;

    private Integer failedLoginAttempts = 0;

    @Column(name = "LAST_PASSWORD_CHANGE")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date lastPasswordChange;

    private String companyName;

    private String userType;

    private String address;

    private boolean passwordReset = false;
    @Column(unique = true)
    private String userId;

    private String creator;

    private Boolean appNotif;

    private Boolean promoNotif;

}
