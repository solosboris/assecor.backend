package de.assecor.csv.server;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "Assecor CSV example",
		version = "1.0",
		description = "API documentation"
	)
)
public class CSVApplication {

	public static void main(String[] args) {
		SpringApplication.run(
			CSVApplication.class,
			args
		);
	}

}