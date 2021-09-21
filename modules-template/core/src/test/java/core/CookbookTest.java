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
    private Ingredient ingredient1, ingredient2, ingredient3;
    private List<Ingredient> ingredientList1 = new ArrayList<>();
    private List<Ingredient> ingredientList2 = new ArrayList<>();
    private List<Recipe> recipes = new ArrayList<>();
	
	@BeforeEach
	public void setUp() {
        ingredient1 = new Ingredient("Mel", 200, "g");
        ingredient2 = new Ingredient("Egg", 2, "stk");
        ingredient3 = new Ingredient("Kjøttdeig", 400, "g");
		ingredientList1.add(ingredient1);
		ingredientList1.add(ingredient2);
        ingredientList2.add(ingredient3);
        recipe1 = new Recipe("Bløtkake", "Den beste oppskriften på bløtkake!", 1, ingredientList1);
		recipe2 = new Recipe("Kjøttkaker", "Mormor sin oppskrift", 4, ingredientList2);
        recipe3 = new Recipe("Wok", "Rask middag", 5, ingredientList1);
        recipes.add(recipe1);
        recipes.add(recipe2);

        cookbook1 = new Cookbook("Mine beste oppskrifter", recipes);
        cookbook2 = new Cookbook();

	}
	
	@Test
	public void testConstructor() {
		assertEquals(cookbook1.getName(), "Mine beste oppskrifter");
        assertEquals(cookbook1.getRecipes(), Arrays.asList(recipe1, recipe2));
	}

    @Test
    public void testEmptyConstructor() {
        assertEquals(cookbook1.getName(), "Ny kokebok");
        assertEquals(cookbook1.getRecipes(), null);
    }


	
	@Test
	public void testSetName() {
        cookbook1.setName("Vegetar");
		assertEquals(cookbook1.getName(), "Vegetar");
    }


		@Test
		public void testAddRecipe() {
        	cookbook1.addRecipe(recipe3);
			assertEquals(cookbook1.getRecipes(), Arrays.asList(recipe1, recipe2, recipe3));
		}

		@Test
		public void testRemoveRecipe() {
        	cookbook1.removeRecipe(recipe1); 
			assertEquals(cookbook1.getRecipes(), Arrays.asList(recipe2, recipe3));
    	}



}