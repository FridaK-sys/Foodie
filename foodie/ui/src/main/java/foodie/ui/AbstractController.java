package foodie.ui;

import foodie.core.Cookbook;
import foodie.core.Recipe;
import foodie.ui.utils.CookbookAccess;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Abstract class for RestApp- and LocalAppController.
 */
/* */
public abstract class AbstractController implements FoodieController, Initializable {

  protected CookbookAccess dataAccess;

  protected Recipe selectedRecipe = null;

  protected Stage stage;


  @FXML
  AnchorPane listView;

  @FXML
  ListViewController mainListViewController;

  
  protected abstract void setUpStorage();


  public void setCookbookAccess(CookbookAccess access) {
    this.dataAccess = access;
    update();
  }


  private void updateMainListView() {
    mainListViewController.setCookbookAccess(dataAccess);
  }


  public CookbookAccess getAccess() {
    return dataAccess;
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
    AbstractController controller = (AbstractController) model.getController();
    controller.setSelectedRecipe(selectedRecipe);
    controller.setCookbookAccess(dataAccess);
    controller.update();
    
    stage.setScene(scene);
  }

  public Cookbook getCookbook() {
    return mainListViewController.getCookbook();
  }

  void setSelectedRecipe(Recipe recipe) {
    this.selectedRecipe = recipe;
  }

  public Recipe getSelectedrecipe() {
    return selectedRecipe;
  }

  @Override
  public void setStage(Stage stage) {
    this.stage = stage;
  }
  
}
