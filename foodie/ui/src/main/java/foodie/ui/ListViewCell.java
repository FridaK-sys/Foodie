package foodie.ui;

import foodie.core.Recipe;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

/**
 * ListCell for Recipe. Displays Recipe.
 */
public class ListViewCell extends ListCell<Recipe> {

  private HBox hbox = null;

  private Label recipeTitle = null;

  @Override
  protected void updateItem(Recipe recipe, boolean empty) {
    super.updateItem(recipe, empty);
    if (empty || recipe == null) {
      setStyle("-fx-background-color: white;");
      setGraphic(null);
    } else {
      hbox = new HBox();
      hbox.getStyleClass().add("listViewCell");
      recipeTitle = new Label();
      recipeTitle.setText(recipe.getName());
      hbox.getChildren().add(recipeTitle);

      setGraphic(hbox);
    }
  }

}
