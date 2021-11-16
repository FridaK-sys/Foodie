package json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import core.Cookbook;
import core.Recipe;

import java.io.IOException;

class CookbookReader extends JsonDeserializer<Cookbook> {

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
      String name = nameNode.asText();
      cookbook.setName(name);
      JsonNode recipesNode = objectNode.get("recipes");
      for (JsonNode recipeNode : (ArrayNode) recipesNode) {
        Recipe recipe = recipeReader.deserialize(recipeNode);
        cookbook.addRecipe(recipe);
      }
      return cookbook;
    }
    return null;
  }
}
