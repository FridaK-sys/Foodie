package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import core.Ingredient;
import core.Recipe;

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
    assertEquals(defaultRecipe.getName(), recipe.getName());
    assertEquals(defaultRecipe.getLabel(), recipe.getLabel());
    assertEquals(defaultRecipe.getIngredients().toString(), recipe.getIngredients().toString());
  }

  @Test
  public void testSerialization() {
    try {
      String actualJson = mapper.writeValueAsString(createRecipe());
      assertEqualsIgnoreWhitespace(recipeJson, actualJson);
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
