package com.seamfix.bio.job.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sf.biocloud.entity.Shift;
import org.apache.commons.lang3.StringUtils;
import com.seamfix.bio.job.jpa.dao.ShiftRepository;
import org.springframework.batch.item.ItemProcessor;

public class ShiftProcessor implements ItemProcessor<Shift, com.seamfix.bio.entities.Shift> {

    private static final Logger logger = LoggerFactory.getLogger(ShiftProcessor.class);

    private final ShiftRepository repository;

    public ShiftProcessor(ShiftRepository repository) {
        this.repository = repository;
    }

    @Override
    public com.seamfix.bio.entities.Shift process(Shift shift) throws Exception {
        logger.debug("Processing shift record");

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
        existingRec.setResumption(updatedRec.getResumption() != null ? updatedRec.getResumption() : 0);
        existingRec.setClockOutTime(updatedRec.getClockOutTime() != null ? updatedRec.getClockOutTime() : 0);
        existingRec.setBreakTimeStart(updatedRec.getBreakTimeStart() != null ? updatedRec.getBreakTimeStart() : 0);
        existingRec.setBreakTimeEnd(updatedRec.getBreakTimeEnd() != null ? updatedRec.getBreakTimeEnd() : 0);
        existingRec.setGracePeriodInMinutes(updatedRec.getGracePeriodInMinutes());
        existingRec.setActive(updatedRec.isDeleted());

        if (updatedRec.getWorkingDays() != null) {
            existingRec.setWorkingDays(updatedRec.getWorkingDays());
        }
    }
    
}
