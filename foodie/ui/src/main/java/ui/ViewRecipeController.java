package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import json.FileHandler;

public class ViewRecipeController implements Initializable {

  private Recipe selectedRecipe;
  private ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
  private int portion;
  private int index;
  private FileHandler fileHandler = new FileHandler();

  @FXML
  private Label recipeTitle, labelTag, portions;

  @FXML
  private ListView<Ingredient> ingredientsListView;

  @FXML
  private TextArea textField;

  @FXML
  private Button faveButton;

  public void favorizeRecipeButton(ActionEvent ae) {
    if (selectedRecipe.getFav() == true) {
      selectedRecipe.setFav(false);
      faveButton.setText("Add to favorite");
    } else {
      selectedRecipe.setFav(true);
      faveButton.setText("Remove from favorite");
    }
    fileHandler.replaceRecipeInFile(this.selectedRecipe, this.index);
  }

  public void incPortion(ActionEvent event) {
    if (this.portion > 0) {
      alterPortions(portion + 1);
    }
  }

  public void decPortion(ActionEvent event) {
    if (this.portion != 1 && portion != 0) {
      alterPortions(portion - 1);
    }
  }

  public void alterPortions(int portionSize) {
    selectedRecipe.setPortions(portionSize);
    ingredients.setAll(selectedRecipe.getIngredients());
    portions.setText(Integer.toString(portionSize));
    this.portion = portionSize;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ingredientsListView.setItems(ingredients);
    textField.setText("Hmm her var det tomt...");

  }

  public void initData(Recipe recipe, int index) {
    this.index = index;
    this.selectedRecipe = recipe;
    this.portion = recipe.getPortions();
    if (selectedRecipe.getName() != null) {
      recipeTitle.setText(selectedRecipe.getName());
    } else {
      recipeTitle.setText("oppskrift");
    }
    portions.setText(Integer.toString(recipe.getPortions()));
    if (!recipe.getIngredients().isEmpty()) {
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

  public void changeToEditRecipe(ActionEvent ae) throws IOException {
    URL fxmlLocation = getClass().getResource("NewRecipe.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

    Parent root = fxmlLoader.load();
    Scene viewRecipesScene = new Scene(root);

    NewRecipeController controller = fxmlLoader.getController();
    controller.initData(selectedRecipe, index);

    Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
    stage.setScene(viewRecipesScene);
    stage.show();
  }

  public void changeSceneToMain(ActionEvent ea) throws IOException {
    URL fxmlLocation = getClass().getResource("Main.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

    Parent root = fxmlLoader.load();
    Scene viewRecipesScene = new Scene(root);

    Stage stage = (Stage) ((Node) ea.getSource()).getScene().getWindow();
    stage.setScene(viewRecipesScene);
    stage.show();
  }

}
