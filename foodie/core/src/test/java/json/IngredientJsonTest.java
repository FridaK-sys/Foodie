package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import core.Ingredient;

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
    assertEquals(defaultIngredient.getName(), ingredient.getName());
    assertEquals(defaultIngredient.getAmount(), ingredient.getAmount());
    assertEquals(defaultIngredient.getUnit(), ingredient.getUnit());
  }

  @Test
  public void testSerialization() {
    try {
      String actualJson = mapper.writeValueAsString(createIngredient());
      assertEqualsIgnoreWhitespace(ingredientJson, actualJson);
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
