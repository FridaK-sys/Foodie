package ui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import ui.utils.LocalCookbookAccess;

public class LocalAppController extends AbstractController {

  /**
   * Makes local CookbookAccess file
   */

  @Override
  protected void setUpStorage() {
    dataAccess = new LocalCookbookAccess("checkCookbookff.json");
  }

  @FXML
  private Pane mainListView;

  @FXML
  ListViewController mainListViewController;

  /**
   * Initializes
   * 
   * @return the new URI
   */

  public void initialize(URL url, ResourceBundle rb) {
    setUpStorage();
    initializeRecipesView();
  }

  /**
   * Gives mainListViewControll dataAccess to populate listView
   * 
   */

  private void initializeRecipesView() {
    mainListViewController.setCookbookAccess(dataAccess);
  }

  /**
   * Updates the mainListViewController
   */

  @Override
  public void update() {
    mainListViewController.update();
  }

}
