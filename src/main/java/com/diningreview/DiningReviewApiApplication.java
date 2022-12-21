package com.diningreview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class DiningReviewApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiningReviewApiApplication.class, args);
	}

}
