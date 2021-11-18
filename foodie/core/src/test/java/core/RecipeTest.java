package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeTest {

	private Recipe recipe1, recipe2;
	private Ingredient ingredient1, ingredient2, ingredient3;
	private List<Ingredient> ingredients = new ArrayList<>();

	@BeforeEach
	public void setUp() {
		ingredient1 = new Ingredient("Mel", 200, "g");
		ingredient2 = new Ingredient("Egg", 2, "stk");
		ingredient3 = new Ingredient("Sukker", 1.5, "dl");
		ingredients.add(ingredient1);
		ingredients.add(ingredient2);
		recipe1 = new Recipe("Bløtkake", "Den beste oppskriften på bløtkake!", 1, ingredients);
		recipe2 = new Recipe("Taco");
	}

	@Test
	public void testConstructor() {
		assertEquals(recipe1.getName(), "Bløtkake");
		assertEquals(recipe1.getDescription(), "Den beste oppskriften på bløtkake!");
		assertEquals(recipe1.getPortions(), 1);
		assertEquals(recipe1.getIngredients(), ingredients);
		assertEquals(recipe1.getFav(), false);
		assertEquals(recipe1.getLabel(), "");

		assertThrows(IllegalArgumentException.class, () -> {
			new Recipe("$~@", "Min favoritt dessert", 10, ingredients);
		}, "Invalid name for recipe");

		assertThrows(IllegalArgumentException.class, () -> {
			new Recipe("Muffins", "Min favoritt dessert", -1, ingredients);
		}, "Portions cannot be negative");
	}

	@Test
	public void testConstructor2() {
		assertEquals(recipe2.getName(), "Taco");
		assertEquals(recipe2.getDescription(), "nothing here...");
		assertEquals(recipe2.getPortions(), 0);
		assertEquals(recipe2.getIngredients(), new ArrayList<>());
		assertEquals(recipe2.getFav(), false);
		assertEquals(recipe2.getLabel(), "");

		assertThrows(IllegalArgumentException.class, () -> {
			new Recipe("$~@", "Min favoritt dessert", 10, ingredients);
		}, "Invalid name for recipe");
	}

	@Test
	public void testSetName() {
		recipe1.setName("Muffins");
		assertEquals(recipe1.getName(), "Muffins");

		assertThrows(IllegalArgumentException.class, () -> {
			recipe1.setName("");
		}, "Must select name for recipe");

		assertThrows(IllegalArgumentException.class, () -> {
			recipe1.setName("$~@");
		}, "Invalid name for recipe");
	}

	@Test
	public void testSetPortions() {

		recipe1.setPortions(3);
		assertEquals(recipe1.getPortions(), 3);
		assertEquals(recipe1.getIngredients().get(0).getAmount(), 600);
		assertEquals(recipe1.getIngredients().get(1).getAmount(), 6);

		assertThrows(IllegalArgumentException.class, () -> {
			recipe1.setPortions(0);
		}, "Portions cannot be zero");

		assertThrows(IllegalArgumentException.class, () -> {
			recipe1.setPortions(-1);
		}, "Portions cannot be negative");
	}

	@Test
	public void testAddIngredient() {
		recipe1.addIngredient(ingredient3);
		assertEquals(recipe1.getIngredients(), Arrays.asList(ingredient1, ingredient2, ingredient3));

		assertThrows(IllegalArgumentException.class, () -> {
			recipe1.addIngredient(ingredient3);
		}, "Recipe already contains this ingredient");
	}

	@Test
	public void testRemoveIngredient() {
		recipe1.removeIngredient(0);
		assertEquals(recipe1.getIngredients(), Arrays.asList(ingredient2));
	}

	@Test
	public void testFavorite() {
		assertEquals(recipe1.getFav(), false);
		recipe1.setFav(true);
		assertEquals(recipe1.getFav(), true);
		recipe1.setFav(false);
		assertEquals(recipe1.getFav(), false);
	}

	@Test
	public void testSetLabel() {
		assertThrows(IllegalArgumentException.class, () -> recipe1.setLabel("Vegetar"), "Vegetar is not a valid label");
		recipe1.setLabel("Dinner");
		assertEquals("Dinner", recipe1.getLabel());
	}

	@Test
	public void testRemoveLabel() {
		recipe1.setLabel("Dinner");
		recipe1.removeLabel();
		assertEquals("", recipe1.getLabel());
	}

}