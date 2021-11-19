package foodie.restapi;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import core.Cookbook;
import core.Ingredient;
import core.Recipe;
import json.CookbookModule;

@ContextConfiguration(classes = { CookbookController.class, CookbookService.class, CookbookApplication.class })
@WebMvcTest(CookbookApplication.class)
public class IntegrationTest {

        @Autowired
        private MockMvc mvc;

        @Autowired
        private ObjectMapper mapper;

        private CookbookService service;

        @BeforeEach
        public void setUp() {
                try {
                        service = new CookbookService();
                        service.setCookbook(CookbookService.createDefaultCookbook());
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        @Test
        void getCookbook() throws Exception {
                // GET-request returns OK-status code
                MvcResult result = mvc.perform(MockMvcRequestBuilders.get(CookbookService.COOKBOOK_SERVICE_PATH))
                                .andExpect(status().isOk()).andReturn();

                // GET-request returns correct data
                Cookbook cookbook = new ObjectMapper().registerModule(new CookbookModule()).readValue(
                                result.getResponse().getContentAsString(StandardCharsets.UTF_8), Cookbook.class);
                assertEquals(2, cookbook.getRecipes().size());
                assertNotNull(cookbook);
                assertEquals("Cookbook", cookbook.getName());
        }

        @Test
        void addRecipe() throws Exception {
                Recipe recipe = new Recipe("recipe3", 2);
                String json = mapper.writeValueAsString(recipe);
                // POST-request returns OK-status code and response = true
                MvcResult result = mvc
                                .perform(MockMvcRequestBuilders.post("/cookbook/" + recipe.getName())
                                                .contentType(MediaType.APPLICATION_JSON).content(json))
                                .andExpect(status().isOk()).andReturn();
                assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));

                // GET-request returns updated cookbook
                MvcResult result2 = mvc.perform(MockMvcRequestBuilders.get(CookbookService.COOKBOOK_SERVICE_PATH))
                                .andReturn();
                Cookbook cookbook = new ObjectMapper().registerModule(new CookbookModule()).readValue(
                                result2.getResponse().getContentAsString(StandardCharsets.UTF_8), Cookbook.class);
                assertTrue(cookbook.getRecipes().stream().anyMatch(r -> r.getName().equals("recipe3")));

                // cleanup
                MvcResult result3 = mvc.perform(MockMvcRequestBuilders.delete("/cookbook/recipe3")).andReturn();

        }

        @Test
        void editRecipe() throws Exception {
                Recipe recipe = new Recipe("Cake", 2);
                String json = mapper.writeValueAsString(recipe);
                // PUT-request returns OK-status code
                MvcResult result = mvc
                                .perform(MockMvcRequestBuilders.put("/cookbook/" + recipe.getName() + "/edit")
                                                .contentType(MediaType.APPLICATION_JSON).content(json))
                                .andExpect(status().isOk()).andReturn();
                assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));

                // Repeatedly calling PUT-requests will always return the same result
                MvcResult result2 = mvc
                                .perform(MockMvcRequestBuilders.put("/cookbook/" + recipe.getName() + "/edit")
                                                .contentType(MediaType.APPLICATION_JSON).content(json))
                                .andExpect(status().isOk()).andReturn();
                assertEquals(result.getResponse().getContentAsString(), result2.getResponse().getContentAsString());

                // GET-request returns updated cookbook
                MvcResult result3 = mvc.perform(MockMvcRequestBuilders.get(CookbookService.COOKBOOK_SERVICE_PATH))
                                .andReturn();
                Cookbook cookbook = new ObjectMapper().registerModule(new CookbookModule()).readValue(
                                result3.getResponse().getContentAsString(StandardCharsets.UTF_8), Cookbook.class);
                Recipe res = cookbook.getRecipes().stream().filter(r -> r.getName().equals("Cake")).findAny()
                                .orElse(new Recipe("New", 1));
                assertEquals(2, res.getPortions());

                // cleanup
                Recipe recipe1 = new Recipe("Cake", 1);
                String recipe1String = mapper.writeValueAsString(recipe);
                MvcResult result4 = mvc.perform(MockMvcRequestBuilders.put("/cookbook/" + recipe1.getName() + "/edit")
                                .contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();

        }

        @Test
        void removeRecipe() throws Exception {
                String name = "Cake";
                // DELETE-request returns OK-status code and true
                MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/cookbook/" + name))
                                .andExpect(status().isOk()).andReturn();
                assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));

                // GET-request returns updated cookbook
                MvcResult result2 = mvc.perform(MockMvcRequestBuilders.get(CookbookService.COOKBOOK_SERVICE_PATH))
                                .andReturn();
                Cookbook cookbook = new ObjectMapper().registerModule(new CookbookModule()).readValue(
                                result2.getResponse().getContentAsString(StandardCharsets.UTF_8), Cookbook.class);
                assertTrue(!cookbook.getRecipes().stream().anyMatch(r -> r.getName().equals("Cake")));

                // Several DELETE-requests to same file will throw exception
                boolean thrown = false;
                try {
                        mvc.perform(MockMvcRequestBuilders.delete("/cookbook/" + "Cake"));
                } catch (Exception e) {
                        thrown = true;
                }
                assertTrue(thrown);

                // cleanup
                Recipe r1 = new Recipe("Cake", 1);
                r1.setDescription("Recipe for cake");
                r1.setLabel("Breakfast");
                r1.addIngredient(new Ingredient("Flour", 200.0, "g"));
                r1.addIngredient(new Ingredient("Egg", 2.0, "stk"));
                String json = mapper.writeValueAsString(r1);
                MvcResult result3 = mvc.perform(MockMvcRequestBuilders.post("/cookbook/" + r1.getName())
                                .contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();

        }
}
