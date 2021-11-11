package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An ingredient containing name, amount and unit.
 */

public class Ingredient {

  private String name;
  private double amount;
  private String unit;
  public static final List<String> units = new ArrayList<>(Arrays.asList("g", "kg", "dl", "tbs", "ts"));

  /**
   * Constructor for ingredient with name, amount and unit
   * 
   * @param name
   * @param amount
   * @param unit
   */
  public Ingredient(String name, double amount, String unit) {
    setName(name);
    setAmount(amount);
    this.unit = unit;
  }

  /**
   * Constructor for ingredient with name
   * 
   * @param name
   */
  public Ingredient(String name) {
    setName(name);
    this.amount = 0;
    this.unit = "";
  }

  public String getName() {
    return this.name;
  }

  /**
   * Sets name of ingredient if param consists of letters and numbers
   * 
   * @param name
   * @throws IllegalArgumentException if param contains other characters than
   * letters and numbers
   */
  public void setName(String name) {
    if (!name.matches("^[ÆØÅæøåa-zA-Z0-9\\s]+$")) {
      throw new IllegalArgumentException("Invalid name");
    }
    this.name = name;
  }

  public double getAmount() {
    return this.amount;
  }

  /**
   * Sets amount if param is larger than zero
   * 
   * @param amount
   * @throws IllegalArgumentException if param is a negative integer or zero
   */
  public void setAmount(double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be more than 0");
    }
    this.amount = amount;
  }

  public String getUnit() {
    return this.unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public String toString() {
    return getName() + ": " + Double.toString(amount) + getUnit();
  }

}
