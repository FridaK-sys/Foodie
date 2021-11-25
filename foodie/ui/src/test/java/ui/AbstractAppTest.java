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
import ui.utils.CookbookAccess;
import ui.utils.LocalCookbookAccess;

public abstract class AbstractAppTest extends ApplicationTest {

    protected Cookbook cookbook;
    protected Recipe recipe1, recipe2, recipe3, recipe4;
    protected Ingredient ing1;
    protected List<Ingredient> ingredients = new ArrayList<>();
    protected Cookbook testCookbook = populationData();

    protected CookbookAccess dataAccess;

    protected void setTestData() {
        this.cookbook = null;
        dataAccess = new LocalCookbookAccess("/foodie-test.json");
        dataAccess.setRecipes(testCookbook.getRecipes());
        this.cookbook = dataAccess.getCookbook();
        assertNotNull(this.cookbook, "Could not load Cookbook from DataAccess");
        assertTrue(3 <= cookbook.getRecipes().size());
        recipe1 = cookbook.getRecipes().get(0);
        recipe2 = cookbook.getRecipes().get(1);
        recipe3 = cookbook.getRecipes().get(2);
        recipe4 = new Recipe("Eple");
        recipe4.setPortions(2);
        ing1 = new Ingredient("Eple", 3, "stk");
        ingredients.add(ing1);
        recipe4.addIngredient(ing1);
        recipe4.setDescription("Epler...");
    }

    public void checkIngredient(Ingredient ingredient, Ingredient compIng) {
        assertEquals(ingredient.getAmount(), compIng.getAmount(),
                "Expected amount: " + compIng.getAmount() + ", was: " + ingredient.getAmount());
        assertEquals(ingredient.getName(), compIng.getName(),
                "Expected name: " + compIng.getAmount() + ", was: " + ingredient.getAmount());
        assertEquals(ingredient.getUnit(), compIng.getUnit(),
                "Expected unit: " + compIng.getAmount() + ", was: " + ingredient.getAmount());
    }

    public void checkRecipe(Recipe recipe, Recipe compareRecipe, int num) {
        assertEquals(recipe.getPortions(), compareRecipe.getPortions(), "Failed for recipe number: " + num
                + ", expected portion size: " + compareRecipe.getPortions() + ", was: " + recipe.getPortions());
        assertEquals(recipe.getName(), compareRecipe.getName(), "Failed for recipe number: " + num + ", expected name: "
                + compareRecipe.getName() + ", was: " + recipe.getName());

        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            checkIngredient(recipe.getIngredients().get(i), compareRecipe.getIngredients().get(i));
        }
    }

    public void checkRecipesListViewItems(Recipe... recipes) {
        ListView<Recipe> recipeListView = lookup("#mainListView").query();
        testRecipes(recipeListView.getItems(), recipes);
    }

    public void testRecipes(List<Recipe> re, Recipe... recipes) {
        int i = 0;
        for (Recipe r : re) {
            assertTrue(i < recipes.length, "Too many recipes in list");
            checkRecipe(r, recipes[i], i);
            i++;
        }
        assertTrue(i == recipes.length, ("Incorrect length of recipes, was: " + i + " , should be: " + recipes.length));
        System.out.println(re.toString());
    }

    private Cookbook populationData() {
        Recipe testRecipe1 = new Recipe("KakeKake");
        testRecipe1.addIngredient(new Ingredient("Mel", 200, "g"));


        Recipe testRecipe2 = new Recipe("EpleKake");
        testRecipe2.setLabel("breakfast");
        testRecipe2.addIngredient(new Ingredient("Epler", 200, "gram"));

        Recipe testRecipe3 = new Recipe("Fiskeake");
        testRecipe3.setLabel("dinner");
        testRecipe3.setFav(true);
        testRecipe3.addIngredient(new Ingredient("Fisk", 400, "g"));
        testRecipe3.addIngredient(new Ingredient("Mel", 3, "ss"));


        Cookbook testCookbook = new Cookbook();
        testCookbook.addRecipe(testRecipe1);
        testCookbook.addRecipe(testRecipe2);
        testCookbook.addRecipe(testRecipe3);

        return testCookbook;
    }

}
