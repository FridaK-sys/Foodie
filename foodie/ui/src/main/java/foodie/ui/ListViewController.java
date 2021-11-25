package foodie.ui;

import foodie.core.Cookbook;
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
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

/**
 * Controller for main page in the application.
 */
public class ListViewController {


  private Cookbook mainBook = new Cookbook();
  private ObservableList<Recipe> recipes = FXCollections.observableArrayList();
  private ToggleGroup group = new ToggleGroup();
  private CookbookAccess dataAccess;
  private AbstractController mainController;

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

  
  @FXML
  void initialize(URL url, ResourceBundle rb) {
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

  /**
   * Loads cookbook from dataAccess and updates view.
   */
  protected void update() {
    mainBook = dataAccess.getCookbook();
    recipes.setAll(mainBook.getRecipes());
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
  protected void updateListView() {
    mainListView.setCellFactory(listView -> {
      ListViewCell listCell = new ListViewCell();
      return listCell;
    });
    setToggles();
    setToggleListener();
    setListViewListener();
    recipes.setAll(mainBook.getRecipes());
    mainListView.setItems(recipes);
    mainListView.getSelectionModel().clearSelection();
  }


  protected void setCookbookAccess(CookbookAccess dataAccess) {
    this.dataAccess = dataAccess;
    this.mainBook = dataAccess.getCookbook();
    updateListView();
  }

  /**
   * Sorts ListView based on label.
   *
   * @param label the selected label
   * @param fav if fav is toggled
   */
  private void sortListview(String label, Boolean fav) {
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

  public void setMainController(AbstractController master) {
    this.mainController = master;
  }

  /**
   * Listener to open a Recipe from ListView when selected.
   */
  protected void setListViewListener() {
    mainListView.getSelectionModel().selectedItemProperty().addListener(
      new ChangeListener<Recipe>() {
      @Override
      public void changed(ObservableValue<? extends Recipe> observable, Recipe oldValue, 
          Recipe newValue) {
        if (newValue != null) {
          mainController.setSelectedRecipe(newValue);
          mainController.changeSceneToViewRecipe();
        }
      }
    });
  }


  /**
   * Listener to update ListView with selected toggle label.
   */
  private void setToggleListener() {
    all.setSelected(true);
    group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
      @Override
      public void changed(ObservableValue<? extends Toggle> ov, Toggle oldToggle, 
            Toggle newToggle) {
        // Has selection.
        if (group.getSelectedToggle() != null) {
          RadioButton button = (RadioButton) group.getSelectedToggle();
          sortListview(button.getId(), fav.isSelected());
        }
      }
    });
  }

  private void setToggles() {
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

  protected void setRecipes(Cookbook cookbook) {
    this.mainBook = cookbook;
    recipes.setAll(cookbook.getRecipes());
    updateListView();
  }

  protected Cookbook getCookbook() {
    Cookbook initiatedCookbook = mainBook;
    return initiatedCookbook;
  }

}
