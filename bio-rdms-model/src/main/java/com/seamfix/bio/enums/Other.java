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
public enum Other {

    PDF(".pdf"),
    JPG(".jpeg"),
    PNG(".png");

    private String name;

    public String getName() {
        return name;
    }

    Other(String name) {
        this.name = name;
    }

    public static Other fromname(String name) {
        for (Other type : values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        return null;
    }

}
