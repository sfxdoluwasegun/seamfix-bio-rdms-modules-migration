package com.seamfix.bio.job.processors;

import com.seamfix.bio.entities.AuthAudit;
import com.seamfix.bio.entities.Location;
import com.seamfix.bio.job.jpa.dao.AuthAuditRepository;
import com.seamfix.bio.job.jpa.dao.LocationRepository;
import com.seamfix.bio.job.util.TimezoneUtil;
import com.sf.biocloud.entity.IclockerAuthAudit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public class AuthAuditProcessor implements ItemProcessor<IclockerAuthAudit, AuthAudit> {

    private static final Logger log = LoggerFactory.getLogger(AuthAuditProcessor.class);
    private final AuthAuditRepository authAuditRepository;
    private final LocationRepository locationRepository;

    public AuthAuditProcessor(AuthAuditRepository authAuditRepository, LocationRepository locationRepository) {
        this.authAuditRepository = authAuditRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public AuthAudit process(IclockerAuthAudit iclockerAuthAudit) throws Exception {

        log.info("Auth Audit Log migration job is in progress!");


        List<AuthAudit> existingRecord = null;

        if(iclockerAuthAudit.getProviderJobId() != null) {
            existingRecord = authAuditRepository.findByProviderJobId(iclockerAuthAudit.getProviderJobId());
        }

        if(existingRecord == null || existingRecord.isEmpty()) {
            AuthAudit converted = new AuthAudit();

            Location location = locationRepository.findByLocId(iclockerAuthAudit.getLocId());

            converted.setReconciled(iclockerAuthAudit.isReconciled());
            converted.setEmail(iclockerAuthAudit.getEmail());
            converted.setLocId(iclockerAuthAudit.getLocId());
            converted.setLongitude(iclockerAuthAudit.getLongitude());
            converted.setLatitude(iclockerAuthAudit.getLatitude());
            converted.setMatchConfidenceLevel(iclockerAuthAudit.getMatchConfidenceLevel());
            converted.setAuthMode(iclockerAuthAudit.getAuthMode());
            converted.setClockInMode(iclockerAuthAudit.getClockInMode());
            converted.setMatch(iclockerAuthAudit.isMatch());
            converted.setLastModified(new Date(iclockerAuthAudit.getLastModified()));
            converted.setCreateDate(new Date(iclockerAuthAudit.getCreated()));
            converted.setProviderJobId(iclockerAuthAudit.getProviderJobId());


            if (iclockerAuthAudit.getTime() != null) {
                Instant i = Instant.ofEpochMilli(iclockerAuthAudit.getTime());
                if (location != null) {
                    converted.setTime(TimezoneUtil.getZonedTime(iclockerAuthAudit.getTime(), location.getResumptionTimezoneId()));
                } else {
                    converted.setTime(ZonedDateTime.ofInstant(i, ZoneId.systemDefault()));
                }
            }

            authAuditRepository.save(converted);
            return converted;

        } else {
            if(iclockerAuthAudit.getProviderJobId() != null && iclockerAuthAudit.getLastModified() != null) {
                authAuditRepository.updateAuthAudit(iclockerAuthAudit.isReconciled(), iclockerAuthAudit.getProviderJobId(), new Date(iclockerAuthAudit.getLastModified()));
            }

            return existingRecord.get(0);
        }
    }
}
