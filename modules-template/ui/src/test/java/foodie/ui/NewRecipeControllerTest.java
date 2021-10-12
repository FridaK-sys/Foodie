package foodie.ui;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import foodie.core.Cookbook;
import foodie.core.Ingredient;
import foodie.core.Recipe;
import foodie.json.FileHandler;
import foodie.ui.NewRecipeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NewRecipeControllerTest extends AbstractAppTest{

    FileHandler handler = new FileHandler();

    private NewRecipeController controller;
    private Recipe recipe1;
    private Ingredient ing1, ing2;
    private List<Ingredient> ingredients = new ArrayList<>();
    private Cookbook cookbook = new Cookbook();


    @Override
    public void start(final Stage stage) throws Exception {

        URL fxmlLocation = getClass().getResource("NewRecipe_test.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Parent root = fxmlLoader.load();
        this.controller = fxmlLoader.getController();
        // this.cookbook = this.controller.getCookbook();
        Scene scene = new Scene(root);

        stage.setTitle("Cookbook<3");

        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setupItems() {
        recipe1 = new Recipe("Eple", 2);
        ing1 = new Ingredient("Eple", 3, "stk");
        ingredients.add(ing1);
    
        recipe1.addIngredient(ing1);
        recipe1.setDescription("Epler...");
    }

    @Test
    public void testNewIngredient() {
        clickOn("#ingredientTitle").write("Eple");
        clickOn("#ingredientAmount").write("3");
        clickOn("#ingredientUnit").write("stk");
        clickOn("#addIngredient");
        checkIngredient(controller.getIngredients().get(0), ing1);
    }

    @Test
    public void testNewRecipe() throws FileNotFoundException{
        clickOn("#recipeTitle").write("Eple");
        clickOn("#recipePortions").write("2");
        clickOn("#ingredientTitle").write("Eple");
        clickOn("#ingredientAmount").write("3");
        clickOn("#ingredientUnit").write("stk");
        clickOn("#addIngredient");
        clickOn("#recipeDescription").write("Epler...");
        clickOn("#createRecipe");
        handler.readRecipesFromFile("src/main/resources/foodie/ui/test.txt", cookbook);
        checkRecipe(cookbook.getRecipes().get(0), recipe1);
    }


}
