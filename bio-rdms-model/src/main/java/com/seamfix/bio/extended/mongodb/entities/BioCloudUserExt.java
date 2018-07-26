/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.extended.mongodb.entities;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Document(collection = "biocloud_users")
public class BioCloudUserExt extends com.sf.biocloud.entity.BioCloudUserExt {

}
