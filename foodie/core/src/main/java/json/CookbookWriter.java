package json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.Cookbook;
import core.Recipe;
import java.io.IOException;

/*
   * Serializer for Cookbook-class
   */
public class CookbookWriter extends JsonSerializer<Cookbook> {

  /*
   * format: { "name": "...", "recipes": [ ... ] }
   */

  @Override
  public void serialize(Cookbook cookbook, JsonGenerator jsonGen, SerializerProvider serializerProvider)
      throws IOException {
    jsonGen.writeStartObject();
    jsonGen.writeStringField("name", cookbook.getName());
    jsonGen.writeArrayFieldStart("recipes");
    for (Recipe recipe : cookbook.getRecipes()) {
      jsonGen.writeObject(recipe);
    }
    jsonGen.writeEndArray();
    jsonGen.writeEndObject();
  }

}
