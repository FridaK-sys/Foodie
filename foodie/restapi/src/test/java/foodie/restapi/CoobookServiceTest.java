package foodie.restapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.RequestMapping;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = { CookbookController.class, CookbookService.class, CookbookApplication.class })
class CookbookServiceTest {

  private CookbookService service;

  @BeforeEach
  public void setUp() {
    service = new CookbookService();
  }

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
    assertEquals("Hot chocolate", cookbook.getRecipes().get(1).getName());
  }

  @Test
  void addRecipe() {
    Recipe recipe = new Recipe("recipe3", 5);
    assertTrue(service.addRecipe(recipe));
    assertTrue(service.getCookbook().getRecipes().contains(recipe));
  }

  @Test
  void removeRecipe() {
    String name = "Cake";
    assertTrue(service.removeRecipe(name));
    assertTrue(!service.getCookbook().getRecipes().stream().anyMatch(r -> r.getName().equals(name)));
  }

  @Test
  void editRecipe() {
    String name = "Cake";
    Recipe recipe = new Recipe(name, 3);
    assertTrue(service.editRecipe(name, recipe));
    assertEquals(3, service.getCookbook().getRecipes().stream().filter(r -> r.getName().equals(name)).findAny()
        .orElse(new Recipe("r", 1)).getPortions());
  }

  @Test
  void autoSaveCookbook() {
    Cookbook cookbook = new Cookbook();
    cookbook.setName("New");
    service.setCookbook(cookbook);
    service.autoSaveCookbook();
    assertEquals("New", service.getCookbook().getName());
  }

}