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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import ui.utils.CookbookAccess;

/**
 * Controller for page responsible for creating and editing recipes.
 */
public class NewRecipeController implements Initializable {

  private Recipe newRecipe;
  private Cookbook cookbook = new Cookbook();
  private ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
  private String label = "";
  private boolean editing = false;
  // private int index;
  private String recipeName;

  private CookbookAccess dataAccess;

  @FXML
  private TextField ingredientTitle; 
  private TextField ingredientAmount;
  private TextField ingredientUnit;
  private TextField recipePortions;
  private TextField recipeTitle;
  
  @FXML
  private ListView<Ingredient> ingredientListView;

  @FXML
  private TextArea recipeDescription;

  @FXML
  private Label errorMessageLabel;

  @FXML
  private Button backButton;
  private Button breakfastTag; 
  private Button lunchTag;
  private Button dinnerTag;

  @FXML
  private Button saveRecipeButton;
  private Button deleteRecipeButton;
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
      if (ingredientTitle.getText().isBlank()) {
        throw new IllegalArgumentException("Missing a title here...");
      }
      if (ingredientAmount.getText() != null && !ingredientAmount.getText().isEmpty()) {
        Ingredient newIngredient = new Ingredient(ingredientTitle.getText(),
            (Double.parseDouble(ingredientAmount.getText())), (ingredientUnit.getText()));
        ingredients.add(newIngredient);
      } else {
        Ingredient newIngredient = new Ingredient(ingredientTitle.getText());
        ingredients.add(newIngredient);
      }

      ingredientAmount.setText(null);
      ingredientTitle.setText(null);
      ingredientUnit.setText(null);

    } catch (NumberFormatException e) {
      errorMessageLabel.setText("Invalid input: ingredient amount must be a number");
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      errorMessageLabel.setText(e.getMessage());
      e.printStackTrace();
    } catch (NullPointerException e) {
      errorMessageLabel.setText("The ingredient needs a title");
      e.printStackTrace();
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
      dataAccess.deleteRecipe(recipeName);
      dataAccess.addRecipe(updatedRecipe);
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
    if (recipePortions.getText() == null || recipeTitle.getText().isBlank() || recipePortions.getText().isBlank()) {
      throw new IllegalArgumentException("Missing name or portion size");
    }
    if (!editing) {
      if ((this.cookbook).isInCookbook(recipeTitle.getText())) {
        throw new IllegalArgumentException("This recipe title already exists");
      }
    }
    try {
      this.newRecipe = new Recipe(recipeTitle.getText(), Integer.parseInt(recipePortions.getText()));
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
   * @param recipe
   * @param recipeIndex
   * @param dataAccess
   */
  public void initData(Recipe recipe, int recipeIndex, CookbookAccess dataAccess) {
    this.recipeTitle.setText(recipe.getName());
    this.recipePortions.setText(String.valueOf(recipe.getPortions()));
    this.dataAccess = dataAccess;
    this.recipeName = recipe.getName();
    if (!recipe.getDescription().isEmpty()) {
      this.recipeDescription.setText(recipe.getDescription());
    }
    if (!recipe.getLabel().isEmpty()) {
      setLabel(recipe.getLabel());
    }
    ingredients.addAll(recipe.getIngredients());
    this.editing = true;
    // this.index = recipeIndex;

    createRecipeButton.setVisible(false);
    saveRecipeButton.setVisible(true);
    deleteRecipeButton.setVisible(true);

  }

  /**
   * Initialises data from another scene.
   *
   * @param recipe Recipe to initialize
   * @param cookbook Cookbook to initialize
   * 
   */
  public void initData(Recipe recipe, Cookbook cookbook) {
    this.recipeTitle.setText(recipe.getName());
    this.recipePortions.setText(String.valueOf(recipe.getPortions()));
    this.recipeName = recipe.getName();
    if (!recipe.getDescription().isEmpty()) {
      this.recipeDescription.setText(recipe.getDescription());
    }
    if (!recipe.getLabel().isEmpty()) {
      setLabel(recipe.getLabel());
    }
    ingredients.addAll(recipe.getIngredients());
    this.editing = true;
    this.cookbook = cookbook;

    createRecipeButton.setVisible(false);
    saveRecipeButton.setVisible(true);
    deleteRecipeButton.setVisible(true);

  }

  /**
   * Initialises data from another scene.
   *
   * @param cookbook Cookbook to initialize
   * @param dataAccess CookbookInterface to initialize
   */
  public void initData(Cookbook cookbook, CookbookAccess dataAccess) {
    this.cookbook = cookbook;
    this.dataAccess = dataAccess;

    createRecipeButton.setVisible(true);
    saveRecipeButton.setVisible(false);
    deleteRecipeButton.setVisible(false);

  }

  /**
   * Sets label to "breakfast" if breakfastTag is pushed
   *
   * @param ae when breakfast tag is pushed
   */
  public void breakfastTagPushed(ActionEvent ae) {
    setLabel("Breakfast");
  }

  public void lunchTagPushed(ActionEvent ae) {
    setLabel("Lunch");
  }

  public void dinnerTagPushed(ActionEvent ae) {
    setLabel("Dinner");
  }

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
    ingredientListView.setItems(ingredients);
    setLabelButton("blank");
    hb.setSpacing(20);
  }

  public List<Ingredient> getIngredients() {
    return new ArrayList<Ingredient>(ingredients);
  }

  /**
   * Changes color based on which label is selected.
   * 
   * @param label selected label
   */
  public void setLabelButton(String label) {
    if (label.equals("Breakfast")) {
      breakfastTag.setStyle("-fx-text-fill: white; -fx-background-color: red;");
      dinnerTag.setStyle("-fx-background-color: white");
      lunchTag.setStyle("-fx-background-color: white");
    } else if (label.equals("Lunch")) {
      lunchTag.setStyle("-fx-text-fill: white; -fx-background-color: red;");
      dinnerTag.setStyle("-fx-background-color: white");
      breakfastTag.setStyle("-fx-background-color: white");
    } else if (label.equals("Dinner")) {
      dinnerTag.setStyle("-fx-text-fill: white; -fx-background-color: red;");
      breakfastTag.setStyle("-fx-background-color: white");
      lunchTag.setStyle("-fx-background-color: white");
    } else if (label.equals("blank")) {
      lunchTag.setStyle("-fx-background-color: white");
      breakfastTag.setStyle("-fx-background-color: white");
      dinnerTag.setStyle("-fx-background-color: white");

    }
  }

  /**
   * Sets the SceneTarget for return button.
   * @param sceneTarget previous scene. 
   */
  public void setBackButtonTarget(SceneTarget sceneTarget) {
    backButton.setOnAction(sceneTarget.getActionEventHandler());
  }

}
