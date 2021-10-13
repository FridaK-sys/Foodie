package json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;
import json.FileHandler;

public class FileHandlerTest {

  private FileHandler filehandler;
  private Recipe recipe;
  private Recipe recipe2;
  private Ingredient ingredient1, ingredient2, ingredient3, ingredient4;
  private List<Ingredient> ingredients = new ArrayList<>();
  private List<Ingredient> ingredients2 = new ArrayList<>();
  private List<Recipe> recipeList = new ArrayList<>();
  private Cookbook cookbook;

  public Recipe readRecipe(String filename, int i) {
    InputStream input = new FileInputStream(filename);
    JSONParser jsonParser = new JSONParser();
    JSONObject Jobj = (JSONObject) obj;
    JSONArray recipeList = (JSONArray) Jobj.get("Recipes");
    JSONObject rec = (JSONObject) recipeList.get(i);
    JSONArray ing = (JSONArray) rec.get("Ingredients");
    String name = (String) rec.get("Name");
    Long portionsLong = (Long) rec.get("Portions");
    int portions = portionsLong.intValue();
    String description = (String) rec.get("Description");
    Recipe recipe = new Recipe(name, portions);
    recipe.setDescription(description);

    for (int j = 0; j < ing.size(); j++) {
      JSONObject ingredient = (JSONObject) ing.get(j);
      String nameI = (String) ingredient.get("Name");
      Double amountI = (Double) ingredient.get("Amount");
      String unitI = (String) ingredient.get("Unit");
      Ingredient ingre = new Ingredient(nameI, amountI, unitI);

      recipe.addIngredient(ingre);
    }
    return recipe;
  }

  @BeforeEach
  public void setup() {
    filehandler = new FileHandler();
    ingredient1 = new Ingredient("Mel", 200, "g");
    ingredient2 = new Ingredient("Egg", 2, "stk");
    ingredient3 = new Ingredient("Sukker", 1.5, "dl");
    ingredient4 = new Ingredient("Kakao", 1, "dl");
    ingredients.add(ingredient1);
    ingredients.add(ingredient2);
    ingredients2.add(ingredient3);
    ingredients2.add(ingredient4);
    recipe = new Recipe("Bløtkake", "Den beste oppskriften på bløtkake!", 1, ingredients);
    recipe2 = new Recipe("Kakao", "Varm og god dessert!", 1, ingredients2);
    recipeList.add(recipe, recipe2);
    cookbook = new Cookbook("Kokebook", recipeList);

  }

  @Test
  public void testWriteRecipesToFile() {
    filehandler.writeRecipesToFile("testFile", cookbook);
    int i = 0;
    for (Recipe recipe : cookbook.getRecipes()) {
      JSONAssert.assertEquals(recipe.getName(), readRecipe("testFile", i).getName(), true); // må gjøre recipe.getname
                                                                                            // om til array?
      JSONAssert.assertEquals(recipe.getPortions(), readRecipe("testFile", i).getPortions(), true);
      JSONAssert.assertEquals(recipe.getIngredients(), readRecipe("testFile", i).getIngredients()); // skrevet ingedient
      i++;
    }

  }
  /*
   * @Test public void testWriteRecipeToFile() {
   * filehandler.writeRecipeToFile("testFile2", recipe2); JSONObject obj = new
   * JSONObject();
   * 
   * JSONAssert.assertEquals("Kakao", obj.get("Name"), true);
   * JSONAssert.assertEquals(1, obj.get("Portions"), true); //
   * JSONAssert.assertEquals({["Amount": 1.5, "Unit":"dl", //
   * "Name":"Sukker","Amount":1,"Unit":"dl","Name":"Kakao"]}, //
   * obj.get("Ingrediens"), true);
   * 
   * }
   */
  /*
   * @Test public void testReadRecipeFromFile() {
   * 
   * }
   */

}