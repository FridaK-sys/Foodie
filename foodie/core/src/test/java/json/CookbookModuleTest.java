package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import core.Recipe;
import core.Cookbook;
import core.Ingredient;

public class CookbookModuleTest {

  private static ObjectMapper mapper;
  private static Ingredient ingredient1, ingredient2, ingredient3, ingredient4;
  private static List<Ingredient> ingredients1 = new ArrayList<>();
  private static List<Ingredient> ingredients2 = new ArrayList<>();
  private static Recipe recipe1, recipe2;
  private static List<Recipe> recipes = new ArrayList<>();
  private static Cookbook cookbook;

  @BeforeAll
  public static void setUp() {
    mapper = CookbookPersistence.createObjectMapper();

  }

  static Cookbook createCookbook() {
    ingredient1 = new Ingredient("Egg", 2, "stk");
    ingredient2 = new Ingredient("Kakao", 1, "dl");
    ingredient3 = new Ingredient("Mel", 200, "g");
    ingredient4 = new Ingredient("Sukker", 1.5, "dl");
    ingredients1.add(ingredient1);
    ingredients1.add(ingredient2);
    ingredients2.add(ingredient3);
    ingredients2.add(ingredient4);
    recipe1 = new Recipe("Brownies", "Den beste oppskriften på brownies!", 1, ingredients1);
    recipe1.setLabel("Dessert");
    recipe1.setFav(true);
    recipe2 = new Recipe("Pepperkaker", "Bestemor sin oppskrift", 4, ingredients2);
    recipe2.setLabel("Dessert");
    recipes.add(recipe1);
    recipes.add(recipe2);
    cookbook = new Cookbook("Cookbook", recipes);
    return cookbook;
  }

  final static String newCookbook = """
      {
        "name": "Cookbook",
        "recipes" :[
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
          },
          {
            "name": "Pepperkaker",
            "description": "Bestemor sin oppskrift",
            "portions": 4,
            "fav": false,
            "label": "Dessert",
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

  @Test
  public void testSerializers() {
    Cookbook cookbook = createCookbook();
    try {
      for (Recipe recipe : recipes) {
        recipe.setDescription(recipe.getDescription().replaceAll("\\s+", ""));
      }
      assertEquals(newCookbook.replaceAll("\\s+", ""), mapper.writeValueAsString(cookbook));
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

  static void checkCookbook(Cookbook cookbook, String name) {
    assertEquals(name, cookbook.getName());
  }

  static void checkRecipe(Recipe recipe, String name, String description, int portions, boolean fav, String label,
      List<Ingredient> ingredients) {
    assertEquals(name, recipe.getName());
    assertEquals(description, recipe.getDescription());
    assertEquals(portions, recipe.getPortions());
    assertEquals(fav, recipe.getFav());
    assertEquals(label, recipe.getLabel());
    assertEquals(ingredients, recipe.getIngredients());
  }

  @Test
  public void testDeserializers() {
    try {
      createCookbook();
      Cookbook newCookbook2 = mapper.readValue(newCookbook, Cookbook.class);
      checkCookbook(newCookbook2, "Cookbook");
      checkRecipe(recipe1, "Brownies", "Den beste oppskriften på brownies!", 1, true, "Dessert", ingredients1);
      checkRecipe(recipe2, "Pepperkaker", "Bestemor sin oppskrift", 4, false, "Dessert", ingredients2);

    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }

  }

  @Test
  public void testSerializersDeserializers() {
    Ingredient ingredient1 = new Ingredient("Potet", 3, "stk");
    List<Ingredient> ingredients = new ArrayList<>();
    ingredients.add(ingredient1);
    Recipe recipe = new Recipe("Stekte poteter", "Kutt potetene og stek de", 2, ingredients);
    List<Recipe> recipes = new ArrayList<>();
    recipes.add(recipe);
    Cookbook cookbook1 = new Cookbook("Tilbehør", recipes);
    try {
      String json = mapper.writeValueAsString(cookbook1);
      Cookbook cookbook2 = mapper.readValue(json, Cookbook.class);
      assertEquals(cookbook1.getName(), cookbook2.getName());
      assertEquals(cookbook1.getRecipes().toString(), cookbook2.getRecipes().toString());
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

}
