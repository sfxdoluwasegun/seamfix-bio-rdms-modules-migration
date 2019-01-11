package com.seamfix.bio.entities;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PROSPECTIVE_USER")
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class ProspectiveUsers extends BaseEntity {

    private String email;

    private String token;

    private long expTime;

    private String createdBy;
}
