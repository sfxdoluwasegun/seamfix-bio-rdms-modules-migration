package com.seamfix.bio.job.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class MigrationListener implements JobExecutionListener {

    private static Logger logger = LoggerFactory.getLogger(MigrationListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("==== Starting BioRegistra Migration Job " + jobExecution.getStatus());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info("===== BioRegistra Migration Job Ends " + jobExecution.getStatus());
    }
}
