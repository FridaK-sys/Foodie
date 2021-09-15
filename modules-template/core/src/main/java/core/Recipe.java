package core;

import core.Cookbook;
import core.Ingredient;

public class Recipe {
    private String name;
    private CollectionIngredient> ingredients;
    private int portions;

    Recipe(String name, Collection<Ingredient> ingredients, int portions) {
        this.name = name;
        this.ingredients = ingredients;
        this.portions = portions;
    }

    public String getName() {
        return name;
    }

    public void addIngredient() {
        ingredients.add(Ingredient);
    }

    public void removeIngredient(String name) {
        for (Ingredient i : ingredients) {
            if (i.getName().equals(name)) {
                    ingredients.remove(i);
                    return;
            }
        }
    }

    public void editPortions(int portion) {
        if (portion <= 0) {
            throw new IllegalArgumentException("Kan ikke sette oppskriften til mindre enn 1");
        }
        oldPortion = this.portion;
        this.portion = portion;
        ingredients.stream().forEach(i -> i.setAmount((i.getAmount()*portion)/oldPortion));
    }
}
