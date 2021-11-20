package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Launches the application.
 */
public class CookbookApp extends Application {

  private static final String MAIN_FXML = "Main.fxml";
  private static final String VIEW_RECIPE_FXML = "ViewRecipe.fxml";
  private static final String NEW_RECIPE_FXML = "NewRecipe.fxml";
  private static final String LISTVIEW_FXML = "ListView.fxml";

  /** Holds the information for various scenes to switch between */
  private static Map<SceneName, FxmlModel> scenes = new HashMap<>();

  public static void main(String[] args) throws Exception {
    launch(CookbookApp.class, args);
  }

  @Override
  public void start(Stage stage) throws Exception {

    scenes.put(SceneName.MAIN, new FxmlModel(MAIN_FXML, SceneName.MAIN, stage));
    scenes.put(SceneName.VIEWRECIPE, new FxmlModel(VIEW_RECIPE_FXML, SceneName.VIEWRECIPE, stage));
    scenes.put(SceneName.NEWRECIPE, new FxmlModel(NEW_RECIPE_FXML, SceneName.NEWRECIPE, stage));
    scenes.put(SceneName.LISTVIEW, new FxmlModel(LISTVIEW_FXML, SceneName.LISTVIEW, stage));


    stage.setScene(scenes.get(SceneName.MAIN).getScene());
    stage.setTitle("Multi-Scene Demo");
    stage.show();


    // URL fxmlLocation = getClass().getResource("Main.fxml");
    // FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
    // Parent root = fxmlLoader.load();
    // Scene scene = new Scene(root);
    // scene.setUserData(fxmlLoader);

    // stage.setTitle("Foodie<3");

    // stage.setScene(scene);
    // stage.show();
  }

  /** @return a Map of the {@link FxmlInfo} by {@link SceneName} */
  public static Map<SceneName, FxmlModel> getScenes() {
    return scenes;
  }

  /**
   * Update the scene Map with new FxmlInfo
   * 
   * @param name the {@link SceneName} that is the key to update
   * @param info the {@link FxmlInfo} that is the data to update
   */
  public static void updateScenes(SceneName name, FxmlModel info) {
    scenes.put(name, info);
  }

}
