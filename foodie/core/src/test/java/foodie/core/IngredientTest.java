package foodie.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import foodie.core.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IngredientTest {

	private Ingredient ingredient1, ingredient2;

	@BeforeEach
	public void setUp() {
		ingredient1 = new Ingredient("Mel", 200, "g");
		ingredient2 = new Ingredient("Smør");
	}

	@Test
	public void testConstructor() {
		assertEquals(ingredient1.getName(), "Mel", "Name does not match value set in constructor");
		assertEquals(ingredient1.getAmount(), 200, "Amount does not match value set in constructor");
		assertEquals(ingredient1.getUnit(), "g", "Unit does not match value set in constructor");

		assertEquals(ingredient2.getName(), "Smør", "Name does not match value set in constructor");
		assertEquals(ingredient2.getAmount(), 0, "Amount should be 0");
		assertEquals(ingredient2.getUnit(), "", "Unit should be the empty string");

		assertThrows(IllegalArgumentException.class, () -> {
			new Ingredient("$~@");
		}, "Invalid name for ingredient");

		assertThrows(IllegalArgumentException.class, () -> {
			new Ingredient("$~@", 2, "dl");
		}, "Invalid name for ingredient");

		assertThrows(IllegalArgumentException.class, () -> {
			new Ingredient("Sukker", 0, "dl");
		}, "Amount cannot be zero");

		assertThrows(IllegalArgumentException.class, () -> {
			new Ingredient("Sukker", -2, "dl");
		}, "Amount cannot be negative");

	}

	@Test
	public void testSetName() {
		ingredient1.setName("Smør");
		assertEquals(ingredient1.getName(), "Smør", "Incorrect name");

		assertThrows(IllegalArgumentException.class, () -> {
			ingredient1.setName("$~@");
		}, "Invalid name for ingredient");
	}

	@Test
	public void testSetAmount() {
		ingredient1.setAmount(250);
		assertEquals(ingredient1.getAmount(), 250, "Incorrect amount");

		assertThrows(IllegalArgumentException.class, () -> {
			ingredient1.setAmount(0);
		}, "Amount cannot be zero");

		assertThrows(IllegalArgumentException.class, () -> {
			ingredient1.setAmount(-2);
		}, "Amount cannot be negative");

	}

}
