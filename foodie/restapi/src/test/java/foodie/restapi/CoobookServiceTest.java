package foodie.restapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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

  @Test
  void getCookbook() {
    assertNotNull(service.getCookbook());
  }

  @Test
  void createDefaultCookbook() {
    Cookbook cookbook = CookbookService.createDefaultCookbook();
    assertEquals("Cookbook", cookbook.getName());
    assertEquals("Cake", cookbook.getRecipes().get(0).getName());
    assertEquals(200.0, cookbook.getRecipes().get(0).getIngredients().get(0).getAmount());
  }

  @Test
  void addRecipe() {
    Recipe recipe = new Recipe("recipe3", 5);
    assertTrue(service.addRecipe(recipe));
    // cleanup
    service.removeRecipe(recipe.getName());
  }

  @Test
  void removeRecipe() {
    String name = "recipe2";
    service.addRecipe(new Recipe(name, 2));
    assertTrue(service.removeRecipe(name));
  }
}