/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.enums;

/**
 *
 * @author UCHECHUKWU
 */
public enum RoleCategory {

    ORG("ORG"),
    PROJECT("PROJECT");

    private String name;

    public String getName() {
        return name;
    }

    RoleCategory(String name) {
        this.name = name;
    }

    public static RoleCategory fromname(String name) {
        for (RoleCategory type : values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        return null;
    }

}
