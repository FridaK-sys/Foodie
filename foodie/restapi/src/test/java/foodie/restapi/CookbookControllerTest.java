package foodie.restapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import core.Cookbook;
import core.Recipe;
import json.CookbookModule;

@AutoConfigureMockMvc
@ContextConfiguration(classes = { CookbookController.class, CookbookService.class, CookbookApplication.class })
@WebMvcTest(CookbookApplication.class)
class CookbookControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private CookbookService service;

  @Autowired
  private ObjectMapper mapper;

  @Test
  void getCookbook() throws Exception {
    given(service.getCookbook()).willReturn(CookbookService.createDefaultCookbook());
    MvcResult result = mvc.perform(MockMvcRequestBuilders.get(service.COOKBOOK_SERVICE_PATH)).andExpect(status().isOk())
        .andReturn();
    Cookbook cookbook = new ObjectMapper().registerModule(new CookbookModule())
        .readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), Cookbook.class);
    assertEquals(2, cookbook.getRecipes().size());
  }

  @Test
  void addRecipe() throws Exception {
    Recipe recipe = new Recipe("recipe3", 2);
    String json = mapper.writeValueAsString(recipe);
    MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/cookbook/" + recipe.getName())
        .contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn();
    assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
  }

  @Test
  void editRecipe() throws Exception {
    Recipe recipe = new Recipe("recipe3", 2);
    String json = mapper.writeValueAsString(recipe);
    MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/cookbook/" + recipe.getName() + "/edit")
        .contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn();
    assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
  }

  @Test
  void removeRecipe() throws Exception {
    String name = "recipe1";
    MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/cookbook/" + name)).andExpect(status().isOk())
        .andReturn();
    assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
  }
}