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
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

/**
 *
 * @author Uchechukwu
 */
@MappedSuperclass
@Data
public class EmbeddedBaseEntity implements Serializable {

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
