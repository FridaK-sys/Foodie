package foodie.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import com.fasterxml.jackson.core.JsonProcessingException;
import foodie.core.Ingredient;
import org.junit.jupiter.api.Test;

/**
 * Test class for IngredientDeserializer and IngredientSerializer.
 */
public class IngredientJsonTest extends AbstractJsonTest {

  final static String ingredientJson = """
      {
        "name": "Egg",
        "amount": 2.0,
        "unit": "stk"
      }
      """;

  protected static Ingredient createIngredient() {
    return new Ingredient("Egg", 2, "stk");
  }

  static void checkIngredient(Ingredient ingredient) {
    Ingredient defaultIngredient = createIngredient();
    assertEquals(defaultIngredient.getName(), ingredient.getName(), "Name was wrong");
    assertEquals(defaultIngredient.getAmount(), ingredient.getAmount(), "Amount was wrong");
    assertEquals(defaultIngredient.getUnit(), ingredient.getUnit(), "Unit was wrong");
  }

  @Test
  public void testSerialization() {
    try {
      String actualJson = mapper.writeValueAsString(createIngredient());
      assertEqualsIgnoreWhitespace(ingredientJson, actualJson, "Serialization was not done correctly");
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testDeserialization() {
    try {
      Ingredient ingredient = mapper.readValue(ingredientJson, Ingredient.class);
      checkIngredient(ingredient);
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testSerializersDeserializers() {
    try {
      String ingredientJson = mapper.writeValueAsString(createIngredient());
      Ingredient ingredient = mapper.readValue(ingredientJson, Ingredient.class);
      checkIngredient(ingredient);
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }
}
