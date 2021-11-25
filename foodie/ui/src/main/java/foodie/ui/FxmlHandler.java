package foodie.ui;

import java.io.IOException;
import java.net.URL;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class FxmlHandler {
  /**
   * Either builds the scene from FxmlInfo or loads the built scene. Uses this class's ClassLoader to
   * find the URL of the FXML file. If the URL is null then the FXML file could not be found.
   * 
   * @param fxmlModel the FXML file info to load the scene with
   * @return the built scene, or null if there was an error
   */
  public Scene load(FxmlModel fxmlModel) {
    if (fxmlModel.hasScene()) {
      return fxmlModel.getScene();
    }
    URL url = getClass().getResource(fxmlModel.getResourceName());
    if (url == null) {
      System.out.println("error");
    }
    FXMLLoader loader = new FXMLLoader(url);
    Scene scene;
    try {
      Parent root = loader.load();
      scene = new Scene(root);
    } catch (IOException e) {
      e.printStackTrace();
      Platform.exit();
      return null;
    }
    // Write back the updated FxmlInfo to the scenes Map in Main
    fxmlModel.setScene(scene);
    SceneHandler.updateScenes(fxmlModel.getSceneName(), fxmlModel);

    AbstractController controller = loader.getController();
    fxmlModel.setController(controller);
    scene.getStylesheets().add(getClass().getResource("MainStyle.css").toString());
    if (controller != null) {
      controller.setStage(fxmlModel.getStage());
    }
    return scene;
  }
}
