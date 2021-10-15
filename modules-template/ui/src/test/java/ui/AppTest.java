package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import ui.MainController;

public class AppTest extends AbstractAppTest {

    private MainController controller;
    private Cookbook cookbook;
    private Recipe recipe1, recipe2;

    @Override
    public void start(final Stage stage) throws Exception {

        URL fxmlLocation = getClass().getResource("Main_test.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Parent root = fxmlLoader.load();
        this.controller = fxmlLoader.getController();
        this.cookbook = this.controller.getCookbook();
        Scene scene = new Scene(root);

        stage.setTitle("Cookbook<3");

        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setupItems() {
        cookbook = setUpCookBook();
        recipe1 = cookbook.getRecipes().get(0);
        recipe2 = cookbook.getRecipes().get(1);
        controller.setRecipes(cookbook);
    }

    @Test
    public void testController_MainController() {
        assertNotNull(this.controller);
        assertNotNull(this.cookbook);
        testRecipes(this.cookbook.getRecipes(), recipe1, recipe2);
    }

    @Test
    public void testRecipeListView() {
        checkRecipesListViewItems(recipe1, recipe2);
    }

    @Test
    public void testDeleteButton() {
        Cookbook testBook = controller.getCookbook();
        ListView<Recipe> recipeListView = lookup("#mainListView").query();
        recipeListView.getSelectionModel().select(testBook.getRecipes().size() - 1);
        clickOn("#deleteButton");
        checkRecipesListViewItems(recipe1);
    }

}
