/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.job.config;

import com.seamfix.bio.service.MigrationJobService;
import com.seamfix.bio.service.TimeSelectorService;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Configuration
@EnableScheduling
public class SchedularConfig implements SchedulingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(SchedularConfig.class);

    @Autowired
    TimeSelectorService timeSelectorService;

    @Autowired
    MigrationJobService migrationJobService;

    private String cronConfig() {
        String cronTabExpression = timeSelectorService.getCronTabExpression();
        if (cronTabExpression == null || timeSelectorService.getCronTabExpression().isEmpty()) {
            cronTabExpression = "0 0 0/1 * * ?";
        }
        return cronTabExpression;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                log.info("Starting Migration Job");
                migrationJobService.launchJob();
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                String cron = cronConfig();
                log.info(cron);
                CronTrigger trigger = new CronTrigger(cron);
                Date nextExec = trigger.nextExecutionTime(triggerContext);
                return nextExec;
            }
        });
    }

}
