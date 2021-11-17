package ui.utils;

import core.Recipe;
import json.CookbookPersistence;

import java.io.IOException;

import core.Cookbook;

public class LocalCookbookAccess implements CookbookInterface {

  private final CookbookPersistence persistence;
  private final Cookbook cookbook;

  /**
   * Constructor for LocalCookbookAccess
   * 
   * Initializes persistence and cookbook
   * 
   * @param path the path that is convertet to a URI
   * 
   * 
   */

  public LocalCookbookAccess(String path) {
    persistence = new CookbookPersistence();
    persistence.setSaveFile(path);
    cookbook = getCookbook();
  }

  /**
   * Gets Cookbook
   *
   * @return the cookbook
   * 
   * @return null if IllegalStateException or IOException occured
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
   * Edits recipe
   *
   * @param name   the name of the recipe to be removed
   * @param recipe the edited recipe that is added
   * 
   * @return true if edited
   * 
   * 
   */

  @Override
  public boolean editRecipe(String name, Recipe recipe) {
    cookbook.removeRecipe(name);
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
   * Adds recipe
   * 
   * @param recipe the recipe that is added
   *
   * @return true if added
   * 
   * 
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
   * Deletes recipe
   *
   * @param name the name of the recipe that is deleted
   * 
   * @return true if edited
   * 
   * 
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

}
