package ui;


import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import core.Cookbook;
import core.Ingredient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ui.NewRecipeController;

public class NewRecipeControllerTest extends AbstractAppTest {

  private NewRecipeController controller;
  private Cookbook originalData;

  @FXML
  private TextField recipeTitle, recipePortions, ingredientTitle;

  @FXML
  private TextArea recipeDescription;

  @FXML
  private ListView<Ingredient> ingredientListView;

  @Override
  public void start(final Stage stage) throws Exception {
    SceneHandler.initializeTest(stage);
    URL fxmlLocation = getClass().getResource("NewRecipe_test.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
    Parent root = fxmlLoader.load();
    this.controller = fxmlLoader.getController();
    controller.setStage(stage);
    

    Scene scene = new Scene(root);

    stage.setScene(scene);
    stage.show();
  }

  @BeforeEach
  public void setupItems() {
    setTestData();
    this.controller.setDataAccess(dataAccess);
  }

  @Test
  public void testNewIngredient() {
    clickOn("#ingredientTitle").write("Eple");
    clickOn("#ingredientAmount").write("3");
    clickOn("#ingredientUnit");
    type(KeyCode.DOWN);
    type(KeyCode.DOWN);
    type(KeyCode.DOWN);
    type(KeyCode.DOWN);
    type(KeyCode.DOWN);
    type(KeyCode.ENTER);
    clickOn("#addIngredient");
    checkIngredient(controller.getIngredients().get(0), ing1);
  }


  @Test
  public void testNewRecipe() throws FileNotFoundException {
    this.controller.clear();
    clickOn("#recipeTitle").write("Eple");
    clickOn("#recipePortions").write("2");
    clickOn("#ingredientTitle").write("Eple");
    clickOn("#ingredientAmount").write("3");
    clickOn("#ingredientUnit");
    type(KeyCode.DOWN);
    type(KeyCode.DOWN);
    type(KeyCode.DOWN);
    type(KeyCode.DOWN);
    type(KeyCode.DOWN);
    type(KeyCode.ENTER);
    clickOn("#addIngredient");
    clickOn("#recipeDescription").write("Epler...");
    clickOn("#createRecipeButton");
    testRecipes(dataAccess.getCookbook().getRecipes(), recipe1, recipe2, recipe3, recipe4);
  }

  @Test
  public void testEditRecipe() {
    dataAccess.addRecipe(recipe4);
    this.controller.initData(recipe4);
    TextField nrecipeTitle = lookup("#recipeTitle").query();
    TextField portions = lookup("#recipePortions").query();

    assertEquals(recipe4.getName(), nrecipeTitle.getText(), "The displayed title did not match the expected output");
    assertEquals(recipe4.getPortions(), Integer.parseInt(portions.getText()),
        "The displayed portion-size did not match the expected output");
    ListView<Ingredient> ingListView = lookup("#ingredientListView").query();
    for (int i = 0; i < ingredients.size(); i++) {
      checkIngredient(ingListView.getItems().get(i), ingredients.get(i));
    }

    clickOn("#recipeDescription").write(" er godt!");
    clickOn("#saveRecipeButton");

    recipe4.setDescription("Epler... er godt!");
    testRecipes(dataAccess.getCookbook().getRecipes(), recipe1, recipe2, recipe3, this.recipe4);

  }

  @Test
  public void testDeleteButton() {
    SceneHandler.getScenes().get(SceneName.MAIN).getScene();
    dataAccess.addRecipe(recipe4);
    assertTrue(dataAccess.getCookbook().getRecipes().size() == 4);
    this.controller.setSelectedRecipe(recipe4);
    this.controller.update();
    clickOn("#deleteRecipeButton");
    testRecipes(dataAccess.getCookbook().getRecipes(), recipe1, recipe2, recipe3);
  }

}
