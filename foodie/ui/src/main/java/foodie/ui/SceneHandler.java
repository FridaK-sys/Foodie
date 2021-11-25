package foodie.ui;

import java.util.HashMap;
import java.util.Map;
import javafx.stage.Stage;

/**
 * Class for storing and handling information used to change scenes.
 */
public class SceneHandler {

  private static final String MAIN_FXML = "Main.fxml";
  private static final String VIEW_RECIPE_FXML = "ViewRecipe.fxml";
  private static final String NEW_RECIPE_FXML = "NewRecipe.fxml";
  private static final String MAINTEST_FXML = "Main_test.fxml";
  private static Map<SceneName, FxmlModel> scenes = new HashMap<>();


  public static Map<SceneName, FxmlModel> getScenes() {
    return new HashMap<SceneName, FxmlModel>(scenes);
  }

  /**
   * Populates Map with Scenename as keys and FxmlModel as items.
   *
   * @param stage stage to to display the scenes on
   */
  public static void initialize(Stage stage) {
    scenes.put(SceneName.MAIN, new FxmlModel(MAIN_FXML, SceneName.MAIN, stage));
    scenes.put(SceneName.VIEWRECIPE, new FxmlModel(VIEW_RECIPE_FXML, SceneName.VIEWRECIPE, stage));
    scenes.put(SceneName.NEWRECIPE, new FxmlModel(NEW_RECIPE_FXML, SceneName.NEWRECIPE, stage));
  }
  
  /**
   * Populates Map with Scenename and FxmlModels used in testing. Initializes the application with local storage
   *
   * @param stage stage to to display the scenes on
   */
  public static void initializeTest(Stage stage) {
    scenes.put(SceneName.MAIN, new FxmlModel(MAINTEST_FXML, SceneName.MAIN, stage));
    scenes.put(SceneName.VIEWRECIPE, new FxmlModel(VIEW_RECIPE_FXML, SceneName.VIEWRECIPE, stage));
    scenes.put(SceneName.NEWRECIPE, new FxmlModel(NEW_RECIPE_FXML, SceneName.NEWRECIPE, stage));
  }

  /**
   * Update the scene Map with new FxmlInfo.
   *
   * @param name the {@link SceneName} that is the key to update
   * @param info the {@link FxmlInfo} that is the data to update
   */
  public static void updateScenes(SceneName name, FxmlModel info) {
    scenes.put(name, info);
  }

 



  
}
