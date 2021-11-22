package ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.function.Predicate;
import core.Recipe;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
    // ListView<Recipe> recipessListView = lookup("#mainListView").query();
    // recipessListView.get
    // Predicate<ListViewCell> listCell = cell -> cell.lookup(".label") != null;

    // clickOn(listViewCell(listCell, 1));;
    checkRecipesListViewItems(recipe1, recipe2, recipe3);
  }

  private Node findNode(Predicate<Node> nodeTest, int num) {
    int count = 0;
    for (Node node : lookup(nodeTest).queryAll()) {
      if (nodeTest.test(node) && count++ == num) {
        return node;
      }
    }
    return null;
  }


  private ListViewCell listViewCell(Predicate<ListViewCell> test, int num) {
    return (ListViewCell) findNode(node -> node instanceof ListViewCell listCell && test.test(listCell), num);
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



  // @Test
  // public void testChangeSceneToNewRecipeButtonPushed() {
  //   clickOn("#newButton");
  // }

}
