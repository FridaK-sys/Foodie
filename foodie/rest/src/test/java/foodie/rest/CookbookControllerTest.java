package foodie.rest;


import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import foodie.core.Cookbook;
import foodie.core.Recipe;
import foodie.json.CookbookModule;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Unit-test (isolated integration-test) of CookbookController.
 */
@ContextConfiguration(classes = {CookbookController.class, CookbookService.class, CookbookApplication.class})
@WebMvcTest(CookbookApplication.class)
public class CookbookControllerTest {

  @MockBean
  private CookbookService cookbookService;

  @Autowired
  private MockMvc mvc;

  private ObjectMapper mapper;

  private Cookbook cookbook;

  @BeforeEach
  public void setUp() {
    mapper = new ObjectMapper().registerModule(new CookbookModule());
    cookbook = CookbookService.createDefaultCookbook();
  }

  @Test
  void getCookbook() {
    try {
      when(cookbookService.getCookbook()).thenReturn(cookbook);
      // GET-request returns OK-status code
      MvcResult result = mvc.perform(MockMvcRequestBuilders.get(CookbookService.COOKBOOK_SERVICE_PATH))
          .andExpect(status().isOk()).andReturn();

      // GET-request returns correct data
      Cookbook cookbook = new ObjectMapper().registerModule(new CookbookModule())
          .readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), Cookbook.class);
      assertNotNull(cookbook, "Cookbook was null");
      assertEquals(2, cookbook.getRecipes().size(), "Cookbook did not have default amount of recipes");
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  void addRecipe() {
    try {
      Recipe recipe = new Recipe("recipe3");
      String json = mapper.writeValueAsString(recipe);
      // POST-request returns OK-status code and response = true
      when(cookbookService.addRecipe(recipe)).thenReturn(true);
      MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/cookbook/" + recipe.getName())
          .contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn();
      assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()), "Response was not true");

    } catch (Exception e) {
      fail(e.getMessage());
    }


  }

  @Test
  void editRecipe() throws Exception {
    try {
      Recipe recipe = new Recipe("Cake");
      recipe.setPortions(2);
      String json = mapper.writeValueAsString(recipe);

      // PUT-request returns OK-status code and resnponse = true
      when(cookbookService.editRecipe(recipe.getName(), recipe)).thenReturn(true);
      MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/cookbook/" + recipe.getName() + "/edit")
          .contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn();
      assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()), "Response was not true");

      // Repeatedly calling PUT-requests will always return the same result
      MvcResult result2 = mvc.perform(MockMvcRequestBuilders.put("/cookbook/" + recipe.getName() + "/edit")
          .contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn();
      assertEquals(result.getResponse().getContentAsString(), result2.getResponse().getContentAsString(),
          "Response was not the same");

    } catch (Exception e) {
      fail(e.getMessage());
    }

  }

  @Test
  void removeRecipe() throws Exception {
    try {
      String name = "Cake";
      
      // DELETE-request returns OK-status code and response = true
      when(cookbookService.removeRecipe(name)).thenReturn(true);
      MvcResult result =
          mvc.perform(MockMvcRequestBuilders.delete("/cookbook/" + name)).andExpect(status().isOk()).andReturn();
      assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()), "Response was not true");

    } catch (Exception e) {
      fail(e.getMessage());
    }

  }
}
