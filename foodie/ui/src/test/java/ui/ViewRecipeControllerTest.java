package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import core.Ingredient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ui.ViewRecipeController;

public class ViewRecipeControllerTest extends AbstractAppTest {

    private ViewRecipeController controller = new ViewRecipeController();

    @FXML
    private Label recipeTitle;

    @FXML
    private Label portions, labelTag;

    @FXML
    private TextArea textField;

    @Override
    public void start(final Stage stage) throws Exception {

        URL fxmlLocation = getClass().getResource("ViewRecipes_test.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Parent root = fxmlLoader.load();
        setTestData();

        Scene scene = new Scene(root);
        this.controller = fxmlLoader.getController();
        controller.initData(recipe4, 1, null, dataAccess);

        stage.setScene(scene);
        stage.show();
    }

    // @BeforeEach
    // public void setupItems() {

    // }

    @Test
    public void testRecipeView() {
        Label title = lookup("#recipeTitle").query();
        Label portion = lookup("#portions").query();
        ListView<Ingredient> ingredientListView = lookup("#ingredientsListView").query();
        assertEquals(recipe4.getName(), title.getText());
        assertEquals(recipe4.getPortions(), Integer.parseInt(portion.getText()));
        for (int i = 0; i < ingredients.size(); i++) {
            checkIngredient(ingredientListView.getItems().get(i), ingredients.get(i));
        }
    }

    @Test
    public void testLabel() {
        Label label = lookup("#labelTag").query();
        assertEquals(recipe4.getLabel(), label.getText(),
                "Incorrect label, expected: " + recipe4.getLabel() + ", " + label.getText() + " was displayed");
    }

    @Test
    public void testDescription() {
        TextArea description = lookup("#textField").query();
        assertEquals(recipe4.getDescription(), description.getText(), "Incorrect description, expected: "
                + recipe4.getDescription() + ", " + description.getText() + " was displayed");
    }

}
