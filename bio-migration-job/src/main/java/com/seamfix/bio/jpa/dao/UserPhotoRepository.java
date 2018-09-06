package com.seamfix.bio.jpa.dao;

import com.seamfix.bio.entities.UserPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Repository
public interface UserPhotoRepository extends JpaRepository<UserPhoto, Long> {

   

}
