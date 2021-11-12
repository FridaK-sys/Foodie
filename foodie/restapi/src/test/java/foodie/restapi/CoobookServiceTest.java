package foodie.restapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import core.Cookbook;
import core.Recipe;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = { CookbookController.class, CookbookService.class, CookbookApplication.class })
class CookbookServiceTest {

  @SuppressWarnings("SpringJavaAutowiredMembersInspection")
  @Autowired
  private CookbookService service;

  /**
   * Tests the getVisitLog() method in service. Expected result is an initialized
   * VisitLog.
   */
  @Test
  void getCookbook() {
    assertNotNull(service.getCookbook());
  }

  /**
   * Tests the sample visit log. Expected result is a VisitLog with 3 Visits.
   */
  @Test
  void createDefaultCookbook() {
    Cookbook cookbook = CookbookService.createDefaultCookbook();
    assertEquals("Cookbook", cookbook.getName());
    assertEquals("Cake", cookbook.getRecipes().get(0).getName());
    assertEquals(200.0, cookbook.getRecipes().get(0).getIngredients().get(0).getAmount());
  }

  /**
   * Tests the addVisit() method in service. Expected result is true.
   */
  @Test
  void addRecipe() {
    Recipe recipe = new Recipe("recipe3", 5);
    assertTrue(service.addRecipe(recipe));
    // cleanup
    service.removeRecipe(recipe.getName());
  }

  /**
   * Tests the removeVisit(id) method in service. Add a Visit and remove it by its
   * ID. Expected result is true.
   */
  @Test
  void removeRecipe() {
    String name = "recipe2";
    service.addRecipe(new Recipe(name, 2));
    assertTrue(service.removeRecipe(name));
  }

  /**
   * Class for a VisitLogServiceBean used for testing purposes.
   */
  /*
   * @TestConfiguration static class TestContextConfiguration {
   * 
   * @Bean public VisitLogService visitLogService() { return new
   * VisitLogService(); } }
   */
}