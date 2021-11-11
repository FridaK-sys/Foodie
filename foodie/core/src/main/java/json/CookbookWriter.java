package json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;

import java.io.IOException;

public class CookbookWriter extends JsonSerializer<Cookbook> {

  /*
   * format: { "lists": [ ... ] }
   */

  @Override
  public void serialize(Cookbook cookbook, JsonGenerator jsonGen, SerializerProvider serializerProvider)
      throws IOException {
    jsonGen.writeStartObject();
    jsonGen.writeStringField("Cookbookname", cookbook.getName());
    jsonGen.writeArrayFieldStart("Recipes");
    for (Recipe recipe : cookbook.getRecipes()) {
      jsonGen.writeStartObject();
      jsonGen.writeStringField("name", recipe.getName());
      jsonGen.writeStringField("description", recipe.getDescription());
      jsonGen.writeNumberField("portions", recipe.getPortions());
      jsonGen.writeBooleanField("fav", recipe.getFav());
      jsonGen.writeStringField("label", recipe.getLabel());
      jsonGen.writeArrayFieldStart("Ingredients");
      for (Ingredient ing : recipe.getIngredients()) {
        jsonGen.writeStartObject();
        jsonGen.writeStringField("name", ing.getName());
        jsonGen.writeNumberField("amount", ing.getAmount());
        jsonGen.writeStringField("unit", ing.getUnit());
      }
      jsonGen.writeEndArray();
      jsonGen.writeEndObject();
    }
    jsonGen.writeEndArray();
    jsonGen.writeFieldName("Cookbook");
    jsonGen.writeEndObject();
  }

}
