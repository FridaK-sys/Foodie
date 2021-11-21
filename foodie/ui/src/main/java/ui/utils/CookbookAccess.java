package ui.utils;

import core.Cookbook;
import core.Recipe;
import java.util.List;


/**
 * Interface for local and remote CookbookAccess.
 */
public interface CookbookAccess {

  /**
   * Gets recipe.
   *
   * @return the cookbook
   */

  Cookbook getCookbook();

  /**
   * Edit the recipe.
   *
   * @param name of the recipe to edit
   * @param recipe the updated recipe
   *
   * @return true if it was edited
   */

  boolean editRecipe(String name, Recipe recipe);

  /**
   * Adds the recipe.
   *
   * @param recipe the recipe to add
   *
   * @return true if it was added
   */

  boolean addRecipe(Recipe recipe);

  /**
   * Removes the Recipe.
   * 
   *
   * @param name the name of the Recipe to delete
   * @return true if deleted
   * 
   */
  boolean deleteRecipe(String name);

  /**
   * Sets the cookbook
   * 
   *
   * @param cookbook the new cookbook
   * @return true if set
   * 
   */
  boolean setRecipes(List<Recipe> recipes);
}
