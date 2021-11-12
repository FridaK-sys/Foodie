package core;

import java.util.ArrayList;
import java.util.List;

/**
 * A cookbook containing a name and list of recipes.
 */

public class Cookbook {

  private String name;
  private List<Recipe> recipes = new ArrayList<>();

  /**
   * Constructor for a cookbook with name and list of recipes.
   * 
   * @param name
   * @param recipes
   * 
   */

  public Cookbook(String name, List<Recipe> recipes) {
    setName(name);
    this.recipes = new ArrayList<>(recipes);
  }

  /**
   * Constructor for an empty cookbook.
   */
  public Cookbook() {
    this.name = "Cookbook";
  }

  public String getName() {
    return name;
  }

  /**
   * Sets name of cookbook.
   * 
   * @param name
   * @throws IllegalArgumentException if param contains other characters than
   *                                  letters and numbers.
   */
  public void setName(String name) {
    if (!name.matches("^[ÆØÅæøåa-zA-Z0-9\\s]+$")) {
      throw new IllegalArgumentException("Invalid name");
    }
    this.name = name;
  }

  public List<Recipe> getRecipes() {
    return new ArrayList<>(recipes);
  }

  /**
   * Adds recipe to cookbook.
   * 
   * @param recipe
   * @throws IllegalArgumentException if list already contains recipe
   * 
   */
  public void addRecipe(Recipe recipe) {
    if (!recipes.contains(recipe)) {
      recipes.add(recipe);
    }
  }

  /**
   * Removes recipe from recipeList
   * 
   * @param index
   */
  public void removeRecipe(int index) {
    recipes.remove(index);
  }

  /**
   * Removes recipe from recipeList
   * 
   * @param iname
   */
  public void removeRecipe(String name) {
    Recipe res = recipes.stream().filter(r -> r.getName().equals(name)).findAny()
        .orElseThrow(() -> new IllegalArgumentException("No recipe with name " + name));
    recipes.remove(res);
  }

  /**
   * Makes list of all recipes in recipeList with fav = true
   * 
   * @return list of recipes in recipeList with fav = true
   */
  public List<Recipe> getFavRecipes() {
    return recipes.stream().filter(r -> r.getFav() == true).toList();
  }

  /**
   * Checks if recipe is in cookbook based on name of recipe
   * 
   * @param recipeName
   * @return true if recipe is in RecipeList, false if not.
   */
  public boolean isInCookbook(String recipeName) {
    for (Recipe r : recipes) {
      if (r.getName().equals(recipeName)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Makes list of all recipes in recipeList with label = param
   * 
   * @param label
   * @throws IllegalArgumentException if param is not valid
   * @return list of all recipes in recipeList with label = param
   */
  public List<Recipe> getRecipesWithLabel(String label) {
    if (!Recipe.labels.contains(label)) {
      throw new IllegalArgumentException("Label is not valid");
    }
    return recipes.stream().filter(r -> r.getLabel().equals(label)).toList();
  }

  public String toString() {
    return getName();
  }
}
