package foodie.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import foodie.core.Cookbook;
import foodie.core.Ingredient;
import foodie.core.Recipe;
import foodie.ui.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

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
        recipe2 = new Recipe("Fiskekake", 3);
        Ingredient ing3 = new Ingredient("Fisk", 3, "dl");
        Ingredient ing4 = new Ingredient("purre", 1, "stk");
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


    // public void checkIngredient(Ingredient ingredient, Ingredient compIng) {
    //     assertEquals(ingredient.getAmount(), compIng.getAmount());
    //     assertEquals(ingredient.getName(), compIng.getName());
    //     assertEquals(ingredient.getUnit(), compIng.getUnit());
    // }

    // public void checkRecipesListViewItems(Recipe... recipes) {
    //     ListView<Recipe> todoListView = lookup("#mainListView").query();
    //     testRecipes(todoListView.getItems(), recipes);
    //   }    

    // public void checkRecipe(Recipe recipe, Recipe compareRecipe) {
    //     assertEquals(recipe.getPortions(), compareRecipe.getPortions());
    //     assertEquals(recipe.getName(), compareRecipe.getName());
    //     // if (recipe.getDescription() != null) {
    //     assertEquals(recipe.getDescription(), compareRecipe.getDescription());
    //     // }
    //     for (int i = 0; i < 2; i++) {
    //         checkIngredient(recipe.getIngredients().get(i), compareRecipe.getIngredients().get(i));
    //     }
    // }

    // public void testRecipes(Iterable<Recipe> re, Recipe... recipes) {
    //     int i = 0;
    //     for (Recipe r : re) {
    //         assertTrue(i < recipes.length);
    //         checkRecipe(r, recipes[i]);
    //         i++;
    //     }
    //     assertTrue(i == recipes.length);
    // }


}
