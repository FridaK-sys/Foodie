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
        recipe1 = new Recipe("Taco", 4);
        Ingredient ing1 = new Ingredient("Kjøtt", 500, "g");
        Ingredient ing2 = new Ingredient("Lomper", 3, "stk");
        recipe1.addIngredient(ing1);
        recipe1.addIngredient(ing2);
        recipe1.setDescription("Enkel taco");
        recipe2 = new Recipe("Fiskekaker", 3);
        Ingredient ing3 = new Ingredient("Fisk", 3, "dl");
        Ingredient ing4 = new Ingredient("Purre", 1, "stk");
        recipe2.addIngredient(ing3);
        recipe2.addIngredient(ing4);
        recipe2.setDescription("Dette er gode fiskekaker");
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

}
