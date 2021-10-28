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

  private static String cookbookString;
  private static String recipe2String;
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
    cookbookString = """
        {
          "Recipes":
          [{
            "Favorite":false,
            "Portions":1,
            "Description":"Den beste oppskriften på bløtkake!",
            "Ingredients":
            [{
              "Amount":200.0,
              "Unit":"g",
              "Name":"Mel"
            },
            {"Amount":2.0,
            "Unit":"stk",
            "Name":"Egg"
          }],
          "Label":"",
          "Name":"Bløtkake"
        },{
          "Favorite":false,
          "Portions":1,
          "Description":"Varm og god dessert!",
          "Ingredients":
          [{
            "Amount":1.5,
            "Unit":"dl",
            "Name":"Sukker"
          },{
            "Amount":1.0,
            "Unit":"dl",
            "Name":"Kakao"
          }],
          "Label":"",
          "Name":"Kakao"
        }],
        "Name":"Kokebok"}
        """;
    recipe2String = """
          {
            "Favorite":false,
            "Portions":1,
            "Description":"Varm og god dessert!",
            "Ingredients":
            [{"Amount":1.5,
            "Unit":"dl",
            "Name":"Sukker"
          },{
            "Amount":1.0,
            "Unit":"dl",
            "Name":"Kakao"
          }],"Label":"",
          "Name":"Kakao"
        }
              """;
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
        "Navnet på kokeboken er feil. Det som står i teksten er "
            + (String) readCookbookFile("testFile", "cookbook", 0, 0).get("Name") + "mens det skal vaere "
            + cookbook.getName());
    int i = 0;
    for (Recipe recipe : cookbook.getRecipes()) {
      assertEquals(recipe.getName(), (String) readCookbookFile("testFile", "recipe", i, 0).get("Name"),
          "Navnet på oppskrift nummer " + i + 1 + "er feil. Det som står i teksten er "
              + (String) readCookbookFile("testFile", "recipe", i, 0).get("Name") + "men det skal vare "
              + recipe.getName());
      assertEquals(String.valueOf(recipe.getPortions()),
          (String.valueOf(readCookbookFile("testFile", "recipe", i, 0).get("Portions"))),
          "Antall porsjoner på oppskrift nummer " + i + 1 + "er feil. Det som står i teksten er "
              + readCookbookFile("testFile", "recipe", i, 0).get("Portions") + "men det skal vaere "
              + recipe.getPortions());
      assertEquals(recipe.getDescription(), (String) readCookbookFile("testFile", "recipe", i, 0).get("Description"),
          "Beskrivelsen på oppskrift nummer " + i + 1 + "er feil. Det som står i teksten er "
              + (String) readCookbookFile("testFile", "recipe", i, 0).get("Description") + "men det skal vare "
              + recipe.getDescription());
      int j = 0;
      for (Ingredient ingredient : recipe.getIngredients()) {
        assertEquals(ingredient.getName(), (String) readCookbookFile("testFile", "ingredient", i, j).get("Name"),
            "Navnet på ingrediens nummer" + j + 1 + " på oppskrift nummer " + i + 1
                + "er feil. Det som står i teksten er "
                + (String) readCookbookFile("testFile", "ingredient", i, j).get("Name") + "men det skal vare "
                + ingredient.getName());
        assertEquals(String.valueOf(ingredient.getAmount()),
            (String.valueOf(readCookbookFile("testFile", "ingredient", i, j).get("Amount"))),
            "Mengden på ingrediens nummer" + j + 1 + " på oppskrift nummer " + i + 1
                + "er feil. Det som står i teksten er " + readCookbookFile("testFile", "ingredient", i, j).get("Amount")
                + "men det skal vare " + ingredient.getAmount());
        assertEquals(ingredient.getUnit(), (String) readCookbookFile("testFile", "ingredient", i, j).get("Unit"),
            "Uniten på ingrediens nummer" + j + 1 + " på oppskrift nummer " + i + 1
                + "er feil. Det som står i teksten er "
                + (String) readCookbookFile("testFile", "ingredient", i, j).get("Unit") + "men det skal vare "
                + ingredient.getUnit());
        j++;
      }
      i++;
    }

  }

  @Test
  public void testWriteRecipeToFile() {
    filehandler.writeRecipeToFile("testFile2", recipe2);
    assertEquals(recipe2.getName(), (String) readRecipeFile("testFile2", "recipe", 0).get("Name"),
        "Navnet på oppskrift er feil. Det som står i teksten er "
            + (String) readRecipeFile("testFile2", "recipe", 0).get("Name") + "men det skal vare " + recipe2.getName());
    assertEquals(String.valueOf(recipe2.getPortions()),
        (String.valueOf(readRecipeFile("testFile2", "recipe", 0).get("Portions"))),
        "Antall porsjoner er feil. Det som står i teksten er "
            + readRecipeFile("testFile2", "recipe", 0).get("Portions") + "men det skal vaere " + recipe2.getPortions());
    assertEquals(recipe2.getDescription(), (String) readRecipeFile("testFile2", "recipe", 0).get("Description"),
        "Navnet på oppskrift er feil. Det som står i teksten er "
            + (String) readRecipeFile("testFile2", "recipe", 0).get("Description") + "men det skal vare "
            + recipe2.getDescription());
    int j = 0;
    for (Ingredient ingredient : recipe2.getIngredients()) {
      assertEquals(ingredient.getName(), (String) readRecipeFile("testFile2", "ingredient", j).get("Name"),
          "Navnet på ingrediens nummer" + j + 1 + "er feil. Det som står i teksten er "
              + (String) readRecipeFile("testFile2", "ingredient", j).get("Name") + "men det skal vare "
              + ingredient.getName());
      assertEquals(String.valueOf(ingredient.getAmount()),
          (String.valueOf(readRecipeFile("testFile2", "ingredient", j).get("Amount"))),
          "Mengden på ingrediens nummer" + j + 1 + "er feil. Det som står i teksten er "
              + readRecipeFile("testFile2", "ingredient", j).get("Amount") + "men det skal vare "
              + ingredient.getAmount());
      assertEquals(ingredient.getUnit(), (String) readRecipeFile("testFile2", "ingredient", j).get("Unit"),
          "Uniten på ingrediens nummer" + j + 1 + "er feil. Det som står i teksten er "
              + (String) readRecipeFile("testFile2", "ingredient", j).get("Unit") + "men det skal vare "
              + ingredient.getUnit());
      j++;
    }

  }

  @Test
  public void testReadRecipeFromFile() throws IOException {
    Cookbook newCookbook = new Cookbook();
    filehandler.readRecipesFromFile("test.json", newCookbook);
    assertEquals(cookbook.getName(), newCookbook.getName(),
        "Leste feil navn fra fil. Skulle være " + cookbook.getName() + ", men var " + newCookbook.getName());

    int i = 0;
    for (Recipe recipe : cookbook.getRecipes()) {
      assertEquals(recipe.getName(), newCookbook.getRecipes().get(i).getName(),
          "Leste feil navn fra fil på oppskrift. Skulle vaere " + recipe.getName() + ", men var "
              + newCookbook.getRecipes().get(i).getName());
      assertEquals(recipe.getPortions(), newCookbook.getRecipes().get(i).getPortions(),
          "Leste feil porsjon fra fil på " + recipe.getName() + ". Skulle vaere " + recipe.getPortions() + ", men var "
              + newCookbook.getRecipes().get(i).getPortions());
      assertEquals(recipe.getDescription(), newCookbook.getRecipes().get(i).getDescription(),
          "Leste feil porsjon fra fil på " + recipe.getName() + ". Skulle væaee " + recipe.getDescription()
              + ", men var " + newCookbook.getRecipes().get(i).getDescription());
      int j = 0;
      for (Ingredient ingredient : recipe.getIngredients()) {

        assertEquals(ingredient.getName(), newCookbook.getRecipes().get(i).getIngredients().get(j).getName(),
            "Leste feil ingrediens fra fil på " + recipe.getName() + ". Skulle vaere " + ingredient.getName()
                + ", men var" + newCookbook.getRecipes().get(i).getIngredients().get(j).getName());
        assertEquals(ingredient.getAmount(), newCookbook.getRecipes().get(i).getIngredients().get(j).getAmount(),
            "Leste feil mengde av" + ingredient.getName() + "fra fil på " + recipe.getName() + ". Skulle vaere "
                + ingredient.getAmount() + ", men var"
                + newCookbook.getRecipes().get(i).getIngredients().get(j).getAmount());
        assertEquals(ingredient.getUnit(), newCookbook.getRecipes().get(i).getIngredients().get(j).getUnit(),
            "Leste feil unit på " + ingredient.getName() + " fra fil på " + recipe.getName() + ". Skulle vaere "
                + ingredient.getUnit() + ", men var "
                + newCookbook.getRecipes().get(i).getIngredients().get(j).getUnit());
        j++;
      }
      i++;
    }

  }

}