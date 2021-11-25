package foodie.ui;

import foodie.core.Cookbook;
import foodie.core.Ingredient;
import foodie.core.Recipe;
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
      hBox.getStyleClass().add("ingredientListCell");
      // setStyle("-fx-background-color: white;");
      setGraphic(hBox);
    }
  }
  
}
