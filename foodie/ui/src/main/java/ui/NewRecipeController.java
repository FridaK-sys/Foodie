package ui;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ui.utils.CookbookAccess;

/**
 * Controller for page responsible for creating and editing recipes.
 */
public class NewRecipeController extends AbstractController {


  private Recipe newRecipe;
  private Cookbook cookbook = new Cookbook();
  private ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
  private String label = "";
  private boolean editing = false;
  // private int index;
  private String recipeName;
  private Stage stage;

  private CookbookAccess dataAccess = CookbookApp.getAccess();

  @FXML
  private TextField ingredientTitle;

  @FXML
  private TextField ingredientAmount;

  @FXML
  private TextField ingredientUnit;

  @FXML
  private TextField recipePortions;

  @FXML
  private TextField recipeTitle;

  @FXML
  private ListView<Ingredient> ingredientListView;

  @FXML
  private TextArea recipeDescription;

  @FXML
  private Label errorMessageLabel;

  @FXML
  private Button backButton;
  @FXML
  private Button breakfastTag;
  @FXML
  private Button lunchTag;
  @FXML
  private Button dinnerTag;

  @FXML
  private Button saveRecipeButton;
  @FXML
  private Button deleteRecipeButton;
  @FXML
  private Button createRecipeButton;

  @FXML
  private HBox hb;

  /**
   * Adds ingredient to recipe.
   *
   * @param ae when save ingredient-button is clicked
   * 
   * @throws IllegalArgumentException if ingredient-title is not set
   */
  public void addIngredientButton(ActionEvent ae) {
    try {
      if (ingredientAmount.getText() != null && !ingredientAmount.getText().isEmpty()) {
        Ingredient newIngredient = new Ingredient(ingredientTitle.getText(),
            (Double.parseDouble(ingredientAmount.getText())), (ingredientUnit.getText()));
        ingredients.add(newIngredient);
      } else {
        Ingredient newIngredient = new Ingredient(ingredientTitle.getText());
        ingredients.add(newIngredient);
      }

      ingredientAmount.clear();
      ingredientTitle.clear();
      ingredientUnit.clear();

    } catch (NumberFormatException e) {
      errorMessageLabel.setText("Invalid input: ingredient amount must be a number");
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      errorMessageLabel.setText("Invalid Ingredient name");
      e.printStackTrace();
    } catch (NullPointerException e) {
      errorMessageLabel.setText("The ingredient needs a title");
      e.printStackTrace();
    }
  }

  @FXML
  public void handleDeleteIngredient(){
    Ingredient ing = ingredientListView.getSelectionModel().getSelectedItem();
    if (ing != null){
      ingredients.remove(ing);
    }
  }

  @FXML
  public void handleEditIngredient(){
    Ingredient ing = ingredientListView.getSelectionModel().getSelectedItem();
    if (ing != null) {
      ingredients.remove(ing);
      ingredientAmount.setText(Double.toString(ing.getAmount()));
      ingredientTitle.setText(ing.getName());
      ingredientUnit.setText(ing.getUnit());
    }
  }

  /**
   * Creates a new recipe when create new recipe-button is pushed and saves it.
   *
   * @param ae when create new recipe-button is pushed
   * 
   */
  public void createRecipeButtonPushed(ActionEvent ae) throws IOException {
    try {
      System.out.println(dataAccess.toString());
      dataAccess.addRecipe(createRecipe());
      backButton.fire();

    } catch (Exception e) {
      errorMessageLabel.setText(e.getMessage());
      System.out.println(e.getMessage());
    }
  }

  /**
   * Saves edited recipe to server.
   */
  public void saveRecipe() {
    try {
      Recipe updatedRecipe = createRecipe();
      setSelectedRecipe(updatedRecipe);
      dataAccess.editRecipe(recipeName, updatedRecipe);
      // dataAccess.deleteRecipe(recipeName);
      // dataAccess.addRecipe(updatedRecipe);
      backButton.fire();

    } catch (Exception e) {
      errorMessageLabel.setText(e.getMessage());
      System.out.println(e.getMessage());

    }
  }

  /**
   * Creates edited recipe.
   */
  public Recipe createRecipe() {
    if (recipePortions.getText() == null || recipeTitle.getText().isBlank() || recipePortions
        .getText().isBlank()) {
      throw new IllegalArgumentException("Missing name or portion size");
    }
    if (!editing) {
      if ((this.cookbook).isInCookbook(recipeTitle.getText())) {
        (this.recipeTitle).setText("");
        throw new IllegalArgumentException("This recipe title already exists");
      }
    }
    try {
      this.newRecipe = new Recipe(recipeTitle.getText());
      if (!(recipeDescription.getText() == null)) {
        this.newRecipe.setDescription(recipeDescription.getText());
      }
      if (ingredients.isEmpty()) {
        throw new IllegalArgumentException("You have missing ingredients");
      }

      for (Ingredient i : ingredients) {
        this.newRecipe.addIngredient(i);
      }
      if (!this.label.isBlank()) {
        this.newRecipe.setLabel(this.label);
      }

      this.newRecipe.setDescription(recipeDescription.getText());

      return newRecipe;

    } catch (NullPointerException e) {
      throw new NullPointerException("You have empty fields");
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Ingredient amount must be a number");
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }

  }

