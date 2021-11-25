package foodie.ui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Launches the application.
 */
public class CookbookApp extends Application {

  public static void main(String[] args) throws Exception {
    launch(CookbookApp.class, args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    SceneHandler.initialize(stage);
    stage.setScene(SceneHandler.getScenes().get(SceneName.MAIN).getScene());
    stage.setTitle("Foodie<3");
    stage.show();
  }

}
