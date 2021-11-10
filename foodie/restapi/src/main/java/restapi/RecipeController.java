package restapi;

import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import core.Recipe;

@RestController
public class RecipeController {

  private final RecipeRepository repository;

  RecipeController(RecipeRepository repository) {
    this.repository = repository;
  }

  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/Recipes")
  List<Recipe> all() {
    return repository.findAll(); // fiks
  }
  // end::get-aggregate-root[]

  @PostMapping("/Recipes")
  Recipe newRecipe(@RequestBody Recipe newRecipe) {
    return repository.save(newRecipe);
  }

  // Single item

  @GetMapping("/Recipes/{id}")
  Recipe one(@PathVariable Long id) {

    return repository.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
  }

  @PutMapping("/Recipes/{id}")
  Recipe replaceRecipe(@RequestBody Recipe newRecipe, @PathVariable Long id) {

    return repository.findById(id).map(recipe -> {
      recipe.setName(newRecipe.getName());
      recipe.setPortions(newRecipe.getPortions());
      return repository.save(recipe);
    }).orElseGet(() -> {
      newRecipe.setId(id);
      return repository.save(newRecipe);
    });
  }

  @DeleteMapping("/Recipes/{id}")
  void deleteRecipe(@PathVariable Long id) {
    repository.deleteById(id);
  }

}