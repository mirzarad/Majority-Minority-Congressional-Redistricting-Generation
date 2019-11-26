package com.maxminmajcdg;

import org.springframework.boot.SpringApplication; 
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MaxminmajcdgApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaxminmajcdgApplication.class, args);
	}

}
