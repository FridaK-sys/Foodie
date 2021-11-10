package restapi;

import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

  /*
   * // Aggregate root // tag::get-aggregate-root[]
   * 
   * @GetMapping("/recipes") CollectionModel<EntityModel<Recipe>> all() {
   * 
   * List<EntityModel<Recipe>> recipes = repository.findAll().stream() .map(recipe
   * -> EntityModel.of(recipe,
   * linkTo(methodOn(RecipeController.class).one(recipe.getId())).withSelfRel(),
   * linkTo(methodOn(RecipeController.class).all()).withRel("recipes")))
   * .collect(Collectors.toList());
   * 
   * return CollectionModel.of(recipes,
   * linkTo(methodOn(RecipeController.class).all()).withSelfRel()); } //
   * end::get-aggregate-root[]
   */
  @PostMapping("/recipes")
  Recipe newRecipe(@RequestBody Recipe newRecipe) {
    return repository.save(newRecipe);
  }

  // Single item

  @GetMapping("/recipes/{id}")
  Recipe one(@PathVariable Long id) {

    return repository.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
  }

  @PutMapping("/recipes/{id}")
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

  @DeleteMapping("/recipes/{id}")
  void deleteRecipe(@PathVariable Long id) {
    repository.deleteById(id);
  }
  /*
   * @GetMapping("/recipes/{id}") EntityModel<Recipe> one(@PathVariable Long id) {
   * 
   * Recipe recipe = repository.findById(id) // .orElseThrow(() -> new
   * RecipeNotFoundException(id));
   * 
   * return EntityModel.of(recipe, //
   * linkTo(methodOn(RecipeController.class).one(id)).withSelfRel(),
   * linkTo(methodOn(RecipeController.class).all()).withRel("recipes")); }
   */

}