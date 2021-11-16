package ui.utils;

import core.Recipe;
import json.CookbookPersistence;

import java.io.IOException;

import core.Cookbook;

public class LocalCookbookAccess implements CookbookInterface {

  private final CookbookPersistence persistence;
  private final Cookbook cookbook;

  public LocalCookbookAccess(String path) {
    persistence = new CookbookPersistence();
    persistence.setSaveFile(path);
    cookbook = getCookbook();
  }

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
