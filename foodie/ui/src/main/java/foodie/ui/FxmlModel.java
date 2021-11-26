package foodie.ui;

import foodie.core.Recipe;
import foodie.ui.controllers.AbstractController;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Holds FXML information.
 */
public class FxmlModel {

  private String filepath;
  private SceneName sceneName;
  private Stage stage;
  private Scene scene;
  private Recipe recipe;
  private AbstractController controller;

  /**
   * Construct an FxmlModel.
   *
   * @param filepath the resource name for this FXML
   * @param sceneName the SceneName for this FXML
   * @param stage the stage that the scene will be set to
   */
  public FxmlModel(String filepath, SceneName sceneName, Stage stage) {
    this.filepath = filepath;
    this.sceneName = sceneName;
    this.stage = stage;
    // this. controller = controller;
  }

  public String getResourceName() {
    return filepath;
  }

  public SceneName getSceneName() {
    return sceneName;
  }

  public void setScene(Scene scene) {
    this.scene = scene;
  }

  /**
   * Builds the scene if scene is null. Then it returns the scene to the caller.
   *
   * @return the scene
   */
  public Scene getScene() {
    if (scene == null) {
      scene = new FxmlHandler().load(this);
      // if (logger.isInfoEnabled()) {
      // logger.info("{} has been built", sceneName);
      // }
    }
    return scene;
  }

  /** 
   * Checks if the scene has been built.
   *
   * @return true if the scene has been built successfully, false otherwise
   */
  public boolean hasScene() {
    return scene != null;
  }

  public Stage getStage() {
    return stage;
  }

  public Recipe getRecipe() {
    return recipe;
  }

  public void setRecipe(Recipe recipe) {
    this.recipe = recipe;
  }

  public AbstractController getController() {
    return controller;
  }

  public void setController(AbstractController controller) {
    this.controller = controller;
  }

}
