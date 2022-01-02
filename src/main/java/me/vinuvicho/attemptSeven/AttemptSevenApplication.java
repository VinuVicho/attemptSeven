package me.vinuvicho.attemptSeven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync			//prob not working
public class AttemptSevenApplication {
	public static void main(String[] args) {
		SpringApplication.run(AttemptSevenApplication.class, args);
	}
}
