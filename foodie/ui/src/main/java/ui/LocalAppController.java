package ui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import ui.utils.LocalCookbookAccess;

public class LocalAppController extends AbstractController {

  @Override
  protected void setUpStorage() {
    dataAccess = new LocalCookbookAccess("checkCookbookff.json");
  }

  @FXML
  private Pane mainListView;

  @FXML
  ListViewController mainListViewController;

  public void initialize(URL url, ResourceBundle rb) {
    setUpStorage();
    initializeRecipesView();
  }

  private void initializeRecipesView() {
    mainListViewController.setCookbookAccess(dataAccess);
  }

  @Override
  public void update() {
    mainListViewController.update();
  }

}
