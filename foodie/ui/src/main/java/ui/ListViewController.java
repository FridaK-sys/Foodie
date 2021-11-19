package ui;

import core.Cookbook;
import core.Recipe;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import ui.utils.CookbookAccess;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for main page in the application.
 */
public class ListViewController implements Initializable {
  private Cookbook mainBook = new Cookbook();
  private ObservableList<Recipe> recipes = FXCollections.observableArrayList();
  // private AbstractController controller;
  private CookbookAccess dataAccess;

  @FXML
  private ListView<Recipe> mainListView;

  @FXML
  private ToggleButton fav;

  private ToggleGroup group = new ToggleGroup();

  @FXML
  private RadioButton all;
  @FXML
  private RadioButton breakfast;
  @FXML
  private RadioButton lunch;
  @FXML
  private RadioButton dinner;

  /**
   * Sets the CookbookAccess for this controller so the data can come from both remote and local
   * sources.
   *
   * @param dataAccess is LocalCookbookAcess or RemoteCookbookAccess
   * 
   */

  public void setCookbookAccess(CookbookAccess dataAccess) {
    this.dataAccess = dataAccess;
    this.mainBook = dataAccess.getCookbook();
    updateListView();
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    mainListView.setCellFactory(listView -> {
      ListViewCell listCell = new ListViewCell();
      return listCell;
    });
    setToggles();
    updateListView();
  }

  public void updateListView() {
    recipes.setAll(mainBook.getRecipes());
    mainListView.setItems(recipes);
    setToggles();
    setToggleListener();
    setListViewListener();
    mainListView.getSelectionModel().clearSelection();
  }

  /**
   * Loads NewRecipeController with selected recipe.
   * 
   * @param recipe selected recipe
   * @throws IOException if file not found or could not be loaded
   */
  public void changeSceneToViewRecipe(Recipe recipe) throws IOException {
    URL fxmlLocation = AbstractController.class.getResource("ViewRecipe.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
    Parent root = fxmlLoader.load();

    Scene viewRecipesScene = new Scene(root);

    ViewRecipeController controller = fxmlLoader.getController();
    SceneTarget sceneTarget = new SceneTarget(lunch.getScene());

    controller.initData(recipe, mainListView.getSelectionModel().getSelectedIndex(), sceneTarget, dataAccess);

    controller.setBackButtonTarget(sceneTarget);
    viewRecipesScene.setUserData(fxmlLoader);
    Stage stage = (Stage) mainListView.getScene().getWindow();
    stage.setScene(viewRecipesScene);
    stage.show();
  }

  /**
   * Loads NewRecipeController.
   * 
   * @throws IOException if file not found or could not be loaded
   */
  public void changeSceneToNewRecipe(ActionEvent ae) throws IOException {
    URL fxmlLocation = AbstractController.class.getResource("NewRecipe.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

    Parent root = fxmlLoader.load();

    Scene viewRecipesScene = new Scene(root);
    NewRecipeController controller = fxmlLoader.getController();
    controller.initData(mainBook, dataAccess);
    controller.setBackButtonTarget(new SceneTarget(lunch.getScene()));

    Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
    stage.setScene(viewRecipesScene);
    stage.show();
  }

  /**
   * Loads cookbook from dataAccess and updates view.
   */
  public void update() {
    mainBook = dataAccess.getCookbook();
    updateListView();
  }

  /**
   * Displays fav recipies when button is toggled.
   */
  @FXML
  public void toggleFav() {
    RadioButton button = (RadioButton) group.getSelectedToggle();
    sortListview(button.getId(), fav.isSelected());
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
    recipes.setAll(dataAccess.getCookbook().getRecipes());
  }

  /**
   * Sorts ListView based on label
   * 
   * @param label the selected label
   * @param fav if fav is toggled
   */
  public void sortListview(String label, Boolean fav) {
    if (label.equals("all")) {
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

  /**
   * Listener to open a Recipe from ListView when selected.
   */
  public void setListViewListener() {
    mainListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Recipe>() {
      @Override
      public void changed(ObservableValue<? extends Recipe> observable, Recipe oldValue, Recipe newValue) {
        if (newValue != null) {
          try {
            changeSceneToViewRecipe(newValue);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
        System.out.println("ListView selection changed from oldValue = " + oldValue + " to newValue = " + newValue);
      }
    });
  }

  public void setToggles() {
    all.getStyleClass().remove("radio-button");
    all.getStyleClass().add("toggle-button");
    breakfast.getStyleClass().add("toggle-button");
    breakfast.getStyleClass().remove("radio-button");
    lunch.getStyleClass().remove("radio-button");
    lunch.getStyleClass().add("toggle-button");
    dinner.getStyleClass().remove("radio-button");
    dinner.getStyleClass().add("toggle-button");
    all.setToggleGroup(group);
    breakfast.setToggleGroup(group);
    lunch.setToggleGroup(group);
    dinner.setToggleGroup(group);

  }

  /**
   * Listener to update ListView with selected toggle label.
   */
  public void setToggleListener() {
    all.setSelected(true);
    group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
      @Override
      public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
        // Has selection.
        if (group.getSelectedToggle() != null) {
          RadioButton button = (RadioButton) group.getSelectedToggle();
          sortListview(button.getId(), fav.isSelected());
        }
      }
    });
  }

  public Cookbook getCookbook() {
    return mainBook;
  }

}
