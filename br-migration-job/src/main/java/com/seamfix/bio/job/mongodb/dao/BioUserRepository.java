/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.job.mongodb.dao;

import com.sf.bioregistra.entity.BioUser;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Uchechukwu Onuoha
 */
public interface BioUserRepository extends MongoRepository<BioUser, String> {

    public BioUser findTopByOrderByCreatedDesc();

}
