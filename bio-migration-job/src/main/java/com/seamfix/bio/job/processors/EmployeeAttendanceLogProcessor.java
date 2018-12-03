package com.seamfix.bio.job.processors;

import com.seamfix.bio.entities.EmployeeAttendanceLog;
import com.seamfix.bio.entities.Location;
import com.seamfix.bio.entities.Organisation;
import com.seamfix.bio.job.jpa.dao.EmployeeAttendanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import com.seamfix.bio.job.jpa.dao.LocationRepository;
import com.seamfix.bio.job.jpa.dao.OrganisationRepository;
import com.sf.biocloud.entity.AttendanceLog;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class EmployeeAttendanceLogProcessor implements ItemProcessor<AttendanceLog, EmployeeAttendanceLog> {
    
    private static final Logger logg = LoggerFactory.getLogger(EmployeeAttendanceLogProcessor.class);

    private final LocationRepository locationRepository;
    private final OrganisationRepository organisationRepository;
    private final EmployeeAttendanceRepository employeeAttendanceRepository;
    
    public EmployeeAttendanceLogProcessor(LocationRepository locationRepository, OrganisationRepository organisationRepository, EmployeeAttendanceRepository employeeAttendanceRepository) {
        this.locationRepository = locationRepository;
        this.organisationRepository = organisationRepository;
        this.employeeAttendanceRepository = employeeAttendanceRepository;
    }
    
    @Override
    public EmployeeAttendanceLog process(AttendanceLog log) throws Exception {
        
        logg.info("Employee Attendance Log migration job is in progress!");
        
        EmployeeAttendanceLog converted = new EmployeeAttendanceLog();
        Organisation org = organisationRepository.findByOrgId(log.getOrgId());
        if (org != null) {
            converted.setOrganisation(org);            
        }
        Location loc = locationRepository.findByLocId(log.getLocId());
        if (loc != null) {
            converted.setLocation(loc);            
        }
        
        converted.setActionType(log.getActionType() == null || log.getActionType().trim().isEmpty() ? "" : log.getActionType());
        if (log.getAuthMode() != null) {
            converted.setAuthMode(log.getAuthMode());
        }
        converted.setClockInAddress(log.getClockInAddress() == null || log.getClockInAddress().trim().isEmpty() ? "" : log.getClockInAddress());
        if (log.getClockInLocationStatus() != null) {
            converted.setClockInLocationStatus(log.getClockInLocationStatus());
        }
        if (log.getClockInMode() != null) {
            converted.setClockInMode(log.getClockInMode());
        }
        
        if (log.getCreated() != null) {
            converted.setCreateDate(new Date(log.getCreated()));
        }
        converted.setOldId(log.getId().toHexString());
        converted.setDescription(log.getDescription() == null || log.getDescription().trim().isEmpty() ? "" : log.getDescription());
        converted.setDeviceId(log.getDeviceId() == null || log.getDeviceId().trim().isEmpty() ? "" : log.getDeviceId());
        converted.setEmail(log.getEmail() == null || log.getEmail().trim().isEmpty() ? "" : log.getEmail());
        converted.setLocId(log.getLocId() == null || log.getLocId().trim().isEmpty() ? "" : log.getLocId());
        converted.setOrgId(log.getOrgId() == null || log.getOrgId().trim().isEmpty() ? "" : log.getOrgId());
        converted.setLatitude(log.getLatitude());
        converted.setLongitude(log.getLongitude());
        converted.setMatch(log.isMatch());
        converted.setMatchConfidenceLevel(log.getMatchConfidenceLevel());
        if (log.getPunctualityType() != null) {
            converted.setPunctualityType(log.getPunctualityType());
        }
        
        if (log.getTime() != null) {
            Instant i = Instant.ofEpochMilli(log.getTime());
            if (loc != null) {
                if (loc.getResumptionTimezoneId() == null || loc.getResumptionTimezoneId().trim().isEmpty()) {
                    converted.setTime(ZonedDateTime.ofInstant(i, ZoneId.systemDefault()));
                } else {
                    try {
                        converted.setTime(ZonedDateTime.ofInstant(i, ZoneId.of(loc.getResumptionTimezoneId())));
                    } catch (DateTimeException ex) {
                        converted.setTime(ZonedDateTime.ofInstant(i, ZoneId.systemDefault()));
                    }
                    
                }
            } else {
                converted.setTime(ZonedDateTime.ofInstant(i, ZoneId.systemDefault()));
            }
        }

        if(log.getJobId() != null) {
            converted.setJobId(log.getJobId());
        }

        if(log.getStatus() != null) {
            converted.setStatus(log.getStatus().toString());
        }

        if (log.getTime() != null) {
            converted.setCreateDate(new Date(log.getCreated()));
        }
        employeeAttendanceRepository.save(converted);
        return converted;
    }
    
}
