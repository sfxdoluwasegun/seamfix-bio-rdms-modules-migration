/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.entities;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Uchechukwu
 */
@MappedSuperclass
public class BaseEntity implements Serializable, Comparable<BaseEntity> {

    private static final long serialVersionUID = -5965442215210696967L;
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, insertable = true, updatable = false)
    private Long id;

    @Column(name = "ACTIVE", nullable = false, insertable = true, updatable = true)
    private boolean active = true;

    @Column(name = "DELETED", nullable = false, insertable = true, updatable = true)
    private boolean deleted;

    @Column(name = "CREATE_DATE", nullable = false, insertable = true, updatable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createDate = new Date();

    //@Version
    @Column(name = "LAST_MODIFIED", nullable = true, insertable = true, updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified = new Date();

    @Column(nullable = true, name = "LAST_UPDATED_BY")
    private String lastUpdatedBy;
    @Column(nullable = true, name = "CREATED_BY")
    private String createdBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public int compareTo(BaseEntity o) {
        int cmp = 0;
        if (this.getId() != null && (Objects.equals(this.getId(), o.getId()) || this.getId().equals(o.getId()))) {
            cmp = 0;
        } else {
            cmp = -1;
        }
        return cmp;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        hashCode = 29 * hashCode + (getId() == null ? 0 : getId().hashCode());
        return hashCode;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof BaseEntity)) {
            return false;
        }
        final BaseEntity that = (BaseEntity) object;
        if (this.getId() == null || that.getId() == null
                || !this.getId().equals(that.getId())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append(getTableName());
        result.append(" {");
        result.append(newLine);

        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            result.append("  ");
            try {
                result.append(field.getName());
                result.append(": ");

                String name = field.getName();
                String prefix = "get";
                if (field.getType().isAssignableFrom(Boolean.class)) {
                    prefix = "is";
                }
                name = name.substring(0, 1).toUpperCase() + name.substring(1);

                result.append(this.getClass().getMethod(prefix + name).invoke(this));
            } catch (Exception ex) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();
    }

    public String getTableName() {
        Table table = getClass().getAnnotation(Table.class);
        String tableName = table.name();
        return tableName;
    }

}
