package ui;

import javafx.stage.Stage;

/**
 * Interface with update method for controllers.
 */
public interface FoodieController {
  public void update();

  /** @param stage the stage to set */
  void setStage(Stage stage);

}
