package foodie.ui;

import foodie.core.Recipe;
import java.io.File;
import foodie.ui.utils.CookbookAccess;
import foodie.ui.utils.LocalCookbookAccess;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LocalAppController extends AbstractController {

  @FXML
  private AnchorPane pane;


  /**
   * Makes local CookbookAccess file.
   */

  @Override
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
    mainListViewController.setMaster(this);
  }

  @Override
  public void update() {
    mainListViewController.setCookbookAccess(dataAccess);
    mainListViewController.update();
  }

}
