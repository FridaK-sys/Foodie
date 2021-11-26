package foodie.ui.controllers;

import foodie.core.Ingredient;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

/**
 * ListCell for ringredients.
 * Displays name, unit and amount.
 */
public class IngredientListCell extends ListCell<Ingredient> {

  private HBox hbox = null;

  private Label recipeTitle = null;

  @Override
  protected void updateItem(Ingredient ingredient, boolean empty) {
    super.updateItem(ingredient, empty);
    setText(null);
    if (empty || ingredient == null) {
      setStyle("-fx-background-color: white;");
      setGraphic(null);
    } else {
      hbox = new HBox();
      recipeTitle = new Label();
      recipeTitle.setText(ingredient.toString());
      hbox.getChildren().add(recipeTitle);
      hbox.getStyleClass().add("ingredientListCell");
      setGraphic(hbox);
    }
  }
  
}
