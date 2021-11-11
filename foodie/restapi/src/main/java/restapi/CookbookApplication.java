package restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import json.CookbookPersistence;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CookbookApplication {

	@Bean
	public Module objectMapperModule() {
		return CookbookPersistence.createModule(false);
	}

	public static void main(String[] args) {
		SpringApplication.run(CookbookApplication.class, args);
	}

}
