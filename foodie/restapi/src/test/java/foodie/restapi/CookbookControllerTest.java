package foodie.restapi;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;

import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import core.Cookbook;
import core.Recipe;
import json.CookbookModule;

@ContextConfiguration(classes = { CookbookController.class, CookbookService.class, CookbookApplication.class })
@WebMvcTest(CookbookApplication.class)
class CookbookControllerTest {

  private CookbookService service;

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper mapper;

  @BeforeEach
  public void setUp() {
    service = new CookbookService();
  }

  @Test
  void getCookbook() throws Exception {
    // GET-request returns OK-status code
    MvcResult result = mvc.perform(MockMvcRequestBuilders.get(service.COOKBOOK_SERVICE_PATH)).andExpect(status().isOk())
        .andReturn();

    // GET-request returns correct data
    Cookbook cookbook = new ObjectMapper().registerModule(new CookbookModule())
        .readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), Cookbook.class);
    assertEquals(2, cookbook.getRecipes().size());
    assertNotNull(cookbook);
    assertEquals("Cookbook", cookbook.getName());
  }

  @Test
  void addRecipe() throws Exception {
    Recipe recipe = new Recipe("recipe3", 2);
    String json = mapper.writeValueAsString(recipe);
    // POST-request returns OK-status code and true
    MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/cookbook/" + recipe.getName())
        .contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn();
    assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));

    // GET-request returns updated cookbook
    MvcResult result2 = mvc.perform(MockMvcRequestBuilders.get(service.COOKBOOK_SERVICE_PATH))
        .andExpect(status().isOk()).andReturn();
    Cookbook cookbook = new ObjectMapper().registerModule(new CookbookModule())
        .readValue(result2.getResponse().getContentAsString(StandardCharsets.UTF_8), Cookbook.class);
    assertTrue(cookbook.getRecipes().stream().anyMatch(r -> r.getName().equals("recipe3")));
  }

  @Test
  void editRecipe() throws Exception {
    Recipe recipe = new Recipe("Cake", 2);
    String json = mapper.writeValueAsString(recipe);
    // PUT-request returns OK-status code
    MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/cookbook/" + recipe.getName() + "/edit")
        .contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn();
    assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));

    // Repeatedly calling PUT-requests will always return the same result
    MvcResult result2 = mvc.perform(MockMvcRequestBuilders.put("/cookbook/" + recipe.getName() + "/edit")
        .contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn();
    assertEquals(result, result2);

    // GET-request returns updated cookbook
    MvcResult result3 = mvc.perform(MockMvcRequestBuilders.get(service.COOKBOOK_SERVICE_PATH))
        .andExpect(status().isOk()).andReturn();
    Cookbook cookbook = new ObjectMapper().registerModule(new CookbookModule())
        .readValue(result3.getResponse().getContentAsString(StandardCharsets.UTF_8), Cookbook.class);
    Recipe res = cookbook.getRecipes().stream().filter(r -> r.getName().equals("Cake")).findAny()
        .orElse(new Recipe("New", 1));
    assertEquals(2, res.getPortions());

  }

  @Test
  void removeRecipe() throws Exception {
    String name = "Cake";
    // DELETE-request returns OK-status code and true
    MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/cookbook/" + name)).andExpect(status().isOk())
        .andReturn();
    assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));

    // GET-request returns updated cookbook
    MvcResult result3 = mvc.perform(MockMvcRequestBuilders.get(service.COOKBOOK_SERVICE_PATH))
        .andExpect(status().isOk()).andReturn();
    Cookbook cookbook = new ObjectMapper().registerModule(new CookbookModule())
        .readValue(result3.getResponse().getContentAsString(StandardCharsets.UTF_8), Cookbook.class);
    assertTrue(!cookbook.getRecipes().stream().anyMatch(r -> r.getName().equals("Cake")));

    // Several DELETE-requests to same file will throw exception
    boolean thrown = false;
    try {
      mvc.perform(MockMvcRequestBuilders.delete("/cookbook/" + "Cake"));
    } catch (Exception e) {
      thrown = true;
    }
    assertTrue(thrown);
  }
}