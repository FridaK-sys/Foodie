package restapi;

import org.springframework.stereotype.Service;
import core.Recipe;
import core.Cookbook;

@Service
public class RecipeService {

  private Recipe recipe;
  private Cookbook cookbook;

  public RecipeService(Recipe recipe) {
    this.recipe = recipe;
  }

  public Recipe getRecipe() {
    return this.recipe;
  }

  public Cookbook getCookBook() {
    return this.cookbook;

  }
}
