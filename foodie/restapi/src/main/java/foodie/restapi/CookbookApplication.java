package foodie.restapi;

import com.fasterxml.jackson.databind.module.SimpleModule;
import foodie.json.CookbookPersistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * CookbookApplication that runs SpringbootApplication.
 */
@SpringBootApplication
public class CookbookApplication {

  @Bean
  public SimpleModule objectMapperModule() {
    return CookbookPersistence.createModule();
  }

  public static void main(String[] args) {
    SpringApplication.run(CookbookApplication.class, args);
  }


}
