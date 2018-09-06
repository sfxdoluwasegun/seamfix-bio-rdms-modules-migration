package com.seamfix.bio.jpa.dao;

import com.seamfix.bio.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    public Location findByLocId(String locId);

    public Location findTopByOrderByCreateDateDesc();

}
