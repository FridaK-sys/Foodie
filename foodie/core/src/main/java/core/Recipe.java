package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A recipe containing a name, description, ingredients, portions, fav and
 * label.
 */
public class Recipe {

  private Long id;
  private String name;
  private String description;
  private List<Ingredient> ingredients = new ArrayList<>();
  private int portions;
  private boolean fav = false;
  private String label = "";
  static final List<String> allowedLabels = Arrays.asList("Breakfast", "Lunch", "Dinner");

  /**
   * Constructor for recipe with name, description, portions and ingredients
   * 
   * @param name
   * @param description
   * @param portions
   * @param ingredients
   */
  public Recipe(String name, String description, int portions, List<Ingredient> ingredients) {
    setName(name);
    setPortions(portions);
    this.description = description;
    this.ingredients = new ArrayList<>(ingredients);
  }

  /**
   * Constructor for recipe with name and portions. Description is automatically
   * set to "nothing here..."
   * 
   * @param name
   * @param portions
   */
  public Recipe(String name, int portions) {
    setName(name);
    setPortions(portions);
    this.description = "nothing here...";
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  /**
   * Sets name of recipe if param consists of 1-20 characters (letters and
   * numbers).
   * 
   * @param name
   * @throws IllegalArgumentException if param is not 1-20 characters long or
   *                                  contains other characters than letters and
   *                                  numbers
   */
  public void setName(String name) {
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

  /**
   * Sets portions if param is a positive integer. Updates the amount of each
   * ingredient to fit with portions.
   * 
   * @param portions
   * @throws IllegalArgumentException if param is negative integer
   */
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

  /**
   * Adds ingredient to ingredientlist if it is not already there
   * 
   * @param ingredient
   * @throws IllegalArgumentException if list already contains ingredient
   */
  public void addIngredient(Ingredient ingredient) {
    if (!ingredients.contains(ingredient)) {
      ingredients.add(ingredient);
    } else {
      throw new IllegalArgumentException("Recipe contains this ingredient");
    }
  }

  /**
   * Remove ingredient from ingredientList if index is in the list
   * 
   * @param index
   * @throws IllegalArgumentException if index is larger than size of
   *                                  ingredientList
   */
  public void removeIngredient(int index) {
    if (index <= ingredients.size()) {
      ingredients.remove(index);
    } else {
      throw new IllegalArgumentException();
    }
  }

  public void setFav() {
    this.fav = true;
  }

  public void removeFav() {
    this.fav = false;
  }

  public boolean getFav() {
    return this.fav;
  }

  /**
   * Sets label of recipe if label is valid
   * 
   * @param label
   * @throws IllegalArgumentException if label is not valid
   */
  public void setLabel(String label) {
    if (allowedLabels.contains(label)) {
      this.label = label;
    } else {
      throw new IllegalArgumentException("Label has to be either Frokost, Lunsj or Middag");
    }
  }

  public void removeLabel() {
    this.label = "";
  }

  public String getLabel() {
    return this.label;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    ingredients.stream().forEach(i -> sb.append(i.getName()));
    return getName() + ": " + sb.toString();
  }
}
