/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.Data;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Embeddable
@Data
public class EmployeeId implements Serializable {

    private Long id;

    private String email;

    private long newLocId;

    private long newOrgId;

    public EmployeeId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeId)) {
            return false;
        }
        EmployeeId that = (EmployeeId) o;
        return Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getNewOrgId(), that.getNewOrgId()) && Objects.equals(getNewLocId(), that.getNewLocId());

    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getNewOrgId(), getNewLocId());
    }

}
