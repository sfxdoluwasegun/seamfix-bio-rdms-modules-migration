package com.seamfix.bio.job.processors;

import com.seamfix.bio.entities.CountryObj;
import com.seamfix.bio.entities.CountryStateObj;
import com.seamfix.bio.entities.Organisation;
import com.seamfix.bio.job.jpa.dao.CountryRepository;
import com.seamfix.bio.job.jpa.dao.LocationRepository;
import com.seamfix.bio.job.jpa.dao.OrganisationRepository;
import com.seamfix.bio.job.jpa.dao.StateRepository;
import com.sf.biocloud.entity.Location;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import java.util.Date;

public class LocationProcessor implements ItemProcessor<Location, com.seamfix.bio.entities.Location> {

    private final OrganisationRepository organisationRepository;
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final LocationRepository locationRepository;

    public LocationProcessor(OrganisationRepository organisationRepository, CountryRepository countryRepository, StateRepository stateRepository, LocationRepository locationRepository) {
        this.organisationRepository = organisationRepository;
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.locationRepository = locationRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(LocationProcessor.class);

    @Override
    public com.seamfix.bio.entities.Location process(Location loc) throws Exception {
        log.info("IClocker location migration job is in progress!");
        com.seamfix.bio.entities.Location converted = new com.seamfix.bio.entities.Location();
        converted.setAddress(loc.getAddress() == null || loc.getAddress().trim().isEmpty() ? "" : loc.getAddress());
        if (loc.getClockOutTime() != null) {
            Instant i = Instant.ofEpochMilli(loc.getClockOutTime());
            if (loc.getResumptionTimezoneId() == null || loc.getResumptionTimezoneId().trim().isEmpty()) {
                converted.setClockOutTime(ZonedDateTime.ofInstant(i, ZoneId.systemDefault()));
            } else {
                try {
                    converted.setClockOutTime(ZonedDateTime.ofInstant(i, ZoneId.of(loc.getResumptionTimezoneId())));
                } catch (DateTimeException ex) {
                    converted.setClockOutTime(ZonedDateTime.ofInstant(i, ZoneId.systemDefault()));
                }

            }
        }

        if (loc.getCountry() != null) {
            CountryObj co = countryRepository.findByCountryId(new Integer(loc.getCountry()));
            if (co != null) {
                converted.setCountry(co);
            }
        }
        if (loc.getCreated() != null) {
            converted.setCreateDate(new Date(loc.getCreated()));

        }

        converted.setCreatedBy(loc.getCreatedBy() == null || loc.getCreatedBy().trim().isEmpty() ? "" : loc.getCreatedBy());
        converted.setGracePeriodInMinutes(loc.getGracePeriodInMinutes());
        converted.setLatitude(loc.getLatitude());
        converted.setLocId(loc.getLocId());

        if (loc.getLocationType() != null) {
            converted.setLocationType(loc.getLocationType());
        }
        converted.setLongitude(loc.getLongitude());
        converted.setName(loc.getName() == null || loc.getName().trim().isEmpty() ? "" : loc.getName());
        if (loc.getOrgId() != null && !loc.getOrgId().trim().isEmpty()) {
            converted.setOrgId(loc.getOrgId());
            Organisation org = organisationRepository.findByOrgId(loc.getOrgId());
            if (org != null) {
                converted.setNewOrgId(org);
            }

        }
        converted.setRadiusThreshold(loc.getRadiusThreshold());
        if (loc.getResumption() != null) {
            Instant i = Instant.ofEpochMilli(loc.getResumption());
            if (loc.getResumptionTimezoneId() == null || loc.getResumptionTimezoneId().trim().isEmpty()) {

                converted.setResumption(ZonedDateTime.ofInstant(i, ZoneId.systemDefault()));

            } else {
                try {
                    converted.setResumption(ZonedDateTime.ofInstant(i, ZoneId.of(loc.getResumptionTimezoneId())));
                } catch (DateTimeException ex) {
                    converted.setResumption(ZonedDateTime.ofInstant(i, ZoneId.systemDefault()));
                }
            }
        }
        if (loc.getState() != null) {
            CountryStateObj co = stateRepository.findByStateId(new Integer(loc.getState()));
            if (co != null) {
                converted.setState(co);
            }
        }
        converted.setResumptionTimezoneId(loc.getResumptionTimezoneId() == null || loc.getResumptionTimezoneId().trim().isEmpty() ? "" : loc.getResumptionTimezoneId());
        locationRepository.save(converted);
        return converted;
    }

}
