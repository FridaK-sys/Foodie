package foodie.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CookbookTest {

	private Cookbook cookbook1, cookbook2;
	private Recipe recipe1, recipe2, recipe3;
	private Ingredient ingredient1, ingredient2;
	private List<Ingredient> ingredientList1 = new ArrayList<>();
	private List<Recipe> recipes = new ArrayList<>();

	@BeforeEach
	public void setUp() {
		ingredient1 = new Ingredient("Mel", 200, "g");
		ingredient2 = new Ingredient("Egg", 2, "stk");
		ingredientList1.add(ingredient1);
		ingredientList1.add(ingredient2);

		recipe1 = new Recipe("Bløtkake", "Den beste oppskriften på bløtkake!", 1, ingredientList1);
		recipe2 = new Recipe("Kjøttkaker", "Mormor sin oppskrift", 4, ingredientList1);
		recipe1.setLabel("dessert");
		recipe2.setLabel("dinner");
		recipe1.setFav(true);
		recipe3 = new Recipe("Wok", "Rask middag", 5, ingredientList1);
		recipe3.setLabel("dinner");
		recipes.add(recipe1);
		recipes.add(recipe2);

		cookbook1 = new Cookbook(recipes);
		cookbook2 = new Cookbook();
	}

	@Test
	public void testConstructor() {
		assertEquals(cookbook1.getRecipes(), Arrays.asList(recipe1, recipe2),
				"List of recipes does not match value set in constructor");
	}

	@Test
	public void testEmptyConstructor() {
		assertEquals(cookbook2.getRecipes(), new ArrayList<>(), "List should be empty");
	}

	@Test
	public void testAddRecipe() {
		cookbook1.addRecipe(recipe3);
		assertEquals(cookbook1.getRecipes(), Arrays.asList(recipe1, recipe2, recipe3), "Incorrect list of recipes");
		assertThrows(IllegalArgumentException.class, () -> {
			cookbook1.addRecipe(recipe3);
		}, "Should throw exception when recipe already in cookbook");
	}

	@Test
	public void testRemoveRecipe() {
		cookbook1.removeRecipe("Bløtkake");
		assertEquals(cookbook1.getRecipes(), Arrays.asList(recipe2), "Incorrect list of recipes");
	}

	@Test
	public void testReplaceRecipe() {
		Recipe recipe4 = new Recipe("Brød");
		cookbook1.replaceRecipe("Bløtkake", recipe4);
		assertEquals(cookbook1.getRecipes(), Arrays.asList(recipe4, recipe2), "Incorrect list of recipes");
	}

	@Test
	public void isInCookbook() {
		assertTrue(cookbook1.isInCookbook("Bløtkake"));
		assertFalse(cookbook1.isInCookbook("Pannekaker"));
	}

	@Test
	public void testGetRecipesWithLabel() {
		assertEquals(cookbook1.getRecipesWithLabel("dinner"), Arrays.asList(recipe2), "Incorrect list of recipes");
		assertTrue(cookbook1.getRecipesWithLabel("breakfast").isEmpty(), "List should be empty");
		assertThrows(IllegalArgumentException.class, () -> {
			cookbook1.getRecipesWithLabel("Snacks");
		}, "Invalid label");

	}

}
