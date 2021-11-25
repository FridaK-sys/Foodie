package foodie.ui;

import foodie.ui.utils.LocalCookbookAccess;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;


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
