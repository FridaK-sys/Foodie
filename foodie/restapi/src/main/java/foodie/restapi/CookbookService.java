package foodie.restapi;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import json.CookbookPersistence;
import org.springframework.stereotype.Service;

/**
 * Class for handling the business logic.
 */
@Service
public class CookbookService {

  private Cookbook cookbook;
  private CookbookPersistence cookbookPersistence;
  public static final String COOKBOOK_SERVICE_PATH = "/cookbook";

  /**
   * Initializes the service with a new cookbookPersistence and loads cookbook from
   * COOKBOOK_SERVICE_PATH.
   */
  public CookbookService() throws IllegalStateException, IOException {
    this.cookbookPersistence = new CookbookPersistence();
    cookbookPersistence.setSaveFile(COOKBOOK_SERVICE_PATH);
    this.cookbook = cookbookPersistence.loadCookbook();
  }

  public CookbookPersistence getPersistence() {
    return this.cookbookPersistence;
  }

  public Cookbook getCookbook() {
    return this.cookbook;
  }

  public void setCookbook(Cookbook cookbook) {
    this.cookbook = cookbook;
  }

  /**
   * Creates a default cookbook. Often used for testing.
   */

  public static Cookbook createDefaultCookbook() {
    CookbookPersistence cookbookPersistence = new CookbookPersistence();
    try (Reader reader =
        new FileReader(new File(System.getProperty("user.dir") + File.separator + ("default-cookbook.json")),
            StandardCharsets.UTF_8)) {
      return cookbookPersistence.readCookbook(reader);
    } catch (IOException e) {
      System.out.println("Couldn't read default-cookbook.json, so makes cookbook manually (" + e + ")");
    }
    Recipe r1 = new Recipe("Cake", 1);
    r1.setDescription("Recipe for cake");
    r1.setLabel("breakfast");
    r1.addIngredient(new Ingredient("Flour", 200.0, "g"));
    r1.addIngredient(new Ingredient("Egg", 2.0, "stk"));
    Recipe r2 = new Recipe("Hot chocolate", 1);
    r2.setDescription("Good dessert");
    r2.addIngredient(new Ingredient("Sugar", 1.5, "dl"));
    r2.addIngredient(new Ingredient("Cocoa", 1.0, "dl"));
    Cookbook cookbook = new Cookbook();
    cookbook.addRecipe(r1);
    cookbook.addRecipe(r2);
    return cookbook;
  }

  /**
   * Saves cookbook to file.
   */
  public void autoSaveCookbook() {
    if (cookbookPersistence != null) {
      try {
        cookbookPersistence.saveCookbook(this.cookbook);
      } catch (IllegalStateException | IOException e) {
        System.err.println("Couldn't auto-save cookbook: " + e);
      }
    }
  }

  /**
   * Adds recipe and saves cookbook.
   *
   * @param recipe recipe to add
   */
  public boolean addRecipe(Recipe recipe) {
    cookbook.addRecipe(recipe);
    autoSaveCookbook();
    return true;
  }

  /**
   * Removes recipe and saves cookbook.
   *
   * @param name name of recipe to remove
   */
  public boolean removeRecipe(String name) {
    cookbook.removeRecipe(name);
    autoSaveCookbook();
    return true;
  }

  /**
   * Edits recipe and saves cookbook.
   *
   * @param name name of recipe to edit
   * @param recipe edited recipe
   */
  public boolean editRecipe(String name, Recipe recipe) {
    cookbook.replaceRecipe(name, recipe);
    autoSaveCookbook();
    return true;
  }

}
