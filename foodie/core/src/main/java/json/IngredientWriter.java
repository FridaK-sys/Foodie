package json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.Ingredient;
import java.io.IOException;

class IngredientWriter extends JsonSerializer<Ingredient> {

  /*
   * format: { "name": "...", "amount": "...", "unit": "..."}
   */

  @Override
  public void serialize(Ingredient ingredient, JsonGenerator jsonGen, SerializerProvider serializerProvider)
      throws IOException {
    jsonGen.writeStartObject();
    jsonGen.writeStringField("name", ingredient.getName());
    jsonGen.writeNumberField("amount", ingredient.getAmount());
    jsonGen.writeStringField("unit", ingredient.getUnit());
    jsonGen.writeEndObject();
  }
}
