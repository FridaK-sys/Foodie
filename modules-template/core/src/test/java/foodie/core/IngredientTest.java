package foodie.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import foodie.core.Ingredient;

public class IngredientTest {

	private Ingredient ingredient;
	
	@BeforeEach
	public void setUp() {
		ingredient = new Ingredient("Mel", 200, "g");
	}
	
	@Test
	public void testConstructor() {
		assertEquals(ingredient.getName(), "Mel");
        assertEquals(ingredient.getAmount(), 200);
        assertEquals(ingredient.getUnit(), "g");
		
		assertThrows(IllegalArgumentException.class, () -> {
			new Ingredient("$~@", 2, "dl");
		}, "Invalid name for ingredient");

        assertThrows(IllegalArgumentException.class, () -> {
			new Ingredient("ABCDEFGHIJKLMNOPQRSTUVWXYZÆØÅ", 2, "dl");
		}, "Name contains to many characters");

		assertThrows(IllegalArgumentException.class, () -> {
			new Ingredient("Sukker", 0, "dl");
		}, "Amount cannot be zero");

        assertThrows(IllegalArgumentException.class, () -> {
			new Ingredient("Sukker", -2, "dl");
		}, "Amount cannot be negative");
	
	}
	
	@Test
	public void testSetName() {
        ingredient.setName("Smør");
		assertEquals(ingredient.getName(), "Smør");
		
		assertThrows(IllegalArgumentException.class, () -> {
			ingredient.setName("$~@");
		}, "Invalid name for ingredient");
        
		 assertThrows(IllegalArgumentException.class, () -> {
			ingredient.setName("ABCDEFGHIJKLMNOPQRSTUVWXYZÆØÅ");
		}, "Name of ingredient contains to many characters");
    }


	@Test
	public void testSetAmount() {
        ingredient.setAmount(250);
		assertEquals(ingredient.getAmount(), 250);
		
		assertThrows(IllegalArgumentException.class, () -> {
			ingredient.setAmount(0);
		}, "Amount cannot be zero");

		assertThrows(IllegalArgumentException.class, () -> {
			ingredient.setAmount(-2);
		}, "Amount cannot be negative");
        
    }

}