package foodie.ui;

import foodie.core.Cookbook;
import foodie.core.Ingredient;
import foodie.core.Recipe;
import foodie.ui.utils.CookbookAccess;
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

/**
 * Controller for main page in the application.
 */
public class ListViewController implements FoodieController {


  private Cookbook mainBook = new Cookbook();
  private ObservableList<Recipe> recipes = FXCollections.observableArrayList();
  private ToggleGroup group = new ToggleGroup();
  private CookbookAccess dataAccess;
  private AbstractController mainController;
  // private Stage stage;

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
  @FXML
  private RadioButton dessert;

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

  @FXML
  void initialize(URL url, ResourceBundle rb) {
    setListViewCellfactory();
    setToggles();
    updateListView();
    
  }

  private void setListViewCellfactory() {
    mainListView.setCellFactory(listView -> {
      ListViewCell listCell = new ListViewCell();
      return listCell;
    });
  }

  /**
   * Loads NewRecipeController.
   *
   * @throws IOException if file not found or could not be loaded
   */
  public void changeSceneToNewRecipe(ActionEvent ae) throws IOException {
    mainController.changeSceneToNewRecipe();
  }

  public void changeSceneToViewRecipe() {
    mainController.changeSceneToViewRecipe();
  }

  /**
   * Loads cookbook from dataAccess and updates view.
   */
  public void update() {
    mainBook = dataAccess.getCookbook();
    updateListView();
    setListViewCellfactory();
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
    setListViewCellfactory();
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
    // this.stage = stage;
  }

  public void setMaster(AbstractController master) {
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
          System.out.println("Exception kommer her da, ser det:" + newValue.toString());
          System.out.println("Exception kommer her da, ser det:" + newValue);
          mainController.setSelectedRecipe(newValue);
          changeSceneToViewRecipe();
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
      public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, 
      Toggle new_toggle) {
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
    dessert.getStyleClass().remove("radio-button");
    dessert.getStyleClass().add("toggle-button");
    all.setToggleGroup(group);
    breakfast.setToggleGroup(group);
    lunch.setToggleGroup(group);
    dinner.setToggleGroup(group);
    dessert.setToggleGroup(group);

  }

  public void setRecipes(Cookbook cookbook) {
    this.mainBook = cookbook;
    recipes.setAll(cookbook.getRecipes());
    updateListView();
  }

  public Cookbook getCookbook() {
    Cookbook initiatedCookbook = mainBook;
    return initiatedCookbook;
  }

}
