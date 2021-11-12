package foodie.restapi;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import core.Cookbook;
import core.Recipe;

@ContextConfiguration(classes = { CookbookController.class, CookbookService.class, CookbookApplication.class })
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void testGetCookbook() {
    ResponseEntity<Cookbook> response = restTemplate.getForEntity("/cookbook", Cookbook.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  void testAddRecipe() {
    Recipe recipe = new Recipe("recipe3", 2);
    HttpEntity<Recipe> request = new HttpEntity<Recipe>(recipe);
    ResponseEntity<String> result = this.restTemplate.postForEntity("/cookbook/recipe3", request, String.class);
    assertTrue(Boolean.parseBoolean(result.getBody()));

  }
}