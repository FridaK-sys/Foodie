/*
 * package json;
 * 
 * import static org.junit.jupiter.api.Assertions.assertEquals; import static
 * org.junit.jupiter.api.Assertions.assertFalse; import static
 * org.junit.jupiter.api.Assertions.assertTrue; import static
 * org.junit.jupiter.api.Assertions.fail;
 * 
 * import org.json.simple.JSONObject; import org.junit.Before; import
 * org.junit.experimental.theories.suppliers.TestedOn; import
 * org.junit.jupiter.api.BeforeAll; import org.junit.jupiter.api.BeforeEach;
 * import org.junit.jupiter.api.Test; import json.FileHandler;
 * 
 * public class FileHandlerTest {
 * 
 * private FileHandler filehandler; private Recipe recipe; private Recipe
 * recipe2; private Ingredient ingredient1, ingredient2, ingredient3; private
 * List<Ingredient> ingredients = new ArrayList<>(); private List<Ingredient>
 * ingredients2 = new ArrayList<>(); private List<Recipe> recipeList = new
 * ArrayList<>(); private CookBook cookbook;
 * 
 * @BeforeEach public void setup() { filehandler = new FileHandler();
 * ingredient1 = new Ingredient("Mel", 200, "g"); ingredient2 = new
 * Ingredient("Egg", 2, "stk"); ingredient3 = new Ingredient("Sukker", 1.5,
 * "dl"); ingredients.add(ingredient1); ingredients.add(ingredient2);
 * ingredients2.add(ingredient3); recipe = new Recipe("Bløtkake",
 * "Den beste oppskriften på bløtkake!", 1, ingredients); recipe2 = new
 * Recipe("Sukker", "Søt dessert!", 1, ingredients2); recipeList.add(recipe,
 * recipe2); cookbook = new Cookbook("Kokebook", recipeList);
 * 
 * }
 * 
 * @Test public void testWriteRecipesToFile() {
 * filehandler.writeRecipesToFile("testFile", cookbook); JSONObject obj = new
 * JSONObject();
 * 
 * JSONAssert.assertEquals("Bløtkake", obj.get("Name"), true);
 * JSONAssert.assertEquals(1, obj.get("Portions"), true);
 * JSONAssert.assertEquals(ingredients, obj.get("Ingrediens"), true);
 * 
 * }
 * 
 * @Test public void testWriteRecipeToFile() {
 * filehandler.writeRecipeToFile("testFile2", recipe2); JSONObject obj = new
 * JSONObject();
 * 
 * JSONAssert.assertEquals("Sukker", obj.get("Name"), true);
 * JSONAssert.assertEquals(1, obj.get("Portions"), true);
 * JSONAssert.assertEquals(ingredients2, obj.get("Ingrediens"), true);
 * 
 * }
 * 
 * @Test public void testReadRecipeFromFile() {
 * 
 * }
 * 
 * }
 */