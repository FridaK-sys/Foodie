package json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;

public class FileHandlerTest {

  private static FileHandler filehandler;
  private static Recipe recipe;
  private static Recipe recipe2;
  private static Ingredient ingredient1, ingredient2, ingredient3, ingredient4;
  private static List<Ingredient> ingredients = new ArrayList<>();
  private static List<Ingredient> ingredients2 = new ArrayList<>();
  private static List<Recipe> recipeList = new ArrayList<>();
  private static Cookbook cookbook;

  @BeforeAll
  public static void setup() {
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
    recipe.setLabel("Breakfast");
    recipe2 = new Recipe("Kakao", "Varm og god dessert!", 1, ingredients2);
    recipe2.setFav(true);
    recipeList.add(recipe);
    recipeList.add(recipe2);
    cookbook = new Cookbook("Kokebok", recipeList);
  }

  public static JSONObject readCookbookFile(String filename, String whatToReturn, int recipeNumber,
      int ingredientNumber) {
    JSONParser jsonParser = new JSONParser();

    try (InputStream input = new FileInputStream(filename)) {
      Reader filereader = new InputStreamReader(input, "UTF-8"); // Read JSON file
      Object obj = jsonParser.parse(filereader); // jsonParser.parse(new
      // FileReader(filename));
      JSONObject Jobj = (JSONObject) obj;
      JSONArray recipeList = (JSONArray) Jobj.get("Recipes");
      switch (whatToReturn) {
      case "cookbook":
        return Jobj;
      case "recipe":
        return (JSONObject) recipeList.get(recipeNumber);
      case "ingredient":
        JSONObject recipe = (JSONObject) recipeList.get(recipeNumber);
        JSONArray ingredients = (JSONArray) recipe.get("Ingredients");
        return (JSONObject) ingredients.get(ingredientNumber);
      default:
        return null;
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (org.json.simple.parser.ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static JSONObject readRecipeFile(String filename, String whatToReturn, int ingredientNumber) {
    JSONParser jsonParser = new JSONParser();

    try (InputStream input = new FileInputStream(filename)) {
      Reader filereader = new InputStreamReader(input, "UTF-8");
      Object obj = jsonParser.parse(filereader);
      JSONObject Jobj = (JSONObject) obj;
      switch (whatToReturn) {
      case "recipe":
        return Jobj;
      case "ingredient":
        JSONArray ingredients = (JSONArray) Jobj.get("Ingredients");
        return (JSONObject) ingredients.get(ingredientNumber);
      default:
        return null;
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (org.json.simple.parser.ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Test
  public void testWriteRecipesToFile() {
    filehandler.writeRecipesToFile("testFile", cookbook);
    assertEquals(cookbook.getName(), (String) readCookbookFile("testFile", "cookbook", 0, 0).get("Name"),
        "Cookbook name should be " + cookbook.getName() + ", but is "
            + (String) readCookbookFile("testFile", "cookbook", 0, 0).get("Name"));
    int i = 0;
    for (Recipe recipe : cookbook.getRecipes()) {
      assertEquals(recipe.getName(), (String) readCookbookFile("testFile", "recipe", i, 0).get("Name"),
          "Name of recipe number " + i + 1 + " should be" + recipe.getName() + ", but is "
              + (String) readCookbookFile("testFile", "recipe", i, 0).get("Name"));
      assertEquals(recipe.getFav(), (Boolean) readCookbookFile("testFile", "recipe", i, 0).get("Favorite"),
          "Favorite of recipe number " + i + 1 + " should be " + recipe.getFav() + ", but is "
              + (Boolean) readCookbookFile("testFile", "recipe", i, 0).get("Favorite"));
      assertEquals(String.valueOf(recipe.getPortions()),
          (String.valueOf(readCookbookFile("testFile", "recipe", i, 0).get("Portions"))),
          "Number of portions in recipe number " + i + 1 + " should be " + recipe.getPortions() + ", but is"
              + readCookbookFile("testFile", "recipe", i, 0).get("Portions"));
      assertEquals(recipe.getDescription(), (String) readCookbookFile("testFile", "recipe", i, 0).get("Description"),
          "Description of recipe number " + i + 1 + " should be " + recipe.getDescription() + ", but is"
              + (String) readCookbookFile("testFile", "recipe", i, 0).get("Description"));
      assertEquals(recipe.getLabel(), (String) readCookbookFile("testFile", "recipe", i, 0).get("Label"),
          "Label of recipe number " + i + 1 + " should be" + recipe.getLabel() + ", but is "
              + (String) readCookbookFile("testFile", "recipe", i, 0).get("Label"));
      int j = 0;
      for (Ingredient ingredient : recipe.getIngredients()) {
        assertEquals(ingredient.getName(), (String) readCookbookFile("testFile", "ingredient", i, j).get("Name"),
            "Name of ingredient number" + j + 1 + " in recipe number" + i + 1 + "should be " + ingredient.getName()
                + ", but is " + (String) readCookbookFile("testFile", "ingredient", i, j).get("Name"));
        assertEquals(String.valueOf(ingredient.getAmount()),
            (String.valueOf(readCookbookFile("testFile", "ingredient", i, j).get("Amount"))),
            "Amount of ingredient number " + j + 1 + " in recipe number " + i + 1 + "is wrong. It should be "
                + ingredient.getAmount() + ", but is "
                + readCookbookFile("testFile", "ingredient", i, j).get("Amount"));
        assertEquals(ingredient.getUnit(), (String) readCookbookFile("testFile", "ingredient", i, j).get("Unit"),
            "Uniten of ingredient number" + j + 1 + " in recipe number " + i + 1 + "is wrong. It should be "
                + ingredient.getUnit() + ", but is "
                + (String) readCookbookFile("testFile", "ingredient", i, j).get("Unit") + "men det skal vare ");
        j++;
      }
      i++;
    }

  }

  @Test
  public void testWriteRecipeToFile() {
    filehandler.writeRecipeToFile("testFile2", recipe2);
    assertEquals(recipe2.getName(), (String) readRecipeFile("testFile2", "recipe", 0).get("Name"), "Name should be "
        + recipe2.getName() + ", but is " + (String) readRecipeFile("testFile2", "recipe", 0).get("Name"));
    assertEquals(recipe2.getFav(), (Boolean) readRecipeFile("testFile2", "recipe", 0).get("Favorite"),
        "Favorite should be " + recipe2.getFav() + ", but is "
            + (Boolean) readRecipeFile("testFile2", "recipe", 0).get("Favorite") + "men det skal vare ");
    assertEquals(String.valueOf(recipe2.getPortions()),
        (String.valueOf(readRecipeFile("testFile2", "recipe", 0).get("Portions"))), "Portions should be "
            + recipe2.getPortions() + ", but is " + readRecipeFile("testFile2", "recipe", 0).get("Portions"));
    assertEquals(recipe2.getDescription(), (String) readRecipeFile("testFile2", "recipe", 0).get("Description"),
        "Description should be " + recipe2.getDescription() + ", but is "
            + (String) readRecipeFile("testFile2", "recipe", 0).get("Description"));
    assertEquals(recipe2.getLabel(), (String) readRecipeFile("testFile2", "recipe", 0).get("Label"), "Label should be "
        + recipe2.getLabel() + ", but is " + (String) readRecipeFile("testFile2", "recipe", 0).get("Label"));
    int j = 0;
    for (Ingredient ingredient : recipe2.getIngredients()) {
      assertEquals(ingredient.getName(), (String) readRecipeFile("testFile2", "ingredient", j).get("Name"),
          "Name of ingredient number" + j + 1 + " should be " + ingredient.getName() + ", but is "
              + (String) readRecipeFile("testFile2", "ingredient", j).get("Name"));
      assertEquals(String.valueOf(ingredient.getAmount()),
          (String.valueOf(readRecipeFile("testFile2", "ingredient", j).get("Amount"))),
          "Amount of ingredient number" + j + 1 + "should be " + ingredient.getAmount() + ", but is "
              + readRecipeFile("testFile2", "ingredient", j).get("Amount"));
      assertEquals(ingredient.getUnit(), (String) readRecipeFile("testFile2", "ingredient", j).get("Unit"),
          "Unit of ingredient number" + j + 1 + " should be " + ingredient.getUnit() + ", but is "
              + (String) readRecipeFile("testFile2", "ingredient", j).get("Unit"));
      j++;
    }

  }

  @Test
  public void testReadRecipeFromFile() throws IOException {
    Cookbook newCookbook = new Cookbook();
    filehandler.readRecipesFromFile("test.json", newCookbook);
    assertEquals(cookbook.getName(), newCookbook.getName(),
        "Cookbook name should be " + cookbook.getName() + ", but was " + newCookbook.getName());

    int i = 0;
    for (Recipe recipe : cookbook.getRecipes()) {
      assertEquals(recipe.getName(), newCookbook.getRecipes().get(i).getName(),
          "Recipe name should be " + recipe.getName() + ", but was " + newCookbook.getRecipes().get(i).getName());
      assertEquals(recipe.getFav(), newCookbook.getRecipes().get(i).getFav(),
          "Favorite of recipe should be " + recipe.getFav() + ", but was " + newCookbook.getRecipes().get(i).getFav());
      assertEquals(recipe.getPortions(), newCookbook.getRecipes().get(i).getPortions(), "Recipe portions should be "
          + recipe.getPortions() + ", but was " + newCookbook.getRecipes().get(i).getPortions());
      assertEquals(recipe.getDescription(), newCookbook.getRecipes().get(i).getDescription(),
          "Recipe description should be " + recipe.getDescription() + ", but was  " + ", men var "
              + newCookbook.getRecipes().get(i).getDescription());
      assertEquals(recipe.getLabel(), newCookbook.getRecipes().get(i).getLabel(),
          "Recipe label should be " + recipe.getLabel() + ", but was " + newCookbook.getRecipes().get(i).getLabel());
      int j = 0;
      for (Ingredient ingredient : recipe.getIngredients()) {

        assertEquals(ingredient.getName(), newCookbook.getRecipes().get(i).getIngredients().get(j).getName(),
            "Ingredient name should be" + ingredient.getName() + ", but was" + ", men var"
                + newCookbook.getRecipes().get(i).getIngredients().get(j).getName());
        assertEquals(ingredient.getAmount(), newCookbook.getRecipes().get(i).getIngredients().get(j).getAmount(),
            "Amount of " + ingredient.getName() + " in " + recipe.getName() + " should be" + ingredient.getAmount()
                + ", but was" + newCookbook.getRecipes().get(i).getIngredients().get(j).getAmount());
        assertEquals(ingredient.getUnit(), newCookbook.getRecipes().get(i).getIngredients().get(j).getUnit(),
            "Unit of " + ingredient.getName() + " in " + recipe.getName() + " should be " + ingredient.getUnit()
                + ", but was " + newCookbook.getRecipes().get(i).getIngredients().get(j).getUnit());
        j++;
      }
      i++;
    }

  }

}