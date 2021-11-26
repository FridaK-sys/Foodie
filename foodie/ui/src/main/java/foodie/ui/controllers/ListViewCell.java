package foodie.ui.controllers;

import foodie.core.Recipe;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * ListCell for Recipe. Displays Recipe.
 */
public class ListViewCell extends ListCell<Recipe> {

  private VBox vbox = null;

  private Label recipeTitle = null;
  private Label recipeLabel = null;

  @Override
  protected void updateItem(Recipe recipe, boolean empty) {
    super.updateItem(recipe, empty);
    if (empty || recipe == null) {
      setStyle("-fx-background-color: white;");
      setGraphic(null);
    } else {
      vbox = new VBox();
      vbox.getStyleClass().add("listViewCell");
      recipeTitle = new Label();
      recipeLabel = new Label();
      recipeTitle.setText(recipe.getName());
      vbox.getChildren().add(recipeTitle);
      if (!recipe.getLabel().isEmpty()) {
        recipeLabel.setText(recipe.getLabel());
        recipeLabel.getStyleClass().add("recipeLabel-Label");
        vbox.getChildren().add(recipeLabel);
      }

      setGraphic(vbox);
    }
  }

}
