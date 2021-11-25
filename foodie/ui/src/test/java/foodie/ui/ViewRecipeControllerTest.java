package foodie.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.net.URL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import foodie.core.Ingredient;
import foodie.ui.ViewRecipeController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import foodie.core.Cookbook;
import foodie.core.Recipe;

import javafx.stage.Stage;

public class ViewRecipeControllerTest extends AbstractAppTest {

    private ViewRecipeController controller = new ViewRecipeController();

    @FXML
    private Label recipeTitle;

    @FXML
    private Label portions, labelTag;

    @FXML
    private TextArea textField;

    @FXML
    private Button faveButton;

    @BeforeAll
    public static void setupHeadless() {
        CookbookApp.supportHeadless();
    }

    @Override
    public void start(final Stage stage) throws Exception {

        URL fxmlLocation = getClass().getResource("ViewRecipes_test.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Parent root = fxmlLoader.load();
        setTestData();

        Scene scene = new Scene(root);
        this.controller = fxmlLoader.getController();
        controller.initData(recipe3);

        stage.setScene(scene);
        stage.show();
    }

    
    @BeforeEach
    public void setupItems() {
        setTestData();
        this.controller.setDataAccess(dataAccess);
    }


    @Test
    public void testRecipeView() {
        Label title = lookup("#recipeTitle").query();
        Label portion = lookup("#portions").query();
        ListView<Ingredient> ingredientListView = lookup("#ingredientsListView").query();
        assertEquals(recipe3.getName(), title.getText());
        assertEquals(recipe3.getPortions(), Integer.parseInt(portion.getText()));
        for (int i = 0; i < recipe3.getIngredients().size(); i++) {
            checkIngredient(ingredientListView.getItems().get(i), recipe3.getIngredients().get(i));
        }
    }

    @Test
    public void testLabel() {
        Label label = lookup("#labelTag").query();
        assertEquals(recipe3.getLabel(), label.getText(),
                "Incorrect label, expected: " + recipe3.getLabel() + ", " + label.getText() + " was displayed");
    }

    @Test
    public void testDescription() {
        TextArea description = lookup("#textField").query();
        assertEquals(recipe3.getDescription(), description.getText(), "Incorrect description, expected: "
                + recipe3.getDescription() + ", " + description.getText() + " was displayed");
    }

    @Test
    public void testFaveButton() {
        Button button = lookup("#faveButton").query();
        assertEquals(button.getText(), "Remove from favorite");
        assertTrue(recipe3.getFav());
        clickOn("#faveButton");
        assertFalse(dataAccess.getCookbook().getRecipes().get(2).getFav());
        assertEquals(button.getText(), "Add to favorite");
        clickOn("#faveButton");
        assertTrue(dataAccess.getCookbook().getRecipes().get(2).getFav());
        assertEquals(button.getText(), "Remove from favorite");
    }

}
