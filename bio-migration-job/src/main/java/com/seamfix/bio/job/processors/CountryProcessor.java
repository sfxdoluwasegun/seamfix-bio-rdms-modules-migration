package com.seamfix.bio.job.processors;

import com.seamfix.bio.entities.CountryObj;
import com.seamfix.bio.entities.CountryStateObj;
import com.seamfix.bio.jpa.dao.CountryRepository;
import com.seamfix.bio.jpa.dao.StateRepository;
import com.sf.bioregistra.entity.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class CountryProcessor implements ItemProcessor<Country, CountryObj> {

    private static final Logger log = LoggerFactory.getLogger(CountryProcessor.class);

    private final CountryRepository countryRepository;

    private final StateRepository stateRepository;

    public CountryProcessor(CountryRepository countryRepository, StateRepository stateRepository) {
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
    }

    @Override
    public CountryObj process(Country country) throws Exception {
        log.info("Country and state migration job is in progress!");
        CountryObj converted = new CountryObj();
       
        converted.setCode(country.getCode() == null || country.getCode().trim().isEmpty() ? "" : country.getCode());
        converted.setContinent(country.getContinent() == null || country.getContinent().trim().isEmpty() ? "" : country.getContinent());
        converted.setCountryId(country.getCountryId() == null ? 0 : country.getCountryId());
        converted.setName(country.getName() == null || country.getName().trim().isEmpty() ? "" : country.getName());
        converted.setPhoneCode(country.getPhoneCode() == null || country.getPhoneCode().trim().isEmpty() ? "" : country.getPhoneCode());
        converted=countryRepository.save(converted);
        if (country.getStates() != null && !country.getStates().isEmpty()) {

            for (com.sf.bioregistra.entity.State st : country.getStates()) {
                CountryStateObj nstate = new CountryStateObj();
                nstate.setCode(st.getCode());
                nstate.setName(st.getName());
                nstate.setStateId(st.getStateId());
                nstate.setCountry(converted);
               stateRepository.save(nstate);

            }
           // converted.setStates(states);

        }

        return converted;
    }

}
