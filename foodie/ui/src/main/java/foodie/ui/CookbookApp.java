package foodie.ui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Launches the application.
 */
public class CookbookApp extends Application {

  /**
   * Helper method used by tests needing to run headless.
   */
  public static void supportHeadless() {
    if (Boolean.getBoolean("headless")) {
      System.setProperty("testfx.robot", "glass");
      System.setProperty("testfx.headless", "true");
      System.setProperty("prism.order", "sw");
      System.setProperty("prism.text", "t2k");
      System.setProperty("java.awt.headless", "true");
    }
  }
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
