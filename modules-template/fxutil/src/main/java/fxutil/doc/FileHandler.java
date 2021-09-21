public class FileHandler {

    @Override
    public void writeRecipeToFile(Strign filename, Cookbook cookbook) throws IOException {
        int index = 0;
		try (FileWriter fileWriter = new FileWriter(filename)) {
			for (Recipe recipe : cookbook.getRecipes()) {
				String newLine = recipe.getName() + ";" + recipe.getPortions() + "/n";
				for (Ingredient ingredient : recipe.getIngredients()) {
					newLine += ingredient.getName() + ";" + ingredient.getAmount() + ";" + ingredient.getUnit() + "&";
				}

				newLine += "/n" + recipe.getDescription();
	
				if (index < cookbook.getRecipes().size()) {
					newLine += "\n" + "#"; //sjekk om funker #

				}
				fileWriter.write(newLine);
				index++;
			}
			fileWriter.close();
		}
	}
     
    }

    @Override
	public void readRecipeFromFile(String filename, Cookbook cookbook) throws FileNotFoundException {
		try (Scanner scanner = new Scanner(new File(filename))) {
			while (scanner.hasNextLine()) {
				String recipe = scanner.nextLine();
				String[] checkSteps = recipe.split("#");

				String[] recipeElements = checkSteps[0].split("/n"); //før ; 
                String[] recipeInformation = checkSteps[0].split(";");
				String recipeTitle = recipeInformation[0];
				String portions = recipeInformation[1];
				int intPortions = Integer.parseInt(portions);
				Recipe newRecipe = new Recipe(recipeTitle, intPortions);
			
				newRecipe.setDescription(recipeElements[2]);
				
				String[] ingredients = recipeElements[1].split("&");
				for (String ing : ingredients) {
					String[] ingredientElements = ing.split(";");
					
					Ingredient newIngredient = new Ingredient(ingredientElements[0],
					    Double.parseDouble(ingredientElements[1]), ingredientElements[2]);
					newRecipe.addIngredient(newIngredient);
					

				}
				if (checkSteps.length > 2) {
					String[] steps = checkSteps[1].split("@");
					List<String> stepsAsList = Arrays.asList(steps);
					newRecipe.setSteps(stepsAsList);

					String[] tags = (checkSteps[2].split("[\\[\\]]")[1]).split(", ");
					int[] binaryTags = new int[5];
					for (int i = 0; i < 5; i++) {
						binaryTags[i] = Integer.parseInt(tags[i]);
					}
					newRecipe.setTags(binaryTags);
				} else {
					String[] tags = (checkSteps[1].split("[\\[\\]]")[1]).split(", ");
					int[] binaryTags = new int[5];
					for (int i = 0; i < 5; i++) {
						binaryTags[i] = Integer.parseInt(tags[i]);
					}
					newRecipe.setTags(binaryTags);
				}

				cookbook.addRecipe(newRecipe);

			}
//			scanner.close();
		}
	}
    
}
