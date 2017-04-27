package com.ronbreier;

import com.ronbreier.services.StorageProperties;
import com.ronbreier.services.StorageService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableConfigurationProperties(StorageProperties.class)
@EnableScheduling
public class TheCellarApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheCellarApplication.class, args);
	}

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;


	@Bean
	CommandLineRunner init(StorageService storageService){
		return (args) -> {
			storageService.init();
		};
	}

	/**The pattern is a list of six single space-separated fields:
	 * representing second, minute, hour, day, month, weekday.
	 * Month and weekday names can be given as the first three letters of the English names.
	 * Example patterns:

	 "0 0 * * * *" = the top of every hour of every day.*
	 "*\/10 * * * * *" = every ten seconds. Remove 2nd character, it is escape
	 "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
	 "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.
	 "0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
	 "0 0 0 25 12 ?" = every Christmas Day at midnight

	 */

	@Scheduled(cron = "0 * 0/4 * * ?")
	public void runJob() throws Exception {
		jobLauncher.run(job, new JobParameters());
	}

}
