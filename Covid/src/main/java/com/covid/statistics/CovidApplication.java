package com.covid.statistics;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class CovidApplication   {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	CountryRepository repository;	
	
	public static void main(String[] args) {
		SpringApplication.run(CovidApplication.class, args);
	}

	

}
