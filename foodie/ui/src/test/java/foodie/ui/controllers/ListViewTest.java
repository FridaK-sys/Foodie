package foodie.ui.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import foodie.ui.CookbookApp;

public class ListViewTest extends AbstractAppTest {

  private ListViewController controller;

  /**
   * For running the test headless
   */
  @BeforeAll
  public static void setupHeadless() {
    CookbookApp.supportHeadless();
  }

  @Override
  public void start(final Stage stage) throws Exception {
    URL fxmlLocation = getClass().getResource("ListView_test.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
    Parent root = fxmlLoader.load();
    Scene scene = new Scene(root);
    this.controller = fxmlLoader.getController();
    stage.setScene(scene);
    stage.show();
    
  }

  @BeforeEach
  public void setupItems() {
    setTestData();
    this.controller.setRecipes(cookbook);
  }

  @Test
  public void testController() {
    assertNotNull(this.controller);
    testRecipes(cookbook.getRecipes(), recipe1, recipe2, recipe3);
  }

  @Test
  public void testToggleButtons() {
    clickOn("#breakfast");
    checkRecipesListViewItems(recipe2);
    clickOn("#all");
    checkRecipesListViewItems(recipe1, recipe2, recipe3);
    clickOn("#fav");
    checkRecipesListViewItems(recipe3);
    clickOn("#breakfast");
    checkRecipesListViewItems();
    clickOn("#fav");
    clickOn("#lunch");
    checkRecipesListViewItems();
    clickOn("#dinner");
    checkRecipesListViewItems(recipe3);
    clickOn("#all");
    checkRecipesListViewItems(recipe1, recipe2, recipe3);
  }

}
