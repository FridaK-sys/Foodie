package ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ListViewTest extends AbstractAppTest {

  private ListViewController controller;
  

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
    // assertNotNull(cookbook);
  }

  @Test
  public void testController() {
    assertNotNull(this.controller);
    testRecipes(cookbook.getRecipes(), recipe1, recipe2, recipe3);
  }

  @Test
  public void testRecipeListView() {
    checkRecipesListViewItems(recipe1, recipe2, recipe3);
  }

  @Test
  public void testToggleButtons() {
    clickOn("#Breakfast");
    checkRecipesListViewItems(recipe2);
    clickOn("#All");
    checkRecipesListViewItems(recipe1, recipe2, recipe3);
    clickOn("#Fav");
    checkRecipesListViewItems(recipe3);
    clickOn("#Breakfast");
    checkRecipesListViewItems();
    clickOn("#Fav");
    clickOn("#Lunch");
    checkRecipesListViewItems();
    clickOn("#Dinner");
    checkRecipesListViewItems(recipe3);
    clickOn("#All");
    checkRecipesListViewItems(recipe1, recipe2, recipe3);
  }



  @Test
  public void testChangeSceneToNewRecipeButtonPushed(){
    clickOn("#newButton");
  }

}
