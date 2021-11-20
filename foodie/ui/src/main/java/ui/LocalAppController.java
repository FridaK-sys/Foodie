package ui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ui.utils.CookbookAccess;
import ui.utils.LocalCookbookAccess;

public class LocalAppController extends AbstractController {

  /**
   * Makes local CookbookAccess file.
   */

  @Override
  protected void setUpStorage() {
    dataAccess = new LocalCookbookAccess("/checkCookbookff.json");
  }

  @FXML
  private Pane mainListView;


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
    mainListViewController.update();
  }

  @Override
  public void setStage(Stage stage) {
    this.stage = stage;
  }

  public void ping() {
    stage.setScene(CookbookApp.getScenes().get(SceneName.NEWRECIPE).getScene());

  }

}
