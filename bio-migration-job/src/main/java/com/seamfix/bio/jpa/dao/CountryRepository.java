package com.seamfix.bio.jpa.dao;

import com.seamfix.bio.entities.CountryObj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Repository
public interface CountryRepository extends JpaRepository<CountryObj, Long> {

    public CountryObj findByName(String name);

    public CountryObj findByCountryId(Integer countryId);

}
