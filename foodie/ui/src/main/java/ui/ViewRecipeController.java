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
public class ViewRecipeController implements FoodieController, Initializable {

  private Recipe selectedRecipe;
  private ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
  private int portion;
  private int index;
  private SceneTarget sceneTarget;
  private CookbookAccess dataAccess;

  private Stage stage;

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
    if (selectedRecipe.getFav() == true) {
      selectedRecipe.setFav(false);
      faveButton.setText("Add to favorite");
    } else {
      selectedRecipe.setFav(true);
      faveButton.setText("Remove from favorite");
    }
    dataAccess.deleteRecipe(selectedRecipe.getName());
    dataAccess.addRecipe(selectedRecipe);

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
    selectedRecipe.setPortions(portionSize);
    ingredients.setAll(selectedRecipe.getIngredients());
    portions.setText(Integer.toString(portionSize));
    this.portion = portionSize;
  }

  public void initialize(URL location, ResourceBundle resources) {
    ingredientsListView.setItems(ingredients);
    
    // textField.setText("Hmm her var det tomt...");

  }

  /**
   * Passes information when switching scene. Updates page with selected params.
   *
   * @param recipe selected Recipe
   * @param index index for the selected Recipe in mainListView
   * @param scene scene target for main page
   * @param dataAccess access to Cookbook
   */
  public void initData(Recipe recipe, int index, SceneTarget scene, CookbookAccess dataAccess) {
    this.index = index;
    this.selectedRecipe = recipe;
    this.portion = selectedRecipe.getPortions();
    this.sceneTarget = scene;
    this.dataAccess = dataAccess;
    if (selectedRecipe.getName() != null) {
      recipeTitle.setText(selectedRecipe.getName());
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
    if (selectedRecipe.getFav() == true) {
      faveButton.setText("Remove from favorite");
    } else {
      faveButton.setText("Add to favorite");
    }
    if (!selectedRecipe.getLabel().isBlank()) {
      labelTag.setText(selectedRecipe.getLabel());
    }

  }

  public void initData(Recipe recipe) {
    this.selectedRecipe = recipe;
    this.portion = recipe.getPortions();
    if (recipe.getName() != null) {
      recipeTitle.setText(selectedRecipe.getName());
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
    if (selectedRecipe.getFav() == true) {
      faveButton.setText("Remove from favorite");
    } else {
      faveButton.setText("Add to favorite");
    }
    if (!selectedRecipe.getLabel().isBlank()) {
      labelTag.setText(selectedRecipe.getLabel());
    }

  }
  /**
   * Loads new RecipeController with selected recipe for editing and sets page to edit recipe.
   *
   * @param ae
   * @throws IOException if failed or interrupted I/O operations
   */
  public void changeToEditRecipe(ActionEvent ae) throws IOException {
    URL fxmlLocation = getClass().getResource("NewRecipe.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

    Parent root = fxmlLoader.load();
    Scene viewRecipesScene = new Scene(root);

    // viewRecipesScene.getStylesheets().add(getClass().getResource("MainStyle.css").toString());

    NewRecipeController controller = fxmlLoader.getController();
    controller.initData(selectedRecipe, index, dataAccess);
    controller.setBackButtonTarget(new SceneTarget(faveButton.getScene()));

    Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
    stage.setScene(viewRecipesScene);
    stage.show();
  }

  /**
   * Updates page when switching back to scene.
   */
  @Override
  public void update() {
    Recipe recipe = dataAccess.getCookbook().getRecipes().get(index);
    initData(recipe);
  }

  @FXML
  private void handleBackbutton(){
    FxmlModel model = CookbookApp.getScenes().get(SceneName.MAIN);
    model.getController().update();
    stage.setScene(model.getScene());
    
  }

  @Override
  public void setStage(Stage stage) {
    this.stage = stage;
    Recipe temptrecipe = null;
    temptrecipe = CookbookApp.getScenes().get(SceneName.VIEWRECIPE).getRecipe();
    initData(temptrecipe);
  }

}
