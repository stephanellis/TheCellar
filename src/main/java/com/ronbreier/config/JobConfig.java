package com.ronbreier.config;

import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Ron Breier on 4/28/2017.
 */

@Component
@EnableScheduling
public class JobConfig {

    private static final Logger LOGGER = Logger.getLogger(JobConfig.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job deleteExpiredRegistrationTokenJob;

    @Autowired
    private JobRepository jobRepository;

    @Scheduled(cron = "0 0 0/4 * * ?")
    public void runJob() throws Exception {
        try {
            JobParameters jobParameters =
                    new JobParametersBuilder()
                            .addLong("time", System.currentTimeMillis())
                            .addDate("date", new Date())
                            .toJobParameters();
            JobExecution execution = jobLauncher.run(deleteExpiredRegistrationTokenJob, jobParameters);
            LOGGER.info("Job Exit Status; " + execution.getStatus());
        } catch (Exception e) {
            LOGGER.error("There was an error running the job ", e);
        }
    }

}
