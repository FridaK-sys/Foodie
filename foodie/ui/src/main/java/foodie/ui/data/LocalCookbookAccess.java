package foodie.ui.data;

import foodie.core.Cookbook;
import foodie.core.Recipe;
import foodie.json.CookbookPersistence;
import java.io.IOException;
import java.util.List;



/**
 * Sets a persistence storage file when using the application locally.
 */
public class LocalCookbookAccess implements CookbookAccess {

  private final CookbookPersistence persistence;
  private final Cookbook cookbook;

  /**
   * Constructor initializes persistence and cookbook.
   *
   * @param path the path that is converted to a URI
   */

  public LocalCookbookAccess(String path) {
    persistence = new CookbookPersistence();
    persistence.setSaveFile(path);
    cookbook = getCookbook();
  }

  /**
   * Gets Cookbook.
   *
   * @return the cookbook or null if IllegalStateException or IOException is thrown
   */
  @Override
  public Cookbook getCookbook() {
    try {
      return persistence.loadCookbook();
    } catch (IllegalStateException e) {
      e.printStackTrace();
      return null;

    } catch (IOException e) {
      e.printStackTrace();
      return null;

    }

  }

  /**
   * Edits recipe.
   *
   * @param name the name of the recipe to be removed
   * @param recipe the edited recipe that is added
   * @return true if edited
   */

  @Override
  public boolean editRecipe(String name, Recipe recipe) {
    cookbook.replaceRecipe(name, recipe);
    // cookbook.add
    try {
      persistence.saveCookbook(cookbook);
    } catch (IllegalStateException e) {

      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  /**
   * Adds recipe.
   *
   * @param recipe the recipe that is added
   * @return true if added
   */

  @Override
  public boolean addRecipe(Recipe recipe) {
    cookbook.addRecipe(recipe);
    try {
      persistence.saveCookbook(cookbook);
    } catch (IllegalStateException e) {

      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  /**
   * Deletes recipe.
   *
   * @param name the name of the recipe that is deleted
   * @return true if edited
   */

  @Override
  public boolean deleteRecipe(String name) {
    cookbook.removeRecipe(name);
    try {
      persistence.saveCookbook(cookbook);
    } catch (IllegalStateException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  /**
   * Sets a list of recipes.
   *
   * @param recipes list of recipes to be set 
   * @return true if edited
   */

  @Override
  public boolean setRecipes(List<Recipe> recipes) {
    cookbook.setRecipes(recipes);
    try {
      persistence.saveCookbook(cookbook);
    } catch (IllegalStateException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

}
