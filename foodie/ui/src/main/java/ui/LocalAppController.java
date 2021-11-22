package ui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import core.Recipe;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ui.utils.CookbookAccess;
import ui.utils.LocalCookbookAccess;

public class LocalAppController extends AbstractController {

  @FXML
  private Pane mainListView;


  /**
   * Makes local CookbookAccess file.
   */

  @Override
  protected void setUpStorage() {
    dataAccess = new LocalCookbookAccess("/checkCookbookff.json");
    CookbookApp.setAccess(dataAccess);
  }

  /**
   * Initialize method.
   */

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    setUpStorage();
    initializeRecipesView();
    mainListViewController.setStage(this.stage);
    mainListViewController.setMaster(this);
  }

  @Override
  public void update() {
    mainListViewController.update();
  }

}
