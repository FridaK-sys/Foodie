package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;
import javafx.scene.control.ListView;
import json.CookbookPersistence;
import ui.utils.CookbookInterface;
import ui.utils.LocalCookbookAccess;

public abstract class AbstractAppTest extends ApplicationTest {

    protected Cookbook cookbook;
    protected Recipe recipe1, recipe2, recipe3, recipe4;
    protected Ingredient ing1;
    protected List<Ingredient> ingredients = new ArrayList<>();


    protected CookbookInterface dataAccess;

    protected void setTestData() {
        dataAccess = new LocalCookbookAccess("/foodie-test.json");
        this.cookbook = dataAccess.getCookbook();
        assertNotNull(cookbook.getRecipes().get(0));
        // assertEquals(3, cookbook.getRecipes().size());
        recipe1 = cookbook.getRecipes().get(0);
        recipe2 = cookbook.getRecipes().get(1);
        recipe3 = cookbook.getRecipes().get(2);

        recipe4 = new Recipe("Eple", 2);
        ing1 = new Ingredient("Eple", 3, "stk");
        ingredients.add(ing1);

        recipe4.addIngredient(ing1);
        recipe4.setDescription("Epler...");
    }

    public void checkIngredient(Ingredient ingredient, Ingredient compIng) {
        assertEquals(ingredient.getAmount(), compIng.getAmount());
        assertEquals(ingredient.getName(), compIng.getName());
        assertEquals(ingredient.getUnit(), compIng.getUnit());
    }

    public void checkRecipe(Recipe recipe, Recipe compareRecipe, String testString) {
        assertEquals(recipe.getPortions(), compareRecipe.getPortions(), testString);
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
        int i = 0;
        for (Recipe r : re) {
            assertTrue(i < recipes.length, "hello");
            checkRecipe(r, recipes[i], "failed for recipe:" + i);
            i++;
        }
        assertTrue(i == recipes.length, "nottrue, value is: " + i + " , should be: " + recipes.length);
        System.out.println(re.toString());
    }

}
