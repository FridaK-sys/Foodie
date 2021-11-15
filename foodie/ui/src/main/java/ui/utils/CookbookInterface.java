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
   * Adds the recipe.
   *
   * @return true if it was added, false if it replaced
   */

  boolean addRecipe(Recipe recipe);

  /**
   * Removes the Recipe.
   *
   * @param name the name of the Recipe
   */
  boolean deleteRecipe(String name);

}
