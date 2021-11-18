package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;

/**
 * SceneTarget containing a scene.
 */
public class SceneTarget {
  private Scene scene;

  public SceneTarget(Scene scene) {
    this.scene = scene;
  }

  /**
   * Gets an EventHandler that switches to the scene of this SceneTarget.
   *
   * @return an EventHandler that switches to the scene of this SceneTarget
   */
  public EventHandler<ActionEvent> getActionEventHandler() {
    return actionEvent -> {
      Control control = (Control) actionEvent.getSource();
      Stage stage = (Stage) control.getScene().getWindow();
      FXMLLoader loader = (FXMLLoader) scene.getUserData();

      FoodieController controller = loader.getController();
      controller.update();
      stage.setScene(scene);
    };
  }

}
