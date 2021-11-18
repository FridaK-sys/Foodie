package foodie.restapi;

import com.fasterxml.jackson.databind.Module;
import json.CookbookPersistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * CookbookApplication that runs the SpringApplication.
 */
@SpringBootApplication
public class CookbookApplication {

	@Bean
	public Module objectMapperModule() {
		return CookbookPersistence.createModule();
	}

	public static void main(String[] args) {
		SpringApplication.run(CookbookApplication.class, args);
	}

}
