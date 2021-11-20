package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import ui.ViewRecipeController;

public class ViewRecipeControllerTest extends AbstractAppTest {

    // private ViewRecipeController controller = new ViewRecipeController();

    // @FXML
    // private Label recipeTitle;

    // @FXML
    // private Label portions, labelTag;

    // @FXML
    // private TextArea textField;

    // @Override
    // public void start(final Stage stage) throws Exception {

    // URL fxmlLocation = getClass().getResource("ViewRecipes_test.fxml");
    // FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
    // Parent root = fxmlLoader.load();
    // setTestData();

    // Scene scene = new Scene(root);
    // this.controller = fxmlLoader.getController();
    // controller.initData(recipe4, 1, null, dataAccess);

    // stage.setScene(scene);
    // stage.show();
    // }

    // @BeforeEach
    // public void setupItems() {

    // }

    // @Test
    // public void testRecipeView() {
    // assertEquals(recipe4.getName(), recipeTitle.getText());
    // assertEquals(recipe4.getPortions(), Integer.parseInt(portions.getText()));
    // for (int i = 0; i < ingredients.size(); i++) {
    // checkIngredient(recipe4.getIngredients().get(i), ingredients.get(i));
    // }
    // }

    // @Test
    // public void testLabel() {
    // assertEquals(recipe4.getLabel(), labelTag.getText(),
    // "Incorrect label, expected: " + recipe4.getLabel() + ", " +
    // labelTag.getText() + " was displayed");
    // }

    // @Test
    // public void testDescription() {
    // assertEquals(recipe4.getDescription(), textField.getText(), "Incorrect
    // description, expected: "
    // + recipe4.getDescription() + ", " + textField.getText() + " was displayed");
    // }

}
