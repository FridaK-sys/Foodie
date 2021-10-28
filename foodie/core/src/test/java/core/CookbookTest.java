package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;

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
		recipe3 = new Recipe("Wok", "Rask middag", 5, ingredientList1);
		recipes.add(recipe1);
		recipes.add(recipe2);

		cookbook1 = new Cookbook("Mine oppskrifter", recipes);
		cookbook2 = new Cookbook();
	}

	@Test
	public void testConstructor() {
		assertEquals(cookbook1.getName(), "Mine oppskrifter");
		assertEquals(cookbook1.getRecipes(), Arrays.asList(recipe1, recipe2));

		assertThrows(IllegalArgumentException.class, () -> {
			new Cookbook("$~@", recipes);
		}, "Invalid name for cookbook");

		assertThrows(IllegalArgumentException.class, () -> {
			new Cookbook("ABCDEFGHIJKLMNOPQRSTUVWXYZÆØÅ", recipes);
		}, "Name contains to many characters");

	}

	@Test
	public void testEmptyConstructor() {
		assertEquals(cookbook2.getName(), "Ny kokebok");
		assertEquals(cookbook2.getRecipes(), new ArrayList<>());
	}

	@Test
	public void testSetName() {
		cookbook1.setName("Vegetar");
		assertEquals(cookbook1.getName(), "Vegetar");

		assertThrows(IllegalArgumentException.class, () -> {
			cookbook1.setName("$~@");
		}, "Invalid name for cookbook");

		assertThrows(IllegalArgumentException.class, () -> {
			cookbook1.setName("ABCDEFGHIJKLMNOPQRSTUVWXYZÆØÅ");
		}, "Name contains to many characters");
	}

	@Test
	public void testAddRecipe() {
		cookbook1.addRecipe(recipe3);
		assertEquals(cookbook1.getRecipes(), Arrays.asList(recipe1, recipe2, recipe3));
	}

	@Test
	public void testRemoveRecipe() {
		cookbook1.removeRecipe(recipe1);
		assertEquals(cookbook1.getRecipes(), Arrays.asList(recipe2));

		assertThrows(IllegalArgumentException.class, () -> {
			cookbook1.removeRecipe(recipe3);
		}, "Cookbook does not contain this recipe");
	}

	@Test
	public void testRemoveRecipeString() {
		cookbook1.removeRecipe("Bløtkake");
		assertEquals(cookbook1.getRecipes(), Arrays.asList(recipe2));
	}

	@Test
	public void testRemoveRecipeInt() {
		cookbook1.removeRecipe(0);
		assertEquals(cookbook1.getRecipes(), Arrays.asList(recipe2));
	}

	@Test
	public void testGetFavRecipes() {
		recipe1.setFav();
		List<Recipe> favs = new ArrayList<>();
		favs.add(recipe1);
		assertEquals(cookbook1.getFavRecipes(), favs);

		recipe1.removeFav();
		favs.remove(recipe1);
		assertEquals(cookbook1.getFavRecipes(), favs);
	}

}