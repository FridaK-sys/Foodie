package core;

import java.util.ArrayList;
import java.util. Collection;

public class Cookbook{
    
    private String name;
    private Collection<Recipe> recipes = new ArrayList<Recipe>();

    public Cookbook(String name, Collection<Recipe> recipes){
        if (!name.matches("^[ÆØÅæøåa-zA-Z0-9\\s]+{1, 20}$")){
            throw new IllegalArgumentException("Invalid name");
        }
        this.name = name;
        this.recipes = new ArrayList<>(recipes);
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        if (!name.matches("^[ÆØÅæøåa-zA-Z0-9\\s]+{1, 20}$")){
            throw new IllegalArgumentException("Invalid name");
        }
        this.name = name;
    }

    public Collection<Recipe> getRecipes(){
        return new ArrayList<>(recipes);
    }

    public addRecipe(Recipe recipe){
        recipes.add(recipe);
    }

    public removeRecipe(Recipe recipe){
        if (!recipes.contains(recipe)){
            throw new IllegalArgumentException(name + "does not contain this recipe");
        }
        recipes.remove(recipe);
    }

    public boolean isInCookbook(String recipeName){
        return recipes.stream().anyMatch(recipe -> recipe.getName().equals(recipeName));
    }

}
