package ui;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;
import json.CookbookModule;
import json.CookbookPersistence;
import ui.utils.LocalCookbookAccess;

public class LocalCookbookAccessTest {
  private LocalCookbookAccess localCookbook;
  private CookbookPersistence persistence;
  private static String defaultCookbookPath;
  private static ObjectMapper mapper;

  private Cookbook createDefaultCookbook() {
    Recipe r1 = new Recipe("Cake", 1);
    r1.setDescription("Recipe for cake");
    r1.setLabel("Breakfast");
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

  @BeforeEach
  void setup() throws IllegalStateException, IOException {
    localCookbook = new LocalCookbookAccess("default-cookbook.json");
    persistence = new CookbookPersistence();
    persistence.loadCookbook();

    // defaultCookbookPath = System.getProperty("user.dir") + File.separator
    // + ("/src/test/java/resources/test-cookbook.json");
    // mapper = new ObjectMapper().registerModule(new CookbookModule());

  }

  @Test
  void getCookbook() {
    assertEquals(createDefaultCookbook(), localCookbook.getCookbook());
  }

  // @Test
  // void addRecipe() {
  // assertTrue(localCookbook.addRecipe(new Recipe("Coffee", 1)));

  // }
}
