package com.ronbreier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class TheCellarApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheCellarApplication.class, args);
	}
}
