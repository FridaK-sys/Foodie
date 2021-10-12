package foodie.core;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private String name;
    private String description;
    private List<Ingredient> ingredients = new ArrayList<>();
    private int portions;

    public Recipe(String name, String description, int portions, List<Ingredient> ingredients) {
        setName(name);
        setPortions(portions);
        this.description = description;
        this.ingredients = new ArrayList<>(ingredients);
    }

    public Recipe(String name, int portions) {
        setName(name);
        setPortions(portions);
        this.description = "nothing here...";
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if(name.isBlank()){
            throw new IllegalArgumentException("Invalid name"); 
        }
        if (!name.matches("^[ÆØÅæøåa-zA-Z0-9\\s]{1,20}$")) {
            throw new IllegalArgumentException("Invalid name");
        }
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPortions() {
        return this.portions;
    }

    public void setPortions(int portions) {
        if (portions <= 0) {
            throw new IllegalArgumentException("Portions must be more than 0");
        }
        ingredients.stream().forEach(i -> i.setAmount((double) i.getAmount() / this.portions * portions));
        this.portions = portions;
    }

    public List<Ingredient> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    public void addIngredient(Ingredient ingredient) {
        if (!ingredients.contains(ingredient)) {
            ingredients.add(ingredient);
        } else {
            throw new IllegalArgumentException("Oppskriften inneholder denne ingrediensen");
        }
    }

    public void removeIngredient(Ingredient ingredient) {
        if (!ingredients.contains(ingredient)) {
            throw new IllegalArgumentException(this.name + "does not contain this ingredient");
        }
        ingredients.remove(ingredient);
    }

    public void removeIngredient(String name) {
        for (Ingredient i : ingredients) {
            if (i.getName().equals(name)) {
                ingredients.remove(i);
            }
        }
    }

    public String toString() {
        return this.name;
    }

}
