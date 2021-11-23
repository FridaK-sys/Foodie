package ui;

import core.Recipe;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

/**
 * ListCell for Recipe. Displays Recipe.
 */
public class ListViewCell extends ListCell<Recipe> {

  private HBox hBox = null;

  private Label recipeTitle = null;

  @Override
  protected void updateItem(Recipe recipe, boolean empty) {
    super.updateItem(recipe, empty);
    setText(null);
    if (empty || recipe == null) {
      setGraphic(null);
    } else {
      hBox = new HBox();
      recipeTitle = new Label();
      recipeTitle.setText(recipe.getName());
      hBox.getChildren().add(recipeTitle);

      setGraphic(hBox);
    }
  }

}
