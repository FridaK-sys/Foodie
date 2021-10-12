package core;

public class Ingredient {
  private String name;
  private double amount;
  private String unit;

  public Ingredient(String name, double amount, String unit) {
    setName(name);
    setAmount(amount);
    this.unit = unit;
  }

  public Ingredient(String name) {
    setName(name);
    this.amount = 0;
    this.unit = "";
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    if (!name.matches("^[ÆØÅæøåa-zA-Z0-9\\s]{1,20}$")) {
      throw new IllegalArgumentException("Invalid name");
    }
    this.name = name;
  }

  public double getAmount() {
    return this.amount;
  }

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
