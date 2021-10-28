package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Ingredient;
import core.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeTest {

	private Recipe recipe;
	private Ingredient ingredient1, ingredient2, ingredient3;
	private List<Ingredient> ingredients = new ArrayList<>();

	@BeforeEach
	public void setUp() {
		ingredient1 = new Ingredient("Mel", 200, "g");
		ingredient2 = new Ingredient("Egg", 2, "stk");
		ingredient3 = new Ingredient("Sukker", 1.5, "dl");
		ingredients.add(ingredient1);
		ingredients.add(ingredient2);
		recipe = new Recipe("Bløtkake", "Den beste oppskriften på bløtkake!", 1, ingredients);
	}

	@Test
	public void testConstructor() {
		assertEquals(recipe.getName(), "Bløtkake");
		assertEquals(recipe.getDescription(), "Den beste oppskriften på bløtkake!");
		assertEquals(recipe.getPortions(), 1);
		assertEquals(recipe.getIngredients(), ingredients);
		assertEquals(recipe.getFav(), false);
		assertEquals(recipe.getLabel(), "");

		assertThrows(IllegalArgumentException.class, () -> {
			new Recipe("$~@", "Min favoritt dessert", 10, ingredients);
		}, "Invalid name for recipe");

		assertThrows(IllegalArgumentException.class, () -> {
			new Recipe("ABCDEFGHIJKLMNOPQRSTUVWXYZÆØÅ", "Min favoritt dessert", 10, ingredients);
		}, "Name contains to many characters");

		assertThrows(IllegalArgumentException.class, () -> {
			new Recipe("Muffins", "Min favoritt dessert", -1, ingredients);
		}, "Portions cannot be zero");

		assertThrows(IllegalArgumentException.class, () -> {
			new Recipe("Muffins", "Min favoritt dessert", -1, ingredients);
		}, "Portions cannot be negative");
	}

	@Test
	public void testSetName() {
		recipe.setName("Muffins");
		assertEquals(recipe.getName(), "Muffins");

		assertThrows(IllegalArgumentException.class, () -> {
			recipe.setName("");
		}, "Must select name for recipe");

		assertThrows(IllegalArgumentException.class, () -> {
			recipe.setName("$~@");
		}, "Invalid name for recipe");

		assertThrows(IllegalArgumentException.class, () -> {
			recipe.setName("ABCDEFGHIJKLMNOPQRSTUVWXYZÆØÅ");
		}, "Name of recipe contains to many characters");
	}

	@Test
	public void testSetPortions() {

		recipe.setPortions(3);
		assertEquals(recipe.getPortions(), 3);
		assertEquals(recipe.getIngredients().get(0).getAmount(), 600);
		assertEquals(recipe.getIngredients().get(1).getAmount(), 6);

		assertThrows(IllegalArgumentException.class, () -> {
			recipe.setPortions(0);
		}, "Portions cannot be zero");

		assertThrows(IllegalArgumentException.class, () -> {
			recipe.setPortions(-1);
		}, "Portions cannot be negative");
	}

	@Test
	public void testAddIngredient() {
		recipe.addIngredient(ingredient3);
		assertEquals(recipe.getIngredients(), Arrays.asList(ingredient1, ingredient2, ingredient3));

		assertThrows(IllegalArgumentException.class, () -> {
			recipe.addIngredient(ingredient3);
		}, "Recipe already contains this ingredient");
	}

	@Test
	public void testRemoveIngredient() {
		recipe.removeIngredient(ingredient1);
		assertEquals(recipe.getIngredients(), Arrays.asList(ingredient2));

		assertThrows(IllegalArgumentException.class, () -> {
			recipe.removeIngredient(ingredient3);
		}, "Recipe does not contain this ingredient");
	}

	@Test
	public void testRemoveIngredientString() {
		recipe.removeIngredient("Mel");
		assertEquals(recipe.getIngredients(), Arrays.asList(ingredient2));
	}

	@Test
	public void testFav() {
		recipe.setFav();
		assertEquals(recipe.getFav(), true);
		recipe.removeFav();
		assertEquals(recipe.getFav(), false);
	}

	@Test
	public void testSetLabel() {
		assertThrows(IllegalArgumentException.class, () -> recipe.setLabel("Dessert"), "Dessert is not an allowed label");
		recipe.setLabel("Middag");
		assertEquals("Middag", recipe.getLabel());
		recipe.setLabel("Lunsj");
		assertEquals("Lunsj", recipe.getLabel());
		recipe.setLabel("Frokost");
		assertEquals("Frokost", recipe.getLabel());
	}

	@Test
	public void testRemoveLabel() {
		recipe.setLabel("Middag");
		recipe.removeLabel();
		assertEquals("", recipe.getLabel());
	}

}