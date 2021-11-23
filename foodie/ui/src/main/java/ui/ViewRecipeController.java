package ui;

import core.Ingredient;
import core.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import ui.utils.CookbookAccess;

/**
 * Loads the scene that displays a single recipe. Ability to set favorite and open recipe editor.
 */
public class ViewRecipeController extends AbstractController {

  private ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
  private int portion;
  // private CookbookAccess dataAccess = AbstractController.getAccess();
  private Recipe viewRecipe;

  // private Stage stage;

  @FXML
  private Label recipeTitle;

  @FXML
  private Label labelTag;

  @FXML
  private Label portions;

  @FXML
  private ListView<Ingredient> ingredientsListView;

  @FXML
  private TextArea textField;

  @FXML
  private Button faveButton;

  @FXML
  private Button backButton;

  /**
   * Sets or removes favorite for Recipe.
   */
  public void favoriseRecipeButton(ActionEvent ae) {
    if (viewRecipe.getFav() == true) {
      viewRecipe.setFav(false);
      faveButton.setText("Add to favorite");
    } else {
      viewRecipe.setFav(true);
      faveButton.setText("Remove from favorite");
    }
    dataAccess.editRecipe(viewRecipe.getName(), viewRecipe);

  }

  /**
   * Increments portion size in IngredientListView.
   */
  public void incPortion(ActionEvent event) {
    if (this.portion > 0) {
      alterPortions(portion + 1);
    }
  }

  /**
   * Decreases portions size in IngredientListView.
   */
  public void decPortion(ActionEvent event) {
    if (this.portion != 1 && portion != 0) {
      alterPortions(portion - 1);
    }
  }

  /**
   * Alters portions and updated IngredientListView.
   *
   * @param portionSize new portion size
   */
  public void alterPortions(int portionSize) {
    viewRecipe.setPortions(portionSize);
    ingredients.setAll(viewRecipe.getIngredients());
    portions.setText(Integer.toString(portionSize));
    this.portion = portionSize;
  }

  public void initialize(URL location, ResourceBundle resources) {
    ingredientsListView.setItems(ingredients);
    
  }

  /**
   * Loads new RecipeController with selected recipe for editing and sets page to edit recipe.
   *
   * @param ae
   * @throws IOException if failed or interrupted I/O operations
   */
  public void changeSceneToEditRecipe(ActionEvent ae) throws IOException {
    FxmlModel model = SceneHandler.getScenes().get(SceneName.NEWRECIPE);
    Scene scene = model.getScene();
    NewRecipeController controller = (NewRecipeController) model.getController();
    controller.setSelectedRecipe(selectedRecipe);
    controller.setCookbookAccess(dataAccess);
    // controller.setBackButtonTarget(SceneHandler.getScenes().get(SceneName.VIEWRECIPE));
    controller.update();
    stage.setScene(scene);

  }

  @FXML
  private void handleBackbutton() {
    FxmlModel model = SceneHandler.getScenes().get(SceneName.MAIN);
    Scene scene = model.getScene();
    model.getController().update();
    stage.setScene(scene);
  }

  public void initData(Recipe recipe) {
    this.viewRecipe = recipe;
    this.portion = recipe.getPortions();
    if (recipe.getName() != null) {
      recipeTitle.setText(recipe.getName());
    } else {
      recipeTitle.setText("oppskrift");
    }
    portions.setText(Integer.toString(recipe.getPortions()));
    if (!recipe.getIngredients().isEmpty()) {
      ingredients.clear();
      ingredients.addAll(recipe.getIngredients());
    }
    if (!(recipe.getDescription().isEmpty() || recipe.getDescription().isBlank())) {
      textField.setText(recipe.getDescription());
    }
    if (recipe.getFav() == true) {
      faveButton.setText("Remove from favorite");
    } else {
      faveButton.setText("Add to favorite");
    }
    if (!recipe.getLabel().isBlank()) {
      labelTag.setText(recipe.getLabel());
    } else {
      labelTag.setText("");
    }

  }


  /**
   * Updates page when switching back to scene.
   */
  @Override
  public void update() {
    System.out.println(selectedRecipe.toString());
    initData(this.selectedRecipe);
  }

  @Override
  public void setStage(Stage stage) {
    this.stage = stage;
  }

  @Override
  protected void setUpStorage() {
    
  }

  public void setDataAccess(CookbookAccess dataAccess){
    this.dataAccess = dataAccess;
  } 

}
