package core;

import java.util.ArrayList;
import java.util.List;

public class Cookbook {

    private String name;
    private List<Recipe> recipes = new ArrayList<>();

    public Cookbook(String name, List<Recipe> recipes) {
        setName(name);
        this.name = name;
        this.recipes = recipes;
    }

    public Cookbook() {
        this.name = "Ny kokebok";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!name.matches("^[ÆØÅæøåa-zA-Z0-9\\s]{1,20}$")) {
            throw new IllegalArgumentException("Invalid name");
        }
        this.name = name;
    }

    public List<Recipe> getRecipes() {
        return new ArrayList<>(recipes);
    }

    public void addRecipe(Recipe recipe) {
        if (!recipes.contains(recipe)) {
            recipes.add(recipe);
        }
    }

    public void removeRecipe(Recipe recipe) {
        if (!recipes.contains(recipe)) {
            throw new IllegalArgumentException(name + "does not contain this recipe");
        }
        recipes.remove(recipe);
    }

    public void removeRecipe(String name) {
        for (Recipe r : recipes) {
            if (r.getName().equals(name)) {
                recipes.remove(r);
            }
        }
    }
     
    public String toString(){
        String ing = "";
        for (Recipe r : recipes){
            ing += r.toString();
        }
        return ing;
    }

}
