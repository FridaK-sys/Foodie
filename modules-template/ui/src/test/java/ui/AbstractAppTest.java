package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;
import javafx.scene.control.ListView;
import json.FileHandler;

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

    public void checkRecipesListViewItems(List<Recipe> recipes) {
        ListView<Recipe> todoListView = lookup("#mainListView").query();
        testRecipes(todoListView.getItems(), recipes);
    }

    public void testRecipes(List<Recipe> re, List<Recipe> recipes) {
        for (int j = 0; j < re.size() -1; j++) {
            checkRecipe(re.get(j), recipes.get(j));
        }

    }

    public static Cookbook setUpCookBook(){
        FileHandler handler = new FileHandler();
        Cookbook referenceBook = new Cookbook();
        handler.readRecipesFromFile("src/main/resources/ui/test.txt", referenceBook);
        Recipe recipe1 = new Recipe("Taco", 4);
        Ingredient ing1 = new Ingredient("Kjøtt", 500, "g");
        Ingredient ing2 = new Ingredient("Lomper", 3, "stk");
        recipe1.addIngredient(ing1);
        recipe1.addIngredient(ing2);
        recipe1.setDescription("Enkel taco");
        // Recipe recipe2 = new Recipe("Fiskekaker", 3);
        // Ingredient ing3 = new Ingredient("Fisk", 3, "dl");
        // Ingredient ing4 = new Ingredient("Purre", 1, "stk");
        // recipe2.addIngredient(ing3);
        // recipe2.addIngredient(ing4);
        // recipe2.setDescription("Dette er gode fiskekaker");
        
        // referenceBook.addRecipe(recipe1);
        // referenceBook.addRecipe(recipe2);
        return referenceBook;
    }

}
