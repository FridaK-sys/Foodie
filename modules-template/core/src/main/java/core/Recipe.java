package core;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private String name;
    private List<Ingredient> ingredients;
    private int portions;

    Recipe(String name, int portions, List<Ingredient> ingredients) {
        setName(name);
        setPortions(portions);
        this.name = name;
        this.portions = portions;
        this.ingredients = new ArrayList<>(ingredients);
    }

    Recipe(String name, int portions){
        setName(name);
        setPortions(portions);
        this.name = name;
        this.portions = portions;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (!name.matches("^[ÆØÅæøåa-zA-Z0-9\s]+{1, 20}$")){
            throw new IllegalArgumentException("Invalid name");
        }
        this.name = name;
    }

    public getPortions() {
        return this.portions;
    }

    public void setPortions(int portions){
        if (portions <= 0){
            throw new IllegalArgumentException("Portions must be more than 0");
        }
        this.portions = portions;
        ingredients.stream().forEach(i -> i.setAmount(i.getAmount() * portions));
    }


    public void addIngredient(Ingredient ingredient) {
        if (!ingredients.contains(ingredient)){
            ingredients.add(ingredient);
        }
    }


    public void removeIngredient(Ingredient ingredient){
        if (!ingredients.contains(ingredient)){
            throw new IllegalArgumentException(this.name + "does not contain this ingredient");
        }
        ingredients.remove(ingredient);
    }

    public void removeIngredient(String name) {
        for (Ingredient i: ingredients) {
            if (i.getName().equals(name)){
                ingredients.remove(i);
            }
        }
    }

  

}
