package ui.utils;

import core.Recipe;
import core.Cookbook;

public interface CookbookInterface {

  /**
   * Gets recipe.
   *
   * @return the recipe
   */

  Cookbook getCookbook();

  /**
   * Edit the recipe.
   *
   * @return true if it was edited
   */

  boolean editRecipe(String name, Recipe recipe);

  /**
   * Adds the recipe.
   *
   * @return true if it was added
   */

  boolean addRecipe(Recipe recipe);

  /**
   * Removes the Recipe.
   *
   * @param name the name of the Recipe
   */
  boolean deleteRecipe(String name);

}
