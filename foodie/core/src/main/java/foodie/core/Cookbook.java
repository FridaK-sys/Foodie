package foodie.core;

import java.util.ArrayList;
import java.util.List;

/**
 * A cookbook containing a list of recipes.
 */

public class Cookbook {

  private List<Recipe> recipes = new ArrayList<>();

  /**
   * Constructor for a cookbook with list of recipes.
   *
   * @param recipes list of recipes
   */

  public Cookbook(List<Recipe> recipes) {
    this.recipes = new ArrayList<>(recipes);
  }

  /**
   * Constructor for an empty cookbook.
   */

  public Cookbook() {
    this.recipes = new ArrayList<>();
  }

  /**
   * Sets name of cookbook.
   *
   * @param name name of cookbook
   * 
   * @throws IllegalArgumentException if param contains other characters than letters and numbers.
   */


  public List<Recipe> getRecipes() {
    return new ArrayList<>(recipes);
  }

  /**
   * Adds recipe to cookbook.
   *
   * @param recipe recipe to add
   * 
   * @throws IllegalArgumentException if list already contains recipe
   */

  public void addRecipe(Recipe recipe) {
    if (!recipes.contains(recipe)) {
      recipes.add(recipe);
    }
  }

  /**
   * Adds recipe to cookbook at certain index
   *
   * @param recipe recipe to add
   * 
   * @param index index of where to put recipe in recipeList
   *
   * @throws IllegalArgumentException if list already contains recipe
   */

  public void addRecipe(int index, Recipe recipe) {
    if (!recipes.contains(recipe)) {
      recipes.add(index, recipe);
    }
  }

  /**
   * Removes recipe from recipeList.
   *
   * @param index index in recipeList of recipe to remove
   * 
   */
  public void removeRecipe(int index) {
    recipes.remove(index);
  }

  /**
   * Removes recipe from recipeList.
   *
   * @param name name of recipe to remove
   * 
   */
  public void removeRecipe(String name) {
    Recipe res = recipes.stream().filter(r -> r.getName().equals(name)).findAny()
        .orElseThrow(() -> new IllegalArgumentException("No recipe with the name " + name));
    recipes.remove(res);
  }

  /**
   * Replaces a recipe in list with param.
   *
   * @param name   name of recipe to remove
   * 
   * @param recipe recipe to put in
   * 
   */
  public void replaceRecipe(String name, Recipe recipe) {
    Recipe res = recipes.stream().filter(r -> r.getName().equals(name)).findAny()
        .orElseThrow(() -> new IllegalArgumentException("No recipe with the name " + name));
    int index = this.getRecipes().indexOf(res);
    this.removeRecipe(name);
    this.addRecipe(index, recipe);
  }

  /**
   * Makes list of all recipes in recipeList with fav = true.
   *
   * @return list of recipes in recipeList with fav = true
   * 
   */
  public List<Recipe> getFavRecipes() {
    return recipes.stream().filter(r -> r.getFav() == true).toList();
  }

  /**
   * Checks if recipe is in cookbook based on name of recipe.
   *
   * @param recipeName name of recipe
   * 
   * @return true if recipe is in RecipeList, false if not.
   * 
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
   * Makes list of all recipes in recipeList with label.
   *
   * @param label recipelabel
   *
   * @return list of all recipes in recipeList with label = param
   *
   * @throws IllegalArgumentException if param is not valid
   * 
   */
  public List<Recipe> getRecipesWithLabel(String label) {
    if (!Recipe.labels.contains(label)) {
      throw new IllegalArgumentException("Label is not valid");
    }
    return recipes.stream().filter(r -> r.getLabel().equals(label)).toList();
  }

  /**
   * Sets new recipes in cookbook.
   *
   * @param recipes recipes to add
   *
   * 
   */
  public void setRecipes(List<Recipe> recipes) {
    this.recipes = recipes;
  }

  public String toString() {
    return getRecipes().toString();
  }
}
