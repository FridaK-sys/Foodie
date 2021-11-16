package foodie.restapi;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import core.Cookbook;
import core.Recipe;

class CookbookControllerTest {

  private CookbookController controller;

  @BeforeEach
  void setUp() {
    CookbookService service;
    try {
      service = new CookbookService();
      controller = new CookbookController(service);
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
    assertTrue(controller.addRecipe("recipe", new Recipe("recipe", 1)));
  }

  @Test
  void testEditRecipe() {
    controller.addRecipe("recipe", new Recipe("recipe", 1));
    assertTrue(controller.editRecipe("recipe", new Recipe("recipe", 2)));
  }

  @Test
  void testRemoveRecipe() {
    controller.addRecipe("recipe", new Recipe("recipe", 1));
    assertTrue(controller.removeRecipe("recipe"));
  }

}
