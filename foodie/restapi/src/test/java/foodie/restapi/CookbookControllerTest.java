package foodie.restapi;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import core.Cookbook;
import core.Recipe;

/**
 * Unit test for CookbookController
 */
class CookbookControllerTest {

  private CookbookController controller;

  @BeforeEach
  void setUp() {
    try {
      controller = new CookbookController();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void testGetCookbook() {
    Cookbook cookbook = controller.getCookbook();
    assertNotNull(cookbook);
    assertEquals("Cookbook", cookbook.getName());
  }

  @Test
  void testAddRecipe() {
    assertTrue(controller.addRecipe("recipe", new Recipe("recipe")));
  }

  @Test
  void testEditRecipe() {
    Recipe r1 = new Recipe("recipe");
    r1.setPortions(1);
    Recipe r2 = new Recipe("recipe");
    r2.setPortions(2);
    controller.addRecipe("recipe", r1);
    assertTrue(controller.editRecipe("recipe", r2));
  }

  @Test
  void testRemoveRecipe() {
    controller.addRecipe("recipe", new Recipe("recipe"));
    assertTrue(controller.removeRecipe("recipe"));
  }

}
