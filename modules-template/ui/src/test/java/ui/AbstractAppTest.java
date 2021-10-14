package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

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

        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            checkIngredient(recipe.getIngredients().get(i), compareRecipe.getIngredients().get(i));
        }
    }

    public void checkRecipesListViewItems(Recipe... recipes) {
        ListView<Recipe> todoListView = lookup("#mainListView").query();
        testRecipes(todoListView.getItems(), recipes);
    }

    public void testRecipes(List<Recipe> re, Recipe... recipes) {
        for (int j = 0; j < 2; j++) {
            checkRecipe(re.get(j), recipes[j]);
        }

    }

}
