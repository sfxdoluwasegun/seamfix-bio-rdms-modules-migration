/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.job.mongodb.dao;

import com.seamfix.bio.extended.mongodb.entities.BioCloudUserExt;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * @author Uchechukwu Onuoha
 */
public interface IclockerUserExtMongoRepository extends MongoRepository<BioCloudUserExt, String> {

    @Query("{ 'userId' : ?0 }")
    BioCloudUserExt findByUserIdQuery(String userId);

}
