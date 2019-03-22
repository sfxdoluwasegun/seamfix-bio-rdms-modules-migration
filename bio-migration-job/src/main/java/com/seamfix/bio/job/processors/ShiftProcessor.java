package com.seamfix.bio.job.processors;

import java.util.Date;
import java.time.ZoneId;
import org.slf4j.Logger;
import java.time.Instant;
import java.time.DayOfWeek;
import org.slf4j.LoggerFactory;
import java.time.ZonedDateTime;
import java.time.DateTimeException;
import com.sf.biocloud.entity.Shift;
import com.seamfix.bio.entities.Location;
import org.apache.commons.lang3.StringUtils;
import com.seamfix.bio.job.jpa.dao.ShiftRepository;
import org.springframework.batch.item.ItemProcessor;
import com.seamfix.bio.job.jpa.dao.LocationRepository;

public class ShiftProcessor implements ItemProcessor<Shift, com.seamfix.bio.entities.Shift> {

    private static final Logger logger = LoggerFactory.getLogger(ShiftProcessor.class);

    private String timeZoneId;
    private final ShiftRepository repository;
    private final LocationRepository locationRepository;

    public ShiftProcessor(ShiftRepository repository, LocationRepository locationRepository) {
        this.repository = repository;
        this.locationRepository = locationRepository;
    }

    @Override
    public com.seamfix.bio.entities.Shift process(Shift shift) throws Exception {
        logger.info("Shift migration in progress");

        Location location = locationRepository.findByLocId(shift.getLocId());
        if (location != null) {
            timeZoneId = location.getResumptionTimezoneId();
        }

        com.seamfix.bio.entities.Shift converted = new com.seamfix.bio.entities.Shift();
        com.seamfix.bio.entities.Shift existingShift = repository.findByShiftId(shift.getShiftId());
        if (existingShift != null) {
            setFields(existingShift, shift);
            converted = existingShift;
        } else {
            setFields(converted, shift);
        }
        repository.save(converted);
        return converted;
    }

    private void setFields(com.seamfix.bio.entities.Shift existingRec, Shift updatedRec) {
        existingRec.setShiftId(StringUtils.isNotBlank(updatedRec.getShiftId()) ? updatedRec.getShiftId() : "");
        existingRec.setLocId(StringUtils.isNotBlank(updatedRec.getLocId()) ? updatedRec.getLocId() : "");
        existingRec.setOrgId(StringUtils.isNotBlank(updatedRec.getOrgId()) ? updatedRec.getOrgId() : "");
        existingRec.setName(StringUtils.isNotBlank(updatedRec.getName()) ? updatedRec.getName() : "");
        existingRec.setGracePeriodInMinutes(updatedRec.getGracePeriodInMinutes());
        existingRec.setActive(updatedRec.isDeleted());
        existingRec.setCreatedBy(updatedRec.getCreatedBy());
        existingRec.setCreateDate(new Date(updatedRec.getCreated()));

        if (updatedRec.getLastModified() != null) {
            existingRec.setLastModified(new Date(updatedRec.getLastModified()));
        }

        if (updatedRec.getResumption() != null && updatedRec.getResumption() > 0) {
            Instant instant = Instant.ofEpochMilli(updatedRec.getResumption());
            if (StringUtils.isBlank(timeZoneId)) {
                existingRec.setResumption(ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()));
            } else {
                try {
                    existingRec.setResumption(ZonedDateTime.ofInstant(instant, ZoneId.of(timeZoneId)));
                } catch (DateTimeException e) {
                    existingRec.setResumption(ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()));
                    logger.debug("Exception: {}", e);
                }
            }
        }

        if (updatedRec.getClockOutTime() != null && updatedRec.getClockOutTime() > 0) {
            Instant instant = Instant.ofEpochMilli(updatedRec.getClockOutTime());
            if (StringUtils.isBlank(timeZoneId)) {
                existingRec.setClockOutTime(ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()));
            } else {
                try {
                    existingRec.setClockOutTime(ZonedDateTime.ofInstant(instant, ZoneId.of(timeZoneId)));
                } catch (DateTimeException e) {
                    existingRec.setClockOutTime(ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()));
                    logger.debug("Exception: {}", e);
                }
            }
        }

        if (updatedRec.getBreakTimeStart() != null && updatedRec.getBreakTimeStart() > 0) {
            Instant instant = Instant.ofEpochMilli(updatedRec.getBreakTimeStart());
            if (StringUtils.isBlank(timeZoneId)) {
                existingRec.setBreakTimeStart(ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()));
            } else {
                try {
                    existingRec.setBreakTimeStart(ZonedDateTime.ofInstant(instant, ZoneId.of(timeZoneId)));
                } catch (DateTimeException e) {
                    existingRec.setBreakTimeStart(ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()));
                    logger.debug("Exception: {}", e);
                }
            }
        }

        if (updatedRec.getBreakTimeEnd() != null && updatedRec.getBreakTimeStart() > 0) {
            Instant instant = Instant.ofEpochMilli(updatedRec.getBreakTimeEnd());
            if (StringUtils.isBlank(timeZoneId)) {
                existingRec.setBreakTimeEnd(ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()));
            } else {
                try {
                    existingRec.setBreakTimeEnd(ZonedDateTime.ofInstant(instant, ZoneId.of(timeZoneId)));
                } catch (DateTimeException e) {
                    existingRec.setBreakTimeEnd(ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()));
                    logger.debug("Exception: {}", e);
                }
            }
        }

        if (updatedRec.getWorkingDays() != null) {
            StringBuilder builder = new StringBuilder();
            for (DayOfWeek day : updatedRec.getWorkingDays()) {
                builder.append(day.name())
                        .append(" ");
            }
            existingRec.setWorkingDays(builder.toString());
        }
    }
    
}
