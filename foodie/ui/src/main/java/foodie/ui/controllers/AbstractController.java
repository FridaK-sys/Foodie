package foodie.ui.controllers;

import foodie.core.Cookbook;
import foodie.core.Recipe;
import foodie.ui.FxmlModel;
import foodie.ui.SceneHandler;
import foodie.ui.SceneName;
import foodie.ui.data.CookbookAccess;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Abstract class for RestApp- and LocalAppController.
 */

public abstract class AbstractController implements Initializable {

  protected CookbookAccess dataAccess;

  protected Recipe selectedRecipe = null;

  protected Stage stage;

  @FXML
  AnchorPane listView;

  @FXML
  ListViewController mainListViewController;

  protected abstract void update();


  public void setCookbookAccess(CookbookAccess access) {
    this.dataAccess = access;
    update();
  }


  public void initializeRecipesView() {
    mainListViewController.setCookbookAccess(dataAccess);
  }

  public void changeSceneToNewRecipe() {
    setSelectedRecipe(null);
    changeScene(SceneHandler.getScenes().get(SceneName.NEWRECIPE));
  }

  public void changeSceneToViewRecipe() {
    changeScene(SceneHandler.getScenes().get(SceneName.VIEWRECIPE));
  }

  /**
   * Changes scene to target and updates the targeted controller so the page loads with correct
   * data.
   * @param model the FXML file info to load the scene with.
   */
  public void changeScene(FxmlModel model) {
    final Scene scene = model.getScene();
    AbstractController controller = model.getController();
    
    controller.setSelectedRecipe(getSelectedrecipe());
    controller.setCookbookAccess(dataAccess);
    controller.update();
    
    stage.setScene(scene);
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  protected Cookbook getCookbook() {
    return mainListViewController.getCookbook();
  }
  
  public void setSelectedRecipe(Recipe recipe) {
    this.selectedRecipe = recipe;
  }

  public Recipe getSelectedrecipe() {
    return selectedRecipe;
  }

  protected CookbookAccess getAccess() {
    return dataAccess;
  }
  
}
