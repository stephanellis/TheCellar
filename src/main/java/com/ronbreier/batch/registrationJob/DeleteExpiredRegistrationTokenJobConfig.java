package com.ronbreier.batch.registrationJob;

import com.ronbreier.batch.mappers.EmailVerificationRowMapper;
import com.ronbreier.entities.EmailVerification;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by ron.breier on 4/27/2017.
 */

@Configuration
@EnableBatchProcessing
public class DeleteExpiredRegistrationTokenJobConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Bean(name="deleteExpiredRegistrationTokenJob")
    public Job deleteExpiredRegistrationTokenJob(){
        return jobs.get("deleteExpiredRegistrationTokenJob")
                .incrementer(new RunIdIncrementer())
                .start(deleteExpiredRegTokensStep())
                .build();
    }

    @Bean
    public Step deleteExpiredRegTokensStep(){
        return stepBuilderFactory.get("deleteExpiredRegTokensStep")
                .<EmailVerification, EmailVerification>chunk(10)
                .reader(deleteExpiredRegTokensReader())
                .processor(deleteExpiredRegTokensProcessor())
                .build();
    }

    @StepScope
    @Bean
    public JdbcCursorItemReader<EmailVerification> deleteExpiredRegTokensReader(){
        JdbcCursorItemReader<EmailVerification> itemReader = new JdbcCursorItemReader<>();
        itemReader.setDataSource(dataSource);
        itemReader.setSql("SELECT * FROM email_verification_tokens WHERE date_generated < TIMESTAMP(DATE_SUB(NOW(), INTERVAL 48 HOUR)) ");
        itemReader.setRowMapper(new EmailVerificationRowMapper(EmailVerification.class));
        return itemReader;
    }

    @Bean
    public DeleteExpiredRegTokensProcessor deleteExpiredRegTokensProcessor(){
        return new DeleteExpiredRegTokensProcessor();
    }

}
