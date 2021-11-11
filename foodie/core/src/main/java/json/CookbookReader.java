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

import core.Cookbook;
import core.Recipe;

import java.io.IOException;

class CookbookReader extends JsonDeserializer<Cookbook> {

  private CookbookReader cookbookReader = new CookbookReader();
  private RecipeReader recipeReader = new RecipeReader();

  /*
   * format: { "lists": [ ... ], "settings": ... }
   */

  @Override
  public Cookbook deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  }

  Cookbook deserialize(JsonNode treeNode) {
    if (treeNode instanceof ObjectNode objectNode) {
      Cookbook cookbook = new Cookbook();
      JsonNode nameNode = objectNode.get("name");
      if (!(nameNode instanceof TextNode)) {
        return null;
      }
      String name = nameNode.asText();
      JsonNode itemsNode = objectNode.get("Recipes");
      if (itemsNode instanceof ArrayNode arrayNode) {
        for (JsonNode elementNode : arrayNode) {
          Recipe recipe = recipeReader.deserialize(elementNode);
          if (recipe != null) {
            cookbook.addRecipe(recipe);
          }
        }
      }
      if (objectNode.has("settings")) {
        TodoSettings settings = todoSettingsDeserializer.deserialize(objectNode.get("settings"));
        model.setSettings(settings);
      }
      return model;
    }
    return null;
  }
}
