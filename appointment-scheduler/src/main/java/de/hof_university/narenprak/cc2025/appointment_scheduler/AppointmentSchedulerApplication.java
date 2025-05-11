package de.hof_university.narenprak.cc2025.appointment_scheduler;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class AppointmentSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppointmentSchedulerApplication.class, args);
	}

	@Bean
	CommandLineRunner checkMongoConnection(MongoTemplate mongoTemplate) {
		return args -> {
			try {
				mongoTemplate.getCollectionNames();
				System.out.println("Successfully connected to MongoDB and retrieved collection names!");
			} catch (Exception e) {
				System.err.println("Failed to connect to MongoDB or retrieve collection names: " + e.getMessage());
				e.printStackTrace();
			}
		};
	}

}
