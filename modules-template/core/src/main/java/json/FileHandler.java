package json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;

public class FileHandler {

    // gjorde en endring siden scanner.nextLine ikke deler på /n
	// endret til å dele opp navn, porsjoner, ingredienser og beskrivelse på ;
	//Ingredienser deles opp med & og de tre delene de består av med :
    public void writeRecipesToFile(String filename, Cookbook cookbook) throws IOException	{ //ENDRE POM FIL!!
	//@SuppressWarnings("unchecked")
	    JSONArray recipes = new JSONArray();
    
        for(Recipe recipe: cookbook.getRecipes()) {
			for(Ingredient ingredient : recipe.getIngredients()); {
				JSONObject ing = new JSONObject(); 
				ing.put("Name", ingredient.getName());
				ing.put("Amount", ingredient.getAmount());
				ing.put("Unit", ingredient.getUnit());

			}
			JSONObject recipe = new JSONObject();
			recipe.put("Name", recipe.getName());
			recipe.put("Portions", recipe.getPortions());
			recipe.put("Ingrediens", ing); //kanskje parantes
			recipes.add(recipe);
			
			
			
		}
        //Write JSON file
        try (FileWriter file = new FileWriter(filename)) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(recipes.toJSONString()); 
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






		/*
		try(FileWriter filewrite = new FileWriter(filename)) {
			const cookbook = {
				name: cookbook.getName();
				recipes: JSONArray.toJSONString(cookbook.getRecipes());
				for (Recipe recipe : cookbook.getRecipes()) {
					recipe.getName(): [
						{"Portions":recipe.getPortions() },
					for(Ingredient ingredient : recipe.getIngredients()) {
						{"Ingredient":ingredient.getName(), "Amount":ingredient.getAmount(),"Unit":ingredient.getUnit()}
					
						

					
					}
				  ]
				}
					
					
				}

				

				
				
			}
			*/
		

/*
        int index = 0;
		try (FileWriter fileWriter = new FileWriter(filename)) {
			for (Recipe recipe : cookbook.getRecipes()) {
				String newLine = recipe.getName() + ";" + recipe.getPortions() + ";";
				for (Ingredient ingredient : recipe.getIngredients()) {
					newLine += ingredient.getName() + ":" + ingredient.getAmount() + ":" + ingredient.getUnit() + "&";
				}

				newLine += ";" + recipe.getDescription();
	
				if (index < cookbook.getRecipes().size()) {
					newLine += "\n";

				}
				fileWriter.write(newLine);
				index++;
			}
			fileWriter.close();
		}	catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	*/
/*
	public void writeRecipeToFile(String filename, Recipe recipe) throws IOException  {
		try (FileWriter fileWriter = new FileWriter(filename, true)) {
			String recipeString = recipe.getName() + ";" + recipe.getPortions() + ";";
				for (Ingredient ingredient : recipe.getIngredients()) {
					recipeString += ingredient.getName() + ":" + ingredient.getAmount() + ":" + ingredient.getUnit() + "&";
				}
				if (recipe.getDescription()!= null) {
					recipeString += ";" + recipe.getDescription();
				}
				recipeString += "\n";

				fileWriter.write(recipeString);
				fileWriter.close();
			}	catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}

	
*/
    

	public void readRecipesFromFile(String filename, Cookbook cookbook) throws FileNotFoundException {
		//JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
         
        try (FileReader reader = new FileReader(filename))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONArray recipeList = (JSONArray) obj;
            System.out.println(recipeList);
             
            //Iterate over employee array
            recipeList.forEach( recipe -> parseRecipieListObject( (JSONObject) recipe ) );
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
 
    private static void parseRecipeListObject(JSONObject recipeList) 
    {
        //Get employee object within list
        JSONObject recipe = (JSONObject) recipeList.get("Recipe");
         
        //Get employee first name
        String Name = (String) recipe.get("Name");    
        System.out.println(Name);
         
        //Get employee last name
        String Portions = (String) recipe.get("Portions");  
        System.out.println(Portions);
         
        //Get employee website name
        String Ingredient = (String) recipe.get("Ingredient");    
        System.out.println(Ingredient);
    }

/*
		try (Scanner scanner = new Scanner(new File(filename))) {
			while (scanner.hasNextLine()) {
				String[] recipeInformation = scanner.nextLine().split(";");
				String recipeTitle = recipeInformation[0];
				String portions = recipeInformation[1];
				int intPortions = Integer.parseInt(portions);
				Recipe newRecipe = new Recipe(recipeTitle, intPortions);

				if(!recipeInformation[2].isEmpty())	{

				String[] ingredients = recipeInformation[2].split("&");
				for (String ing : ingredients) {
					String[] ingredientElements = ing.split(":");
				
					Ingredient newIngredient = new Ingredient(ingredientElements[0],
					    Double.parseDouble(ingredientElements[1]), ingredientElements[2]);
					newRecipe.addIngredient(newIngredient);
				}
			}
			
				if (recipeInformation.length == 4)	{
					newRecipe.setDescription(recipeInformation[3]);
				}
				
				cookbook.addRecipe(newRecipe);
			}
			scanner.close();
		}
		*/
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
