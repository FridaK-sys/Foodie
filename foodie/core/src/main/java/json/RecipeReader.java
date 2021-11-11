package json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import core.Recipe;

import java.io.IOException;
import java.time.LocalDateTime;

class RecipeReader extends JsonDeserializer<Recipe> {

  private RecipeReader recipeReader = new RecipeReader();
  /*
   * format: { "items": [ ... ] }
   */

  @Override
  public Recipe deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  }

  Recipe deserialize(JsonNode treeNode) {
    if (treeNode instanceof ObjectNode objectNode) {
      JsonNode nameNode = objectNode.get("name");
      if (!(nameNode instanceof TextNode)) {
        return null;
      }
      String name = nameNode.asText();
      JsonNode descriptionNode = objectNode.get("description");
      if (!(descriptionNode instanceof TextNode)) {
        return null;
      }
      String description = descriptionNode.asText();
      JsonNode portionsNode = objectNode.get("portions");
      if (!(portionsNode instanceof TextNode)) {
        return null;
      }
      String portions = portionsNode.asText();
      JsonNode favNode = objectNode.get("fav");
      if (!(favNode instanceof TextNode)) {
        return null;
      }
      String fav = nameNode.asText();
      JsonNode labelNode = objectNode.get("label");
      if (!(labelNode instanceof TextNode)) { // husk å sjekk et sted om label er gyldig
        return null;
      }
      String label = labelNode.asText();

      JsonNode itemsNode = objectNode.get("items");
      boolean hasItems = itemsNode instanceof ArrayNode;
      AbstractTodoList todoList = (hasItems ? new TodoList(name) : new AbstractTodoList(name));
      JsonNode deadlineNode = objectNode.get("deadline");
      if (deadlineNode instanceof TextNode) {
        todoList.setDeadline(LocalDateTime.parse(deadlineNode.asText()));
      }
      if (hasItems) {
        for (JsonNode elementNode : ((ArrayNode) itemsNode)) {
          TodoItem item = todoItemDeserializer.deserialize(elementNode);
          if (item != null) {
            todoList.addTodoItem(item);
          }
        }
      }
      return todoList;
    }
    return null;
  }
}
