package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;

public class FileHandler {

    // gjorde en endring siden scanner.nextLine ikke deler på /n
	// endret til å dele opp navn, porsjoner, ingredienser og beskrivelse på ;
	//Ingredienser deles opp med & og de tre delene de består av med :
    public void writeRecipesToFile(String filename, Cookbook cookbook) throws IOException	{
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

	public void writeRecipeToFile(String filename, Recipe recipe) throws IOException  {
		try (FileWriter fileWriter = new FileWriter(filename, true)) {
			String recipeString = recipe.getName() + ";" + recipe.getPortions() + ";";
				for (Ingredient ingredient : recipe.getIngredients()) {
					recipeString += ingredient.getName() + ":" + ingredient.getAmount() + ":" + ingredient.getUnit() + "&";
				}
				if (recipe.getDescription()!= null) {
					recipeString += ";" + recipe.getDescription();
				}

				fileWriter.write(recipeString);
				fileWriter.close();
			}	catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}


	public void readRecipesFromFile(String filename, Cookbook cookbook) throws FileNotFoundException {
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
