package json;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import core.Ingredient;
import core.Recipe;

/**
 * Test class for RecipeDeserializer and RecipeSerializer
 */
public class RecipeJsonTest extends AbstractJsonTest {

  final static String recipeJson = """
      {
        "name": "Brownies",
        "description": "Den beste oppskriften på brownies!",
        "portions": 1,
        "fav": true,
        "label": "Dessert",
        "ingredients" :[
          {
            "name": "Egg",
            "amount": 2.0,
            "unit": "stk"
          },
          {
            "name": "Kakao",
            "amount": 1.0,
            "unit": "dl"
          }
        ]
      }
      """;

  protected static Recipe createRecipe() {
    Ingredient ingredient1 = new Ingredient("Egg", 2, "stk");
    Ingredient ingredient2 = new Ingredient("Kakao", 1, "dl");
    List<Ingredient> ingredients = new ArrayList<>();
    ingredients.add(ingredient1);
    ingredients.add(ingredient2);
    Recipe recipe = new Recipe("Brownies", "Den beste oppskriften på brownies!", 1, ingredients);
    recipe.setLabel("Dessert");
    recipe.setFav(true);
    return recipe;
  }

  static void checkRecipe(Recipe recipe) {
    Recipe defaultRecipe = createRecipe();
    assertEquals(defaultRecipe.getName(), recipe.getName(), "Name was wrong");
    assertEquals(defaultRecipe.getLabel(), recipe.getLabel(), "Label was wrong");
    assertEquals(defaultRecipe.getIngredients().toString(), recipe.getIngredients().toString(),
        "List of ingredients was wrong");
  }

  @Test
  public void testSerialization() {
    try {
      String actualJson = mapper.writeValueAsString(createRecipe());
      assertEqualsIgnoreWhitespace(recipeJson, actualJson, "Serialization was not done correctly");
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testDeserialization() {
    try {
      Recipe recipe = mapper.readValue(recipeJson, Recipe.class);
      checkRecipe(recipe);
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testSerializersDeserializers() {
    try {
      String recipeJson = mapper.writeValueAsString(createRecipe());
      Recipe recipe = mapper.readValue(recipeJson, Recipe.class);
      checkRecipe(recipe);
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }
}
