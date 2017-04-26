package com.ronbreier;

import com.ronbreier.services.StorageProperties;
import com.ronbreier.services.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableConfigurationProperties(StorageProperties.class)
public class TheCellarApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheCellarApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService){
		return (args) -> {
			storageService.init();
		};
	}

}
