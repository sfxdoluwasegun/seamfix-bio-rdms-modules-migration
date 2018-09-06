package com.seamfix.bio.job.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class MigrationListener implements JobExecutionListener {

    private static Logger logger = LoggerFactory.getLogger(MigrationListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("==== Before job start " + jobExecution.getStatus());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info("===== After Job ends " + jobExecution.getStatus());
    }
}
