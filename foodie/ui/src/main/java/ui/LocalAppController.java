package ui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import core.Recipe;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ui.utils.CookbookAccess;
import ui.utils.LocalCookbookAccess;

public class LocalAppController extends AbstractController {

  @FXML
  private AnchorPane pane;

  /**
   * Makes local CookbookAccess file.
   */


  protected void setUpStorage() {
    dataAccess = new LocalCookbookAccess("/checkCookbookff.json");
  }

  /**
   * Initialize method.
   */

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    setUpStorage();
    initializeRecipesView();
    mainListViewController.setMainController(this);
  }

  @Override
  public void update() {
    mainListViewController.setCookbookAccess(dataAccess);
    mainListViewController.update();
  }

  @Override
  public void setSelectedRecipe(Recipe newValue){
  this.selectedRecipe = newValue;
  }

}
