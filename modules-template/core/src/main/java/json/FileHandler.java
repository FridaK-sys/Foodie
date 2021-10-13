package json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.FileNameMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.module.SimpleModule;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;

public class FileHandler {

  // gjorde en endring siden scanner.nextLine ikke deler på /n
  // endret til å dele opp navn, porsjoner, ingredienser og beskrivelse på ;
  // Ingredienser deles opp med & og de tre delene de består av med :
  public void writeRecipesToFile(String filename, Cookbook cookbook) throws IOException {
    // @SuppressWarnings("unchecked")
    JSONArray recipes = new JSONArray();
    JSONObject mainObj = new JSONObject();

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
    for (Ingredient ingredient : recipe.getIngredients()) {
      JSONObject ing = new JSONObject();
      ing.put("Name", ingredient.getName());
      ing.put("Amount", ingredient.getAmount());
      ing.put("Unit", ingredient.getUnit());

      res.put("Name", recipe.getName());
      res.put("Portions", recipe.getPortions());
      res.put("Ingredients", ing);

    }
    // Write JSON file
    try (Writer file = new OutputStreamWriter(new FileOutputStream(filename), "UTF-8")) {
      file.write(res.toJSONString());
      file.flush();

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void readRecipesFromFile(String filename, Cookbook cookbook) throws FileNotFoundException {
    // JSON parser object to parse read file
    JSONParser jsonParser = new JSONParser();

    try (InputStream input = new FileInputStream(filename)) { // try (FileReader reader =
                                                              // new FileReader(filename))
      Reader filereader = new InputStreamReader(input, "UTF-8"); // Read JSON file
      Object obj = jsonParser.parse(filereader); // jsonParser.parse(new
                                                 // FileReader(filename));
      JSONObject Jobj = (JSONObject) obj;
      JSONArray recipeList = (JSONArray) Jobj.get("Recipes");

      for (int i = 0; i < recipeList.size(); i++) {
        JSONObject rec = (JSONObject) recipeList.get(i);
        JSONArray ing = (JSONArray) rec.get("Ingredients");
        String name = (String) rec.get("Name");
        Long portionsLong = (Long) rec.get("Portions");
        int portions = portionsLong.intValue();
        Recipe recipe = new Recipe(name, portions);

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

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException {
    Cookbook book = new Cookbook();
    Cookbook book2 = new Cookbook();
    Recipe recipe1 = new Recipe("recipe1", 2);
    recipe1.addIngredient(new Ingredient("Fisk", 3, "dl"));
    recipe1.addIngredient(new Ingredient("Gulrot", 2, "dl"));
    recipe1.setDescription("testin");

    Recipe recipe2 = new Recipe("recipe2", 2);
    recipe2.addIngredient(new Ingredient("Fisk", 3, "dl"));
    recipe2.setDescription("hello");

    book.addRecipe(recipe1);
    book.addRecipe(recipe2);

    FileHandler filehandler = new FileHandler();
    filehandler.writeRecipesToFile("test", book);

    filehandler.readRecipesFromFile("test", book2);
    System.out.println(book2);
  }
}
