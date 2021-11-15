package ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import core.Cookbook;
import core.Ingredient;
import core.Recipe;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import json.FileHandler;
import ui.utils.CookbookInterface;

public abstract class AbstractController {

  private Cookbook mainBook = new Cookbook();
  private ObservableList<Recipe> recipes = FXCollections.observableArrayList();
  private FileHandler fileHandler = new FileHandler();

  protected CookbookInterface dataAccess;

  @FXML
  protected ListView<Recipe> mainListView;

  @FXML
  private Button viewButton;

  @FXML
  public ToggleButton Fav;

  private ToggleGroup group = new ToggleGroup();

  @FXML
  RadioButton All, Breakfast, Lunch, Dinner, Favorite;

  public void initialize(URL url, ResourceBundle rb) {
    fileHandler.readRecipesFromFile("src/main/resources/ui/test.txt", mainBook);

    recipes.setAll(mainBook.getRecipes());
    mainListView.setItems(recipes);
    setToggleListener();
    setListViewListener();
  }

  public void changeSceneToViewRecipe(ActionEvent ae) throws IOException {
    URL fxmlLocation = getClass().getResource("ViewRecipe.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

    Parent root = fxmlLoader.load();

    Scene viewRecipesScene = new Scene(root);

    ViewRecipeController controller = fxmlLoader.getController();
    controller.initData(mainListView.getSelectionModel().getSelectedItem(),
        mainListView.getSelectionModel().getSelectedIndex());
    Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
    stage.setScene(viewRecipesScene);
    stage.show();
  }

  public void changeSceneToNewRecipe(ActionEvent ae) throws IOException {
    URL fxmlLocation = getClass().getResource("NewRecipe.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

    Parent root = fxmlLoader.load();

    Scene viewRecipesScene = new Scene(root);
    NewRecipeController controller = fxmlLoader.getController();
    controller.initData(mainBook);

    Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
    stage.setScene(viewRecipesScene);
    stage.show();
  }

  public void changeSceneToEditRecipe(ActionEvent ae) throws IOException {
    URL fxmlLocation = getClass().getResource("NewRecipe.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

    Parent root = fxmlLoader.load();
    Scene viewRecipesScene = new Scene(root);

    NewRecipeController controller = fxmlLoader.getController();
    controller.initData(mainListView.getSelectionModel().getSelectedItem(),
        mainListView.getSelectionModel().getSelectedIndex(), mainBook);

    Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
    stage.setScene(viewRecipesScene);
    stage.show();
  }

  @FXML
  public void deleteRecipeButtonPushed() {
    int index = mainListView.getSelectionModel().getSelectedIndex();
    recipes.remove(index);
    mainBook.removeRecipe(index);
    fileHandler.writeRecipesToFile("src/main/resources/ui/test.txt", mainBook);
  }

  public Cookbook getCookbook() {
    return mainBook;
  }

  public void addRecipe(Recipe recipe) {
    mainBook.addRecipe(recipe);
    recipes.setAll(mainBook.getRecipes());
  }

  public void removeRecipe(int index) {
    mainBook.removeRecipe(index);
    recipes.setAll(mainBook.getRecipes());
  }

  public void setRecipes(Cookbook cookbook) {
    mainBook = new Cookbook("test", cookbook.getRecipes());
    recipes.setAll(mainBook.getRecipes());
    fileHandler.writeRecipesToFile("src/main/resources/ui/test.txt", mainBook);
  }

  public void setListViewListener() {
    mainListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Recipe>() {
      @Override
      public void changed(ObservableValue<? extends Recipe> observable, Recipe oldValue, Recipe newValue) {
        viewButton.fire();
        System.out.println("ListView selection changed from oldValue = " + oldValue + " to newValue = " + newValue);
      }
    });
  }

  public void setToggleListener() {
    All.setToggleGroup(group);
    Breakfast.setToggleGroup(group);
    Lunch.setToggleGroup(group);
    Dinner.setToggleGroup(group);
    All.setSelected(true);
    group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
      @Override
      public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
        // Has selection.
        if (group.getSelectedToggle() != null) {
          RadioButton button = (RadioButton) group.getSelectedToggle();

          sortListview(button.getId(), Fav.isSelected());
        }
      }
    });
  }

  public void updateListView() {

  }

  @FXML
  public void toggleFav() {
    RadioButton button = (RadioButton) group.getSelectedToggle();
    sortListview(button.getId(), Fav.isSelected());
  }

  public void sortListview(String label, Boolean fav) {
    // Cookbook tempBook = new Cookbook();
    if (label.equals("All")) {
      if (fav) {
        recipes.setAll((mainBook.getRecipes()).stream().filter(r -> r.getFav() == true).toList());
      } else {
        recipes.setAll(mainBook.getRecipes());
      }
    }
    List<Recipe> recipesWithLabel = mainBook.getRecipesWithLabel(label);
    if (fav) {
      recipes.setAll(recipesWithLabel.stream().filter(r -> r.getFav() == true).toList());
    } else {
      recipes.setAll(recipesWithLabel);
    }
  }

  protected abstract void setUpStorage();

}
