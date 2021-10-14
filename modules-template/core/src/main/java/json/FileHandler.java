package json;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;

public class FileHandler {

  public void writeRecipesToFile(String filename, Cookbook cookbook) {
    // @SuppressWarnings("unchecked")
    JSONArray recipes = new JSONArray();
    JSONObject mainObj = new JSONObject();
    mainObj.put("Name", cookbook.getName());

    for (Recipe recipe : cookbook.getRecipes()) {
      JSONArray ingredients = new JSONArray();
      for (Ingredient ingredient : recipe.getIngredients()) {
        JSONObject ing = new JSONObject();
        ing.put("Name", ingredient.getName());
        ing.put("Amount", ingredient.getAmount());
        ing.put("Unit", ingredient.getUnit());
        ingredients.add(ing);

      }
      JSONObject rec = new JSONObject();
      rec.put("Name", recipe.getName());
      rec.put("Portions", recipe.getPortions());
      rec.put("Description", recipe.getDescription());
      rec.put("Ingredients", ingredients);

      recipes.add(rec);

    }
    mainObj.put("Recipes", recipes);
    System.out.println(mainObj);

    // Write JSON file
    try (Writer file = new OutputStreamWriter(new FileOutputStream(filename), "UTF-8")) {
      file.write(mainObj.toJSONString());
      file.flush();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void writeRecipeToFile(String filename, Recipe recipe) {
    JSONObject res = new JSONObject();
    res.put("Name", recipe.getName());
    res.put("Portions", recipe.getPortions());
    res.put("Description", recipe.getDescription());
    JSONArray ingredients = new JSONArray();
    for (Ingredient ingredient : recipe.getIngredients()) {
      JSONObject ing = new JSONObject();
      ing.put("Name", ingredient.getName());
      ing.put("Amount", ingredient.getAmount());
      ing.put("Unit", ingredient.getUnit());
      ingredients.add(ing);

    }
    res.put("Ingredients", ingredients);
    // Write JSON file
    try (Writer file = new OutputStreamWriter(new FileOutputStream(filename), "UTF-8")) {
      file.write(res.toJSONString());
      file.flush();

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void readRecipesFromFile(String filename, Cookbook cookbook) {
    // JSON parser object to parse read file
    JSONParser jsonParser = new JSONParser();

    try (InputStream input = new FileInputStream(filename)) { // try (FileReader reader =
                                                              // new FileReader(filename))
      Reader filereader = new InputStreamReader(input, "UTF-8"); // Read JSON file
      Object obj = jsonParser.parse(filereader); // jsonParser.parse(new
                                                 // FileReader(filename));
      JSONObject Jobj = (JSONObject) obj;
      cookbook.setName((String) Jobj.get("Name"));
      JSONArray recipeList = (JSONArray) Jobj.get("Recipes");

      for (int i = 0; i < recipeList.size(); i++) {
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
        cookbook.addRecipe(recipe);

      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException {
    /*
     * Cookbook book = new Cookbook(); Cookbook book2 = new Cookbook(); Recipe
     * recipe1 = new Recipe("recipe1", 2); recipe1.addIngredient(new
     * Ingredient("Fisk", 3, "dl")); recipe1.addIngredient(new Ingredient("Gulrot",
     * 2, "dl")); recipe1.setDescription("testin");
     * 
     * Recipe recipe2 = new Recipe("recipe2", 2); recipe2.addIngredient(new
     * Ingredient("Fisk", 3, "dl")); recipe2.setDescription("hello");
     * 
     * book.addRecipe(recipe1); book.addRecipe(recipe2);
     * 
     * FileHandler filehandler = new FileHandler();
     * filehandler.writeRecipesToFile("test", book);
     * 
     * filehandler.readRecipesFromFile("test", book2); System.out.println(book2);
     */

    FileHandler filehandler = new FileHandler();
    Ingredient ingredient1 = new Ingredient("Mel", 200, "g");
    Ingredient ingredient2 = new Ingredient("Egg", 2, "stk");
    Ingredient ingredient3 = new Ingredient("Sukker", 1.5, "dl");
    Ingredient ingredient4 = new Ingredient("Kakao", 1, "dl");
    List<Ingredient> ingredients = new ArrayList<Ingredient>();
    ingredients.add(ingredient1);
    ingredients.add(ingredient2);
    List<Ingredient> ingredients2 = new ArrayList<Ingredient>();
    ingredients2.add(ingredient3);
    ingredients2.add(ingredient4);
    Recipe recipe = new Recipe("Bløtkake", "Den beste oppskriften på bløtkake!", 1, ingredients);
    Recipe recipe2 = new Recipe("Kakao", "Varm og god dessert!", 1, ingredients2);
    List<Recipe> recipeList = new ArrayList<Recipe>();
    recipeList.add(recipe);
    recipeList.add(recipe2);
    Cookbook cookbook = new Cookbook("Kokebook", recipeList);

    filehandler.writeRecipesToFile("testFile", cookbook);
  }
}
