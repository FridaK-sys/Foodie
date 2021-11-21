package ui;

import core.Cookbook;
import core.Recipe;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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

/**
 * Controller for main page in the application.
 */
public class ListViewController implements FoodieController {


  private Cookbook mainBook = new Cookbook();
  private ObservableList<Recipe> recipes = FXCollections.observableArrayList();
  private ToggleGroup group = new ToggleGroup();
  private CookbookAccess dataAccess;
  private LocalAppController mainController;
  private Stage stage;

  @FXML
  private ListView<Recipe> mainListView;

  @FXML
  private ToggleButton fav;

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

  public void initialize(URL url, ResourceBundle rb) {
    mainListView.setCellFactory(listView -> {
      ListViewCell listCell = new ListViewCell();
      return listCell;
    });
    setToggles();
    updateListView();
  }

  /**
   * Loads NewRecipeController.
   *
   * @throws IOException if file not found or could not be loaded
   */
  public void changeSceneToNewRecipe(ActionEvent ae) throws IOException {
    mainController.changeSceneToNewRecipe();
  }

  public void changeSceneToViewRecipe(Recipe recipe) {
    mainController.changeSceneToViewRecipe(recipe);
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

  

  /**
   * Updates the list view.
   */
  public void updateListView() {
    recipes.setAll(mainBook.getRecipes());
    mainListView.setItems(recipes);
    setToggles();
    setToggleListener();
    setListViewListener();
    mainListView.getSelectionModel().clearSelection();
  }

  /**
   * Sorts ListView based on label.
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
    } else {
      List<Recipe> recipesWithLabel = mainBook.getRecipesWithLabel(label);
      if (fav) {
        recipes.setAll(recipesWithLabel.stream().filter(r -> r.getFav() == true).toList());
      } else {
        recipes.setAll(recipesWithLabel);
      }
    }
  }


  @Override
  public void setStage(Stage stage) {
    this.stage = stage;
  }

  public void setMaster(LocalAppController master) {
    this.mainController = master;
  }

  /**
   * Listener to open a Recipe from ListView when selected.
   */
  public void setListViewListener() {
    mainListView.getSelectionModel().selectedItemProperty().addListener(
      new ChangeListener<Recipe>() {
      @Override
      public void changed(ObservableValue<? extends Recipe> observable, Recipe oldValue, 
        Recipe newValue) {
        if (newValue != null) {
          changeSceneToViewRecipe(newValue);
        }
        System.out.println("ListView selection changed from oldValue = " + oldValue 
          + " to newValue = " + newValue);
      }
    });
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

  public void setRecipes(Cookbook cookbook) {
    this.mainBook = cookbook;
    recipes.setAll(cookbook.getRecipes());
  }

  public Cookbook getCookbook() {
    Cookbook initiatedCookbook = mainBook;
    return initiatedCookbook;
  }

}
