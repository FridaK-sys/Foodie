package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import core.Recipe;
import core.Cookbook;
import core.Ingredient;

public class CookbookModuleTest {

  // {"lists":[{"name": "todo",
  // "items":[{"text":"item1","checked":false},{"text":"item2","checked":true}]}]}

  private static ObjectMapper mapper;
  private static Recipe recipe;
  private static Recipe recipe2;
  private static Ingredient ingredient1, ingredient2, ingredient3, ingredient4;
  private static List<Ingredient> ingredients = new ArrayList<>();
  private static List<Ingredient> ingredients2 = new ArrayList<>();
  private static List<Recipe> recipeList = new ArrayList<>();
  private static Cookbook cookbook;

  @BeforeAll
  public static void setUp() {
    mapper = CookbookPersistence.createObjectMapper();

  }

  final static String newCookbook = """
      {
        "Recipes":
        [{
            "Favorite":false,
            "Portions":1,
            "Description":"Den beste oppskriften på bløtkake!",
            "Ingredients":
            [{
              "Amount":200.0,
              "Unit":"g",
              "Name":"Mel"
            },
            {"Amount":2.0,
            "Unit":"stk",
            "Name":"Egg"
          }],
          "Label":"Breakfast",
          "Name":"Bløtkake"
        },{
          "Favorite":true,
          "Portions":1,
          "Description":"Varm og god dessert!",
          "Ingredients":
          [{
            "Amount":1.5,
            "Unit":"dl",
            "Name":"Sukker"
          },{
            "Amount":1.0,
            "Unit":"dl",
            "Name":"Kakao"
          }],
          "Label":"",
          "Name":"Kakao"
        }],
        "Name":"Kokebok"}
      }
      """;

  static Cookbook createCookbook() {
    ingredient1 = new Ingredient("Mel", 200, "g");
    ingredient2 = new Ingredient("Egg", 2, "stk");
    ingredient3 = new Ingredient("Sukker", 1.5, "dl");
    ingredient4 = new Ingredient("Kakao", 1, "dl");
    ingredients.add(ingredient1);
    ingredients.add(ingredient2);
    ingredients2.add(ingredient3);
    ingredients2.add(ingredient4);
    recipe = new Recipe("Bløtkake", "Den beste oppskriften på bløtkake!", 1, ingredients);
    recipe.setLabel("Breakfast");
    recipe2 = new Recipe("Kakao", "Varm og god dessert!", 1, ingredients2);
    recipe2.setFav(true);
    recipeList.add(recipe);
    recipeList.add(recipe2);
    cookbook = new Cookbook("Kokebok", recipeList);
    return cookbook;
  }

  @Test
  public void testSerializers() {
    Cookbook cookbook = createCookbook();
    try {
      assertEquals(newCookbook.replaceAll("\\s+", ""), mapper.writeValueAsString(cookbook));
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

  static void checkRecipe(Recipe recipe, String name, String description, int portions, List<Ingredient> ingredients) {
    assertEquals(name, recipe.getName());
    assertEquals(description, recipe.getDescription());
    assertEquals(portions, recipe.getPortions());
    assertEquals(ingredients, recipe.getIngredients());

  }

  @Test
  public void testDeserializers() {
    try {
      Cookbook cookbook = mapper.readValue(newCookbook, Cookbook.class);
      List<Recipe> recipes = new ArrayList<>(cookbook.getRecipes());

      for (Recipe res : recipes) {
        checkRecipe(res, "Bløtkake", "Den beste oppskriften på bløtkake!", 1, ingredients);
        checkRecipe(res, "Kakao", "Varm og god dessert", 1, ingredients2);

      }
      /*
       * 
       * assertEquals(TodoItemsSortOrder.CHECKED_UNCHECKED,
       * model.getSettings().getTodoItemsSortOrder()); } catch
       * (JsonProcessingException e) { fail(e.getMessage()); }
       */

    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }

  }

  @Test
  public void testSerializersDeserializers() {
    List<Ingredient> ings = new ArrayList<>();
    List<Recipe> recipes = new ArrayList<>();

    Ingredient ing = new Ingredient("Potet", 1, "kg");
    ings.add(ing);
    Recipe recipe = new Recipe("Stekte poteter", "Kutt potetene og stek de", 2, ings);
    recipes.add(recipe);
    ;

    Cookbook cook = new Cookbook("Tilbehør", recipes);

    try {
      String json = mapper.writeValueAsString(cook);
      Cookbook cookbook2 = mapper.readValue(json, Cookbook.class); // trenger man denne?
      assertEquals("Stekte poteter", recipe.getName());

    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }
  /*
   * private final static String defaultSettnings =
   * "{\"todoItemsSortOrder\":\"UNCHECKED_CHECKED\"}";
   * 
   * 
   * 
   * @Test public void testTodoSettings() { TodoSettings settings = new
   * TodoSettings();
   * settings.setTodoItemsSortOrder(TodoItemsSortOrder.UNCHECKED_CHECKED); try {
   * String json = mapper.writeValueAsString(settings);
   * assertEquals(defaultTodoSettings.replaceAll("\\s+", ""),
   * mapper.writeValueAsString(settings)); TodoSettings settings2 =
   * mapper.readValue(json, TodoSettings.class);
   * assertEquals(settings.getTodoItemsSortOrder(),
   * settings2.getTodoItemsSortOrder()); } catch (JsonProcessingException e) {
   * fail(e.getMessage()); } }
   */
}
