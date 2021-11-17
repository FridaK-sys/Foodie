package ui;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.framework.junit5.ApplicationTest;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import json.CookbookPersistence;
import ui.utils.LocalCookbookAccess;

public class ListViewTest extends ApplicationTest{
  @BeforeAll
  public static void setupHeadless() {
    CookbookApp.supportHeadless();
  }

  private ListViewController controller;

  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("TodoList_test.fxml"));
    final Parent root = loader.load();
    this.controller = loader.getController();
    LocalCookbookAccess dataAccess = new LocalCookbookAccess("test-todomodel.json");
    this.controller.setCookbookAccess(dataAccess);    
    stage.setScene(new Scene(root));
    stage.show();
  }

  private CookbookPersistence cookbookPersistence = new CookbookPersistence();
  private Recipe recipe;
  private Ingredient ing1, ing2, ing3;

  @BeforeEach
  public void setupItems() {
    Cookbook cookbook = null;
    try {
      cookbook = cookbookPersistence.readCookbook(new InputStreamReader(getClass().getResourceAsStream("test-cokbook.json")));
    } catch (IOException e) {
      fail("Couldn't load test-cookbook.json");
    }
    assertNotNull(cookbook);
    this.controller.setRecipes(cookbook);
    assertNotNull(cookbook.getRecipes().get(0));
    this.recipe = cookbook.getRecipes().get(0);
    assertEquals(3, recipe.getIngredients().size());
    ing1 = recipe.getIngredients().get(0);
    ing2 = recipe.getIngredients().get(1);
    ing3 = recipe.getIngredients().get(2);
  }

  @Test
  public void testController_todoList() {
    assertNotNull(this.controller);
    assertNotNull(this.recipe);
    // initial todo items
    testRecipeIngredients(item1, item2, item3);
  }
  
}
