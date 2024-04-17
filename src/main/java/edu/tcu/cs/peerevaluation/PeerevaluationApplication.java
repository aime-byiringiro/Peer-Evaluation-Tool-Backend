package edu.tcu.cs.peerevaluation;

import edu.tcu.cs.peerevaluation.section.utils.IdWorker;
import jakarta.persistence.Id;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PeerevaluationApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeerevaluationApplication.class, args);
	}

	@Bean
	public IdWorker idWorker(){
		return  new IdWorker(1,1);
	}

}
