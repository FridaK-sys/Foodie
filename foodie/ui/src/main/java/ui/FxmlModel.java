package ui;

import core.Recipe;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Holds FXML information.
 */
public class FxmlModel {

  private String resourceName;
  private SceneName sceneName;
  private Stage stage;
  private Scene scene;
  private Recipe recipe;
  private AbstractController controller;

  /**
   * Construct an FxmlModel object.
   *
   * @param resourceName the resource name for this FXML
   * @param sceneName the {@link SceneName} for this FXML
   * @param stage the primary stage that the scene will be set to
   * 
   */
  public FxmlModel(String resourceName, SceneName sceneName, Stage stage) {
    this.resourceName = resourceName;
    this.sceneName = sceneName;
    this.stage = stage;
    // this. controller = controller;
  }

  /** 
   * @return the resource name for this FXML file 
   */
  public String getResourceName() {
    return resourceName;
  }

  /** 
   *@return the {@link SceneName} for this FXML file 
   */
  public SceneName getSceneName() {
    return sceneName;
  }

  /**
   * @param scene the scene to set, loaded from this FxmlModel 
   */
  public void setScene(Scene scene) {
    this.scene = scene;
  }

  /**
   * Builds the scene iff {@code scene} is {@code null}. Then it returns the scene to the caller.
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

  /** @return {@code true} if the scene has been built, otherwise {@code false} */
  public boolean hasScene() {
    return scene != null;
  }

  /** @return the primary stage for this Scene */
  public Stage getStage() {
    return stage;
  }

  public void setRecipe(Recipe recipe) {
    this.recipe = recipe;
  }

  public Recipe getRecipe() {
      return recipe;
  }

  public AbstractController getController() {
    return controller;
  }

  public void setController(AbstractController controller) {
    this.controller = controller;
  }

}
