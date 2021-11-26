package foodie.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import foodie.ui.data.LocalCookbookAccess;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/**
 * Controller for running the application with local acces to data stored on the user-device.
 */
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

}
