import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
			new Ingredient("%:)", 2, "dl");
		});

        assertThrows(IllegalArgumentException.class, () -> {
			new Ingredient("ABCDEFGHIJKLMNOPQRSTUVWXYZÆØÅ", 2, "dl");
		});

        	assertThrows(IllegalArgumentException.class, () -> {
			new Ingredient("Sukker", -2, "dl");
		});
	
	}
	
	@Test
	public void testSetName() {
        ingredient.setName("Smør");
		assertEquals(ingredient.getName(), "Smør");
		
		assertThrows(IllegalArgumentException.class, () -> {
			ingredient.setName("%:)");
		}, "Invalid name for ingredient");
        
		
		 assertThrows(IllegalArgumentException.class, () -> {
			new Ingredient("ABCDEFGHIJKLMNOPQRSTUVWXYZÆØÅ", 2, "dl");
		});
		
    }

}