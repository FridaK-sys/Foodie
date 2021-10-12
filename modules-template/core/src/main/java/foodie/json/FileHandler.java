package foodie.json;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;


//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.module.SimpleModule;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import foodie.core.Cookbook;
import foodie.core.Ingredient;
import foodie.core.Recipe;

public class FileHandler {

	

    // gjorde en endring siden scanner.nextLine ikke deler på /n
	// endret til å dele opp navn, porsjoner, ingredienser og beskrivelse på ;
	//Ingredienser deles opp med & og de tre delene de består av med :
    public void writeRecipesToFile(String filename, Cookbook cookbook) throws IOException	{ 
	//@SuppressWarnings("unchecked")
	    JSONArray recipes = new JSONArray();
		JSONObject mainObj = new JSONObject();

		for(Recipe recipe : cookbook.getRecipes()){
			for(Ingredient ingredient : recipe.getIngredients()) {
				JSONObject ing = new JSONObject();
				ing.put("Name", ingredient.getName());
				ing.put("Amount", ingredient.getAmount());
				ing.put("Unit", ingredient.getUnit());

				JSONObject rec = new JSONObject();
				rec.put("Name", recipe.getName());
				rec.put("Portions", recipe.getPortions());
				rec.put("Ingrediens", ing);

				recipes.add(rec);
				mainObj.put("Recipes", recipes);
				
			}
		}
  		
        //Write JSON file
        try (Writer file = new OutputStreamWriter(new FileOutputStream(filename), "UTF-8")) { 
            //We can write any JSONArray or JSONObject instance to the file
            file.write(mainObj.toJSONString());  //gjør om til JSON toString?
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
    	}
	}

	public void readRecipesFromFile(String filename, Cookbook cookbook) throws FileNotFoundException {
		//JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
         
        try (FileReader reader = new FileReader(filename))
        {
            //Read JSON file
            Object obj = jsonParser.parse(new FileReader(filename));
			JSONObject Jobj = (JSONObject) obj;
 
            JSONArray recipeList = (JSONArray) Jobj.get("Recipes");

			Iterator<JSONObject> iterator = recipeList.iterator();
			while(iterator.hasNext()) {
				System.out.println(iterator.next());
			}

        } 
         catch (Exception e) {
            e.printStackTrace();
        } 
    }

    
		public static void main(String[] args) throws IOException {
			Cookbook book = new Cookbook();
			Cookbook book2 = new Cookbook();
			Recipe recipe1 = new Recipe("recipe1", 2);
			recipe1.addIngredient(new Ingredient("Fisk", 3, "dl"));
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


	
	

	
    

