package json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

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
    if (jsonNode instanceof ObjectNode) {
      ObjectNode objectNode = (ObjectNode) jsonNode;
      Ingredient ingredient = new Ingredient();
      JsonNode todoItemsSortOrderNode = objectNode.get(TodoSettings.TODO_ITEM_SORT_ORDER_SETTING);
      if (todoItemsSortOrderNode instanceof TextNode) {
        try {
          TodoItemsSortOrder sortOrder = TodoItemsSortOrder.valueOf(todoItemsSortOrderNode.asText());
          settings.setTodoItemsSortOrder(sortOrder);
        } catch (IllegalArgumentException iae) {
          // ignore unknown sort order constant
        }
      }
      return settings;
    }
    return null;
  }
}
