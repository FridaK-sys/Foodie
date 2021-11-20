package json;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import core.Cookbook;
import core.Ingredient;
import core.Recipe;

/**
 * Test class for CookbookDeserializer and CookbookSerializer
 */
public class CookbookJsonTest extends AbstractJsonTest {

  final static String cookbookJson = """
      {
        "name": "Cookbook",
        "recipes" :[
          {
            "name": "Brownies",
            "description": "Den beste oppskriften på brownies!",
            "portions": 1,
            "fav": true,
            "label": "dessert",
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
          },
          {
            "name": "Pepperkaker",
            "description": "Bestemor sin oppskrift",
            "portions": 4,
            "fav": false,
            "label": "dessert",
            "ingredients" :[
              {
                "name": "Mel",
                "amount": 200.0,
                "unit": "g"
              },
              {
                "name": "Sukker",
                "amount": 1.5,
                "unit": "dl"
              }
            ]
          }
        ]
      }
      """;

  protected static Cookbook createCookbook() {
    Ingredient ingredient1 = new Ingredient("Egg", 2, "stk");
    Ingredient ingredient2 = new Ingredient("Kakao", 1, "dl");
    Ingredient ingredient3 = new Ingredient("Mel", 200, "g");
    Ingredient ingredient4 = new Ingredient("Sukker", 1.5, "dl");
    List<Ingredient> ingredients1 = new ArrayList<>();
    List<Ingredient> ingredients2 = new ArrayList<>();
    ingredients1.add(ingredient1);
    ingredients1.add(ingredient2);
    ingredients2.add(ingredient3);
    ingredients2.add(ingredient4);
    Recipe recipe1 = new Recipe("Brownies", "Den beste oppskriften på brownies!", 1, ingredients1);
    recipe1.setLabel("dessert");
    recipe1.setFav(true);
    Recipe recipe2 = new Recipe("Pepperkaker", "Bestemor sin oppskrift", 4, ingredients2);
    recipe2.setLabel("dessert");
    List<Recipe> recipes = new ArrayList<>();
    recipes.add(recipe1);
    recipes.add(recipe2);
    Cookbook cookbook = new Cookbook("Cookbook", recipes);
    return cookbook;
  }

  static void checkCookbook(Cookbook cookbook) {
    Cookbook defaultCookbook = createCookbook();
    assertEquals(defaultCookbook.getName(), cookbook.getName());
    assertEquals(defaultCookbook.getRecipes().toString(), cookbook.getRecipes().toString());
  }

  @Test
  public void testSerialization() {
    try {
      String actualJson = mapper.writeValueAsString(createCookbook());
      assertEqualsIgnoreWhitespace(cookbookJson, actualJson);
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testDeserialization() {
    try {
      Cookbook cookbook = mapper.readValue(cookbookJson, Cookbook.class);
      checkCookbook(cookbook);
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }

  }

  @Test
  public void testSerializersDeserializers() {
    try {
      String json = mapper.writeValueAsString(createCookbook());
      Cookbook cookbook = mapper.readValue(json, Cookbook.class);
      checkCookbook(cookbook);
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }
}
