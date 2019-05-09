package com.seamfix.bio.job.processors;

import com.seamfix.bio.entities.ReEnrolmentLog;
import com.seamfix.bio.job.jpa.dao.ReEnrolmentRepository;
import com.sf.biocloud.entity.ReEnrollmentLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.Date;

public class ReEnrolmentLogProcessor implements ItemProcessor<ReEnrollmentLog, ReEnrolmentLog> {

    private static final Logger logger = LoggerFactory.getLogger(ReEnrolmentLogProcessor.class);
    private final ReEnrolmentRepository reEnrolmentRepository;

    public ReEnrolmentLogProcessor(ReEnrolmentRepository reEnrolmentRepository) {
        this.reEnrolmentRepository = reEnrolmentRepository;
    }

    @Override
    public ReEnrolmentLog process(ReEnrollmentLog reEnrollmentLog) throws Exception {
        logger.info("Re Enrolment migration in progress");

        ReEnrolmentLog converted = new ReEnrolmentLog();

//        converted.setEmail(reEnrollmentLog.getEmail());
        converted.setUserId(reEnrollmentLog.getUserId());
        converted.setJobId(reEnrollmentLog.getJobId());
        converted.setReEnrolmentTime(new Date(reEnrollmentLog.getReEnrollmentTime()));
        converted.setCreateDate(new Date(reEnrollmentLog.getCreated()));
        converted.setLastModified(new Date(reEnrollmentLog.getLastModified()));

        reEnrolmentRepository.save(converted);

        return converted;
    }
}
