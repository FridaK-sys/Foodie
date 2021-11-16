package json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import core.Ingredient;
import java.io.IOException;

class IngredientReader extends JsonDeserializer<Ingredient> {

  @Override
  public Ingredient deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  }

  Ingredient deserialize(JsonNode jsonNode) {
    if (jsonNode instanceof ObjectNode objectNode) {
      JsonNode nameNode = objectNode.get("name");
      String name = nameNode.asText();
      JsonNode amountNode = objectNode.get("amount");
      Double amount = amountNode.asDouble();
      JsonNode unitNode = objectNode.get("unit");
      String unit = unitNode.asText();
      return new Ingredient(name, amount, unit);
    }
    return null;
  }
}
