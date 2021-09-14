package core;
public class Ingredient {

    private String name;
    private double amount;
    private String unit;

    public Ingredient(String name, double amount, String unit) {
        if(amount < 0) {
            throw new IllegalArgumentException("Unvalid amount or unit");
        }
        if(amount == 0) {
            this.amount = amount;
            this.unit = null;
        }
        if(!validName(name)|| !validName(unit)) {
            throw new IllegalArgumentException("Ingredient name can only contain letters");
        }
        this.name = name;
        this.amount = amount;
        this.double = double; 
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;

    }

    private boolean validName(String name) {
        return name.matches("^[ÆØÅæøåa-zA-Z\\s]+$")); //test dette


    }

    public double getAmount() {
        return this.amount;
        }

    public void setAmount(double amount) {
        if(amount < 0) {
            throw new IllegalArgumentException("Amount must be a positive value");
        }
        if(amount == 0) {
            this.unit = null;
        }
        this.amount = amount;
    }

    public void setUnit(String unit) {
        if (!validName(unit)) {
            throw new IllegalArgumentException("Unit can only contain letters");
        }
        this.unit = unit;
    }

    public String getUnit() {
        return this.unit;
    }

    public String toString() {
		if (amount != 0 && unit != null) {
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