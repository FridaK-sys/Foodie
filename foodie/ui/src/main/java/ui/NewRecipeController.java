package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import core.Cookbook;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import json.FileHandler;

public class NewRecipeController implements Initializable {

  private Recipe newRecipe;
  private Cookbook cookbook = new Cookbook();
  private FileHandler fileHandler = new FileHandler();
  private ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
  private String label = "";
  private boolean editing = false;
  private int index;

  @FXML
  private TextField ingredientTitle, ingredientAmount, ingredientUnit, recipePortions, recipeTitle;

  @FXML
  private ListView<Ingredient> ingredientListView;

  @FXML
  private TextArea recipeDescription;

  @FXML
  private Label errorMessageLabel;

  @FXML
  private Button backButton, breakfastTag, lunchTag, dinnerTag;

  @FXML
  private Button saveRecipeButton, deleteRecipeButton, createRecipeButton;

  @FXML
  private HBox hb;

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
        Ingredient newIngredient = new Ingredient(ingredientTitle.getText()); // legge til en konstruktør i ingredient
                                                                              // slitk at vi kan legge til ingredienser
                                                                              // uten mengde, eks "salt"
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

  public void createRecipeButtonPushed(ActionEvent ae) throws IOException {
    try {
      Recipe createdRecipe = createRecipe();
      Cookbook tempBook = new Cookbook();
      fileHandler.readRecipesFromFile("src/main/resources/ui/test.txt", tempBook);
      tempBook.addRecipe(createdRecipe);
      fileHandler.writeRecipesToFile("src/main/resources/ui/test.txt", tempBook);
      backButton.fire();

    } catch (Exception e) {
      errorMessageLabel.setText(e.getMessage());
    }
  }

  public void saveRecipe() {
    try {
      Recipe updatedRecipe = createRecipe();
      fileHandler.replaceRecipeInFile(updatedRecipe, index);
      backButton.fire();

    } catch (Exception e) {
      errorMessageLabel.setText(e.getMessage());
    }
  }

  public Recipe createRecipe() {
    if (recipeTitle.getText().isBlank() || recipePortions.getText().isBlank() || recipePortions.getText() == null) {
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
        newRecipe.setDescription(recipeDescription.getText());
      }
      if (ingredients.isEmpty()) {
        throw new IllegalArgumentException("You are missing ingredients");
      }

      for (Ingredient i : ingredients) {
        newRecipe.addIngredient(i);
      }
      if (!this.label.isBlank()) {
        newRecipe.setLabel(this.label);
      }

      newRecipe.setDescription(recipeDescription.getText());

      return newRecipe;

    } catch (NullPointerException e) {
      throw new NullPointerException("You have empty fields");
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Ingredient amount must be a number");
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }

  }

  public void initData(Recipe recipe, int recipeIndex, Cookbook cookbook) {
    this.recipeTitle.setText(recipe.getName());
    this.recipePortions.setText(String.valueOf(recipe.getPortions()));
    if (!recipe.getDescription().isEmpty()) {
      this.recipeDescription.setText(recipe.getDescription());
    }
    if (!recipe.getLabel().isEmpty()) {
      setLabel(recipe.getLabel());
    }
    ingredients.addAll(recipe.getIngredients());
    this.editing = true;
    this.cookbook = cookbook;
    this.index = recipeIndex;

    createRecipeButton.setVisible(false);
    saveRecipeButton.setVisible(true);
    deleteRecipeButton.setVisible(true);

  }

  public void initData(Cookbook cookbook) {
    this.cookbook = cookbook;

    createRecipeButton.setVisible(true);
    saveRecipeButton.setVisible(false);
    deleteRecipeButton.setVisible(false);

  }

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
    cookbook.removeRecipe(index);
    fileHandler.writeRecipesToFile("src/main/resources/ui/test.txt", cookbook);
    backButton.fire();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ingredientListView.setItems(ingredients);
    setLabelButton("blank");
    hb.setSpacing(20);

  }

  public void changeSceneToListView(ActionEvent ea) throws IOException {
    URL fxmlLocation = getClass().getResource("Main.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

    Parent root = fxmlLoader.load();
    Scene viewRecipesScene = new Scene(root);

    Stage stage = (Stage) ((Node) ea.getSource()).getScene().getWindow();
    stage.setScene(viewRecipesScene);
    stage.show();
  }

  public List<Ingredient> getIngredients() {
    return new ArrayList<Ingredient>(ingredients);
  }

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

}
