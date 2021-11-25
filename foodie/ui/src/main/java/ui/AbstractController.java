package ui;

import core.Cookbook;
import core.Recipe;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ui.utils.CookbookAccess;

/**
 * Abstract class for RestApp- and LocalAppController.
 */
/* */
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

  public void changeScene(FxmlModel model) {
    Scene scene = model.getScene();
    AbstractController controller = model.getController();
    
    controller.setSelectedRecipe(getSelectedrecipe());
    controller.setCookbookAccess(dataAccess);
    controller.update();
    
    stage.setScene(scene);
  }

  /** @param stage the stage to set */

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
