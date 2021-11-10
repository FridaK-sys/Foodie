package restapi;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import core.Recipe;
import core.Ingredient;

public class LoadDatabase {
  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
  // private Ingredient ingredient1 = new Ingredient("Hvetemel", 2, "dl");
  // private Ingredient ingredient2 = new Ingredient("Tomatsaus", 1, "dl");
  // private List<Ingredient> ingrediens = new ArrayList<>();

  @Bean
  CommandLineRunner initDatabase(RecipeRepository repository) {
    // ingrediens.add(ingredient1); // må kanskje flyttes
    // ingrediens.add(ingredient2);

    return args -> {
      log.info("Preloading " + repository.save(new Recipe("Pizza", 2)));

    };
  }

}
