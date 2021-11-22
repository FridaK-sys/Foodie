package ui;

import core.Cookbook;
import core.Recipe;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.utils.CookbookAccess;

/**
 * Abstract class for RestApp- and LocalAppController.
 */
public abstract class AbstractController implements FoodieController, Initializable {

  protected CookbookAccess dataAccess;

  protected Stage stage;

  static Recipe selectedRecipe;

  @FXML
  ListViewController mainListViewController;

  
  protected abstract void setUpStorage();


  public void setCookbookAccess(CookbookAccess access) {
    CookbookApp.setAccess(dataAccess);
    dataAccess = access;
  }

  public void initializeRecipesView() {
    mainListViewController.setCookbookAccess(dataAccess);
  }

  public void changeSceneToNewRecipe() {
    setSelectedRecipe(null);
    changeScene(CookbookApp.getScenes().get(SceneName.NEWRECIPE));
  }

  public void changeSceneToViewRecipe(Recipe recipe) {
    setSelectedRecipe(recipe);
    changeScene(CookbookApp.getScenes().get(SceneName.VIEWRECIPE));
  }

  public void changeScene(FxmlModel model) {
    Scene scene = model.getScene();
    model.getController().update();
    stage.setScene(scene);
  }

  public Cookbook getCookbook() {
    return mainListViewController.getCookbook();
  }

  static void setSelectedRecipe(Recipe recipe) {
    selectedRecipe = recipe;
  }

  public Recipe getSelectedrecipe() {
    return selectedRecipe;
  }

  @Override
  public void setStage(Stage stage) {
    this.stage = stage;
  }
  
}
