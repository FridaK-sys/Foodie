package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import core.Ingredient;
import core.Recipe;
import javafx.scene.control.ListView;

public abstract class AbstractAppTest extends ApplicationTest {



    public void checkIngredient(Ingredient ingredient, Ingredient compIng) {
        assertEquals(ingredient.getAmount(), compIng.getAmount());
        assertEquals(ingredient.getName(), compIng.getName());
        assertEquals(ingredient.getUnit(), compIng.getUnit());
    }


    public void checkRecipe(Recipe recipe, Recipe compareRecipe) {
        assertEquals(recipe.getPortions(), compareRecipe.getPortions());
        assertEquals(recipe.getName(), compareRecipe.getName());
        // if (recipe.getDescription() != null) {
        assertEquals(recipe.getDescription(), compareRecipe.getDescription());
        // }
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            checkIngredient(recipe.getIngredients().get(i), compareRecipe.getIngredients().get(i));
        }
    }

    public void checkRecipesListViewItems(Recipe... recipes) {
        ListView<Recipe> todoListView = lookup("#mainListView").query();
        testRecipes(todoListView.getItems(), recipes);
      } 

    public void testRecipes(Iterable<Recipe> re, Recipe... recipes) {
        int i = 0;
        for (Recipe r : re) {
            // assertTrue(i < recipes.length);
            checkRecipe(r, recipes[i]);
            i++;
        }
        // assertTrue(i == recipes.length);
    }
    
}
