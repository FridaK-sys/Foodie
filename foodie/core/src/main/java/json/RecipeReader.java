package json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import core.Ingredient;
import core.Recipe;
import java.io.IOException;

/**
 * Deserializer for Recipe-class.
 */
class RecipeReader extends JsonDeserializer<Recipe> {

  private IngredientReader ingredientReader = new IngredientReader();

  @Override
  public Recipe deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  }

  Recipe deserialize(JsonNode treeNode) {
    if (treeNode instanceof ObjectNode objectNode) {
      JsonNode nameNode = objectNode.get("name");
      String name = nameNode.asText();
      JsonNode descriptionNode = objectNode.get("description");
      String description = descriptionNode.asText();
      JsonNode portionsNode = objectNode.get("portions");
      int portions = portionsNode.asInt();
      JsonNode favNode = objectNode.get("fav");
      boolean fav = favNode.asBoolean();
      JsonNode labelNode = objectNode.get("label");
      String label = labelNode.asText();

      Recipe recipe = new Recipe(name);
      recipe.setDescription(description);
      recipe.setPortions(portions);
      recipe.setFav(fav);
      recipe.setLabel(label);

      JsonNode ingredientsNode = objectNode.get("ingredients");
      for (JsonNode i : (ArrayNode) ingredientsNode) {
        Ingredient ingredient = ingredientReader.deserialize(i);
        recipe.addIngredient(ingredient);
      }

      return recipe;
    }
    return null;
  }
}
