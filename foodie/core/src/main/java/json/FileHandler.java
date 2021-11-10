package json;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;
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

public class FileHandler {

	public void writeRecipesToFile(String filename, Cookbook cookbook) {
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
			rec.put("Favorite", recipe.getFav());
			rec.put("Label", recipe.getLabel());
			rec.put("Portions", recipe.getPortions());
			rec.put("Description", recipe.getDescription());
			rec.put("Ingredients", ingredients);

			recipes.add(rec);

		}

		mainObj.put("Recipes", recipes);

		try (Writer file = new OutputStreamWriter(new FileOutputStream(filename), "UTF-8")) { // Write JSON file
			file.write(mainObj.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeRecipeToFile(String filename, Recipe recipe) {
		JSONObject res = new JSONObject();
		res.put("Name", recipe.getName());
		res.put("Favorite", recipe.getFav());
		res.put("Label", recipe.getLabel());
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

		try (Writer file = new OutputStreamWriter(new FileOutputStream(filename), "UTF-8")) { // Write JSON file
			file.write(res.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void readRecipesFromFile(String filename, Cookbook cookbook) {
		JSONParser jsonParser = new JSONParser(); // JSON parser object to parse read file

		try (InputStream input = new FileInputStream(filename)) {
			Reader filereader = new InputStreamReader(input, "UTF-8");
			Object obj = jsonParser.parse(filereader);

			JSONObject Jobj = (JSONObject) obj;
			cookbook.setName((String) Jobj.get("Name"));
			JSONArray recipeList = (JSONArray) Jobj.get("Recipes");

			for (int i = 0; i < recipeList.size(); i++) {
				JSONObject rec = (JSONObject) recipeList.get(i);
				JSONArray ing = (JSONArray) rec.get("Ingredients");
				String name = (String) rec.get("Name");
				Boolean fav = (Boolean) rec.get("Favorite");
				String label = (String) rec.get("Label");
				Long portionsLong = (Long) rec.get("Portions");
				int portions = portionsLong.intValue();
				String description = (String) rec.get("Description");
				Recipe recipe = new Recipe(name, portions);
				recipe.setDescription(description);

				if (fav) {
					recipe.setFav();
				}
				if (!label.equals("")) {
					recipe.setLabel(label);
				}

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

	public void replaceRecipeInFile(Recipe recipe, int index) {
		Cookbook tempBook = new Cookbook();
		readRecipesFromFile("src/main/resources/ui/test.txt", tempBook);
		List<Recipe> recipes = new ArrayList<>(tempBook.getRecipes());
		recipes.remove(index);
		recipes.add(index, recipe);
		Cookbook returnBook = new Cookbook("Recipes", recipes);
		writeRecipesToFile("src/main/resources/ui/test.txt", returnBook);
	}
}
