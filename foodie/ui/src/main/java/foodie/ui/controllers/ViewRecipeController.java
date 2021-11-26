package foodie.ui.controllers;


import foodie.core.Ingredient;
import foodie.core.Recipe;
import foodie.ui.FxmlModel;
import foodie.ui.SceneHandler;
import foodie.ui.SceneName;
import foodie.ui.storage.CookbookAccess;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * Loads the scene that displays a single recipe. Ability to set favorite and open recipe editor.
 */
public class ViewRecipeController extends AbstractController {

  private ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
  private int portion;
  private Recipe viewRecipe;


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

  @FXML
  private Button decreaseButton;

  @FXML
  private Button increaseButton;

  @FXML
  private Label ingredientsLabel;

  @FXML
  private Label portionsneeded;

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

  /**
   * Initializes controller and sets the CellFactory for listview.
   */
  public void initialize(URL location, ResourceBundle resources) {
    ingredientsListView.setCellFactory(listView -> {
      IngredientListCell listCell = new IngredientListCell();
      return listCell;
    });
    ingredientsListView.getSelectionModel();
    ingredientsListView.setItems(ingredients);
  }

  /**
   * Loads new RecipeController with selected recipe for editing and sets page to edit recipe.
   *
   * @param ae ActionEvent
   * @throws IOException if failed or interrupted I/O operations
   */
  public void changeSceneToEditRecipe(ActionEvent ae) {
    FxmlModel model = SceneHandler.getScenes().get(SceneName.NEWRECIPE);
    changeScene(model);
  }

  @FXML
  public void handleBackbutton() {
    FxmlModel model = SceneHandler.getScenes().get(SceneName.MAIN);
    changeScene(model);
  }

  /**
   * Populates fields and lists with information from selected recipe.
   *
   * @param recipe the recipe to display
   */
  public void initData(Recipe recipe) {
    this.viewRecipe = recipe;
    this.portion = recipe.getPortions();
    if (recipe.getName() != null) {
      recipeTitle.setText(recipe.getName());
    } else {
      recipeTitle.setText("oppskrift");
    }
    if (recipe.getPortions() == 0) {
      portions.setText("0");
      portionsneeded.setVisible(false);
      portions.setVisible(false);
      increaseButton.setVisible(false);
      decreaseButton.setVisible(false);
    } else {
      portions.setVisible(true);
      portions.setText(Integer.toString(recipe.getPortions()));
      portionsneeded.setVisible(true);
      increaseButton.setVisible(true);
      decreaseButton.setVisible(true);
    }
    if (!recipe.getIngredients().isEmpty()) {
      ingredients.setAll(recipe.getIngredients());
    } else {
      ingredients.clear();
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
    initData(getSelectedrecipe());
  }

  public void setDataAccess(CookbookAccess dataAccess) {
    this.dataAccess = dataAccess;
  } 
  

}
