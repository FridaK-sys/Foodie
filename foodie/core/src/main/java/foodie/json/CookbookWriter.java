package foodie.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import foodie.core.Cookbook;
import foodie.core.Recipe;
import java.io.IOException;

/**
 * Serializer for Cookbook-class.
 */
public class CookbookWriter extends JsonSerializer<Cookbook> {

  /*
   * format: { "recipes": [ ... ] }
   */

  @Override
  public void serialize(Cookbook cookbook, JsonGenerator jsonGen,
      SerializerProvider provider) throws IOException {
    jsonGen.writeStartObject();
    jsonGen.writeArrayFieldStart("recipes");
    for (Recipe recipe : cookbook.getRecipes()) {
      jsonGen.writeObject(recipe);
    }
    jsonGen.writeEndArray();
    jsonGen.writeEndObject();
  }

}