  /**
   * Initialises data from another scene.
   *
   * @param recipe Recipe to initialize
   * @param cookbook Cookbook to initialize
   * 
   */
  public void initData(Recipe recipe) {
    this.recipeTitle.setText(recipe.getName());
    this.recipePortions.setText(String.valueOf(recipe.getPortions()));
    this.recipeName = recipe.getName();
    if (!recipe.getDescription().isEmpty()) {
      this.recipeDescription.setText(recipe.getDescription());
    }
    if (!recipe.getLabel().isEmpty()) {
      setLabel(recipe.getLabel());
    }
    ingredients.setAll(recipe.getIngredients());
    this.editing = true;
    // this.cookbook = cookbook;

    createRecipeButton.setVisible(false);
    saveRecipeButton.setVisible(true);
    deleteRecipeButton.setVisible(true);

  }

  public void clear() {
    clearTextFields();
    
    setLabel("");
    
    ingredients.clear();
    this.editing = false;
    

    createRecipeButton.setVisible(true);
    saveRecipeButton.setVisible(false);
    deleteRecipeButton.setVisible(true);

  }

  /**
   * Sets label to "breakfast" if breakfastTag is pushed.
   *
   * @param ae when breakfast tag is pushed
   */
  public void breakfastTagPushed(ActionEvent ae) {
    setLabel("breakfast");
  }

  public void lunchTagPushed(ActionEvent ae) {
    setLabel("lunch");
  }

  public void dinnerTagPushed(ActionEvent ae) {
    setLabel("dinner");
  }

  /**
   * Sets new label.
   *
   * @param label label to be set
   */
  public void setLabel(String label) {
    if (!this.label.equals(label)) {
      this.label = label;
      setLabelButton(label);
    } else {
      this.label = "";
      setLabelButton("blank");
    }
  }

  public void deleteRecipe(ActionEvent ea) {
    dataAccess.deleteRecipe(recipeTitle.getText());
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    setBackButtonTarget(CookbookApp.getScenes().get(SceneName.MAIN));
    clear();
    ingredientListView.setItems(ingredients);
    setLabelButton("blank");
    hb.setSpacing(20);
  }

  public List<Ingredient> getIngredients() {
    return new ArrayList<Ingredient>(ingredients);
  }

  /**
   * Sets the SceneTarget for return button.
   *
   * @param sceneTarget previous scene.
   */
  public void setBackButtonTarget(FxmlModel model) {
    backButton.setOnAction(ea -> {
      model.getController().update();
      stage.setScene(model.getScene());
    });
  }

  @Override
  public void update() {
    if (getSelectedrecipe() != null) {
      ingredientAmount.setText("");
      ingredientTitle.setText("");
      ingredientUnit.setText("");
      initData(selectedRecipe);
      setBackButtonTarget(CookbookApp.getScenes().get(SceneName.VIEWRECIPE));
    } else {
      clear();
      setBackButtonTarget(CookbookApp.getScenes().get(SceneName.MAIN));
    }

  }

  /**
   * Changes color based on which label is selected.
   *
   * @param label selected label
   */
  public void setLabelButton(String label) {
    if (label.equals("breakfast")) {
      breakfastTag.setStyle("-fx-text-fill: white; -fx-background-color: red;");
      dinnerTag.setStyle("-fx-background-color: white");
      lunchTag.setStyle("-fx-background-color: white");
    } else if (label.equals("lunch")) {
      lunchTag.setStyle("-fx-text-fill: white; -fx-background-color: red;");
      dinnerTag.setStyle("-fx-background-color: white");
      breakfastTag.setStyle("-fx-background-color: white");
    } else if (label.equals("dinner")) {
      dinnerTag.setStyle("-fx-text-fill: white; -fx-background-color: red;");
      breakfastTag.setStyle("-fx-background-color: white");
      lunchTag.setStyle("-fx-background-color: white");
    } else if (label.equals("blank")) {
      lunchTag.setStyle("-fx-background-color: white");
      breakfastTag.setStyle("-fx-background-color: white");
      dinnerTag.setStyle("-fx-background-color: white");

    }
  }

  public void clearTextFields(){
    ingredientAmount.clear();
    ingredientTitle.clear();
    ingredientUnit.clear();
    this.recipeTitle.clear();;
    this.recipePortions.clear();;
    this.recipeName = "";
    this.recipeDescription.clear();
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
