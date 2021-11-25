package foodie.ui;

import core.Ingredient;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class IngredientListCell extends ListCell<Ingredient> {

  private HBox hBox = null;

  private Label recipeTitle = null;

  @Override
  protected void updateItem(Ingredient ingredient, boolean empty) {
    super.updateItem(ingredient, empty);
    setText(null);
    if (empty || ingredient == null) {
      setStyle("-fx-background-color: white;");
      setGraphic(null);
    } else {
      hBox = new HBox();
      recipeTitle = new Label();
      recipeTitle.setText(ingredient.toString());
      hBox.getChildren().add(recipeTitle);
      setStyle("-fx-background-color: white;");
      setGraphic(hBox);
    }
  }
  
}
