/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.job.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author Uchechukwu Onuoha <yoursuche@gmail.com>
 */
@Component
public class JobSchedular {

    private static final Logger logger = LoggerFactory.getLogger(JobSchedular.class);

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @Scheduled(cron = "${cron.expression}")
    public void launchJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.nanoTime()).toJobParameters();
            System.out.println("Param  "+ jobParameters.toString());
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            logger.error("ERROR : " + e.getMessage());
        }
    }

}
