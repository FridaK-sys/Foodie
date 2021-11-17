package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;

import org.junit.jupiter.api.AfterEach;
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
    private Cookbook referenceBook;
    private Recipe recipe1 = new Recipe("test", 2);

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

    // @BeforeEach
    // public void setupItems() {
    //     referenceBook = setUpCookBook();
    // }

    @Test
    public void testController_MainController() {
        assertNotNull(this.controller);
        // assertNotNull(this.cookbook);
        // testRecipes(this.cookbook.getRecipes(), referenceBook.getRecipes());
    }

    // @Test
    // public void testRecipeListView() {
    //     checkRecipesListViewItems(cookbook.getRecipes());
    // }

    // @Test
    // public void testDeleteButton() {
    // if (referenceBook.getRecipes().size() != 0) {

    // ListView<Recipe> recipeListView = lookup("#mainListView").query();
    // recipeListView.getSelectionModel().select(cookbook.getRecipes().size() - 1);
    // clickOn("#deleteButton");
    // checkRecipesListViewItems(controller.getCookbook().getRecipes());
    // FileHandler handler = new FileHandler();
    // handler.writeRecipesToFile("src/main/resources/ui/test.txt", referenceBook);
    // }
    // }

}
