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
    mainListViewController.setStage(this.stage);
    mainListViewController.setMaster(this);
  }

  @Override
  public void update() {
    mainListViewController.update();
  }

 

  public void pingMainPage() {
    stage.setScene(CookbookApp.getScenes().get(SceneName.NEWRECIPE).getScene());
  }
  
  public void pingViewPage(Recipe recipe) {
    CookbookApp.getScenes().get(SceneName.VIEWRECIPE).setRecipe(recipe);
    // System.out.println(newScene.toString());
    // newScene.setUserData(recipe);
    stage.setScene(CookbookApp.getScenes().get(SceneName.VIEWRECIPE).getScene());
  }

}
