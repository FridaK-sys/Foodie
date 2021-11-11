package core;

import java.util.ArrayList;
import java.util.List;

/**
 * A cookbook containing a name and list of recipes
 */

public class Cookbook {

  private String name;
  private List<Recipe> recipes = new ArrayList<>();

  /**
   * Constructor for cookbook with name and list of recipes
   * 
   * @param name
   * @param recipes
   */
  public Cookbook(String name, List<Recipe> recipes) {
    setName(name);
    this.recipes = new ArrayList<>(recipes);
  }

  /**
   * Constructor for empty cookbook
   */
  public Cookbook() {
    this.name = "Ny kokebok";
  }

  public String getName() {
    return name;
  }

  /**
   * Sets name if param consists of letters and numbers
   * 
   * @param name
   * @throws IllegalArgumentException if param contains other characters than
   *                                  letters and numbers
   */
  public void setName(String name) {
    if (!name.matches("^[ÆØÅæøåa-zA-Z0-9\\s]$")) {
      throw new IllegalArgumentException("Invalid name");
    }
    this.name = name;
  }

  public List<Recipe> getRecipes() {
    return new ArrayList<>(recipes);
  }

  /**
   * Adds recipe to recipeList if it is not already there
   * 
   * @param recipe
   * @throws IllegalArgumentException if list already contains recipe
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
   * @return true if recipe is in RecipeList, false if not
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
    if (!Recipe.allowedLabels.contains(label)) {
      throw new IllegalArgumentException("Label is not valid");
    }
    return recipes.stream().filter(r -> r.getLabel().equals(label)).toList();
  }

  public String toString() {
    return getName();
  }
}
