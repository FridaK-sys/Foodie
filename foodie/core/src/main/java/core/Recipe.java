package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Recipe containing a name, description, ingredients, portions, favorite tag
 * and label.
 */
public class Recipe {

  private String name;
  private String description;
  private int portions;
  private boolean fav;
  private String label;
  private List<Ingredient> ingredients = new ArrayList<>();
  public static final List<String> labels = Collections.unmodifiableList(new ArrayList<String>() {
    {
      add("Breakfast");
      add("Lunch");
      add("Dinner");
      add("Dessert");
    }
  });

  /**
   * Constructor for recipe with name, description, portions and ingredients.
   * 
   * @param name        name of recipe
   * 
   * @param description description of recipe
   * 
   * @param portions    number of portions
   * 
   * @param ingredients list of ingredient
   * 
   */
  public Recipe(String name, String description, int portions, List<Ingredient> ingredients) {
    setName(name);
    setPortions(portions);
    this.description = description;
    this.ingredients = new ArrayList<Ingredient>(ingredients);
    this.fav = false;
    this.label = "";
  }

  // denne må fjernes
  public Recipe(String name, int portions) {
    setName(name);
    this.portions = portions;
    this.description = "nothing here...";
    this.ingredients = new ArrayList<Ingredient>();
    this.fav = false;
    this.label = "";
  }

  /**
   * Constructor for a empty recipe.
   * 
   * @param name name of recipe
   * 
   */
  public Recipe(String name) {
    setName(name);
    this.portions = 0;
    this.description = "nothing here...";
    this.ingredients = new ArrayList<Ingredient>();
    this.fav = false;
    this.label = "";
  }

  public String getName() {
    return this.name;
  }

  /**
   * Sets name of recipe.
   * 
   * @param name name of recipe
   * 
   * @throws IllegalArgumentException if param contains other characters than
   *                                  letters and numbers
   * 
   */
  public void setName(String name) {
    if (!name.matches("^[ÆØÅæøåa-zA-Z0-9\\s]+$")) {
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

  /**
   * Sets portions. Updates the amount of each ingredient to fit with portions.
   * 
   * @param portions number of portions
   * 
   * @throws IllegalArgumentException if param is negative integer
   * 
   */
  public void setPortions(int portions) {
    if (portions < 0) {
      throw new IllegalArgumentException("Portions cannot be negative");
    }
    ingredients.stream().forEach(i -> i.setAmount((double) i.getAmount() / this.portions * portions));
    this.portions = portions;
  }

  public List<Ingredient> getIngredients() {
    return new ArrayList<>(ingredients);
  }

  /**
   * Add ingredient to recipe.
   * 
   * @param ingredient ingredient to add
   * 
   * @throws IllegalArgumentException if list already contains ingredient
   * 
   */
  public void addIngredient(Ingredient ingredient) {
    if (!ingredients.contains(ingredient)) {
      ingredients.add(ingredient);
    } else {
      throw new IllegalArgumentException("Recipe contains this ingredient");
    }
  }

  /**
   * Remove ingredient from recipe.
   * 
   * @param index index in ingredientList of ingredient to be removed
   * 
   * @throws IllegalArgumentException if index is larger than size of
   *                                  ingredientList
   * 
   */
  public void removeIngredient(int index) {
    ingredients.remove(index);
  }

  public boolean getFav() {
    return this.fav;
  }

  public void setFav(boolean isFav) {
    this.fav = isFav;
  }

  public String getLabel() {
    return this.label;
  }

  /**
   * Sets label for recipe.
   * 
   * @param label recipelabel
   * 
   * @throws IllegalArgumentException if label is not valid
   * 
   */
  public void setLabel(String label) {
    if (label.isEmpty()) {
      return;
    } else if (labels.contains(label)) {
      this.label = label;
    } else {
      throw new IllegalArgumentException("Invalid label");
    }
  }
  
  /** 
   * Removes label from recipe.
   */
  public void removeLabel() {
    this.label = "";
  }

  /**
   * Writes name of recipe and ingredients to string.
   * 
   */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    ingredients.stream().forEach(i -> sb.append(i.getName()));
    return getName() + ": " + sb.toString();
  }
}
