package com.maxminmajcdg;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan
@Configuration
@EnableCaching
@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
@EnableJpaAuditing
public class MaxminmajcdgApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaxminmajcdgApplication.class, args);
	}
	
	@Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public PlatformTransactionManager transactionManager() 
    {
      return new JpaTransactionManager(entityManagerFactory);
    }


}
