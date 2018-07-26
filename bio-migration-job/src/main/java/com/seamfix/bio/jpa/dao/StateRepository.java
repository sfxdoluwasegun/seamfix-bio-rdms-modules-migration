package com.seamfix.bio.jpa.dao;

import com.seamfix.bio.entities.CountryStateObj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Repository
public interface StateRepository extends JpaRepository<CountryStateObj, Long> {

    public CountryStateObj findByStateId(Integer stateId);

}
