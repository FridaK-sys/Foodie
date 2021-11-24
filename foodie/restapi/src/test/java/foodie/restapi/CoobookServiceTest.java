package foodie.restapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.fail;
import core.Cookbook;
import core.Recipe;

/**
 * Unit test for CookbookService
 */
class CookbookServiceTest {

  private CookbookService service;

  @BeforeEach
  public void setUp() {
    try {
      service = new CookbookService();
      service.setCookbook(CookbookService.createDefaultCookbook());
    } catch (IllegalStateException | IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  void createDefaultCookbook() {
    Cookbook cookbook = CookbookService.createDefaultCookbook();
    assertEquals("Cake", cookbook.getRecipes().get(0).getName(), "Name of first recipe in default cookbook was wrong");
    assertEquals(200.0, cookbook.getRecipes().get(0).getIngredients().get(0).getAmount(),
        "Amount of first ingredient in first recipe was wrong");
    assertEquals("Hot chocolate", cookbook.getRecipes().get(1).getName(), "Name of second recipe was wrong");
  }

  @Test
  void addRecipe() {
    Recipe recipe = new Recipe("recipe3");
    assertTrue(service.addRecipe(recipe), "addRecipe-method did not return true");
    assertTrue(service.getCookbook().getRecipes().contains(recipe), "Recipe was not added");
  }

  @Test
  void removeRecipe() {
    String name = "Cake";
    assertTrue(service.removeRecipe(name), "removeRecipe-method did not return true");
    assertTrue(!service.getCookbook().getRecipes().stream().anyMatch(r -> r.getName().equals(name)),
        "Recipe was not removed");
  }

  @Test
  void editRecipe() {
    String name = "Cake";
    Recipe recipe = new Recipe(name);
    recipe.setPortions(3);
    assertTrue(service.editRecipe(name, recipe), "editRecipe-method did not return true");
    assertEquals(3, service.getCookbook().getRecipes().stream().filter(r -> r.getName().equals(name)).findAny()
        .orElse(new Recipe("Cake")).getPortions(), "Recipe was not edited");
  }

}
