package core;

public class Ingredient {

    private String name;
    private double amount;
    private String unit;

    public Ingredient(String name, double amount, String unit) {
        setName(name);
        setAmount(amount);
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (!name.matches("^[ÆØÅæøåa-zA-Z0-9\s]+{1,20}$")) {
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
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String toString() {
        if (amount >= 0 && unit != null) {
            if (amount % 1 != 0) {
                String amounInString = String.format("%.1f", amount);
                return "" + amounInString + "    " + unit + "\t\t" + name;
            } else {
                String amounInString = String.format("%.0f", amount);
                return "" + amounInString + "    " + unit + "\t\t" + name;
            }

        } else {
            return name + "";
        }
    }

}