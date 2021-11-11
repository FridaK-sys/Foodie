package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
		recipe2.setLabel("Dinner");
		recipe3 = new Recipe("Wok", "Rask Dinner", 5, ingredientList1);
		recipe3.setLabel("Dinner");
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
	}

	@Test
	public void testAddRecipe() {
		cookbook1.addRecipe(recipe3);
		assertEquals(cookbook1.getRecipes(), Arrays.asList(recipe1, recipe2, recipe3));
	}

	@Test
	public void testRemoveRecipeInt() {
		cookbook1.removeRecipe(0);
		assertEquals(cookbook1.getRecipes(), Arrays.asList(recipe2));
	}

	@Test
	public void testGetFavRecipes() {
		recipe1.setFav(true);
		List<Recipe> favs = new ArrayList<>();
		favs.add(recipe1);
		assertEquals(cookbook1.getFavRecipes(), favs);
		recipe1.setFav(false);
		favs.remove(recipe1);
		assertEquals(cookbook1.getFavRecipes(), favs);
	}

	public void testGetRecipesWithLabel() {
		assertThrows(IllegalArgumentException.class, () -> cookbook1.getRecipesWithLabel("Dessert"));

		cookbook1.addRecipe(recipe3);
		List<Recipe> labeled = Arrays.asList(recipe2, recipe3);
		assertEquals(labeled, cookbook1.getRecipesWithLabel("Dinner"));

	}

}