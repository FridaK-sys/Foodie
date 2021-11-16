package json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.Ingredient;
import core.Recipe;
import java.io.IOException;

class RecipeWriter extends JsonSerializer<Recipe> {

  /*
   * format: { "name": "...","description": "...","portions": "...","fav":
   * "...","label": "...", "ingredients": [ ... ] }
   */

  @Override
  public void serialize(Recipe recipe, JsonGenerator jsonGen, SerializerProvider serializerProvider)
      throws IOException {
    jsonGen.writeStartObject();
    jsonGen.writeStringField("name", recipe.getName());
    jsonGen.writeStringField("description", recipe.getDescription());
    jsonGen.writeNumberField("portions", recipe.getPortions());
    jsonGen.writeBooleanField("fav", recipe.getFav());
    jsonGen.writeStringField("label", recipe.getLabel());
    jsonGen.writeArrayFieldStart("ingredients");
    for (Ingredient ingredient : recipe.getIngredients()) {
      jsonGen.writeObject(ingredient);
    }
    jsonGen.writeEndArray();
    jsonGen.writeEndObject();
  }
}
