# Kildekode for domenelaget

Domenelaget utgjøres av tre klasser:  

- **Cookbook** - en kokebok er representert med navn. Hver kokebok består av en samling med oppskrifter, og metoder for å legge til og fjerne disse. 
- **Recipe** - en oppskrift er representert med navn, beskrivelse og antall porsjoner. Hver oppskrift har en liste med ingredienser, og metoder for å legge til og fjerne disse. 
- **Ingredient** - en ingrediens er representert med navn, mengde og enhet. 

```plantuml
Cookbook "1" -- "*" Recipe 
Recipe "1" -- "*" Ingredient

Cookbook : String name
Cookbook : String getName()
Cookbook : void setName(String)
Cookbook : List<Recipe> getRecipes()
Cookbook : addRecipe(Recipe)
Cookbook : removeRecipe(Recipe)
Cookbook : removeRecipe(String)

Recipe : String name
Recipe : String description
Recipe : int portions
Recipe : String getName()
Recipe : void setName(String)
Recipe : String getDescription()
Recipe : void setDesciption(String)
Recipe : int getPortions()
Recipe : void setPortions(int)
Recipe : List<Ingredient> getIngredients()
Recipe : void addIngredient(Ingredient)
Recipe : void removeIngredient(Ingredient)
Recipe : void removeIngredient(String)

Ingredient : String name
Ingredient : double amount
Ingredient : String unit
Ingredient : String getName()
Ingredient : void setName(String)
Ingredient : double getAmount()
Ingredient : void setAmount(double)
Ingredient : String getUnit()
Ingredient : void setUnit(String)
```
