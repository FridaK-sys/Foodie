package foodie.restapi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import core.Cookbook;
import core.Recipe;
import json.CookbookModule;

/**
 * Integration test for Rest-classes
 */
@ContextConfiguration(classes = {CookbookController.class, CookbookService.class, CookbookApplication.class})
@WebMvcTest(CookbookApplication.class)
public class IntegrationTest {

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
                        MvcResult result =
                                        mvc.perform(MockMvcRequestBuilders.get(CookbookService.COOKBOOK_SERVICE_PATH))
                                                        .andExpect(status().isOk()).andReturn();

                        // GET-request returns correct data

                        Cookbook cookbook = new ObjectMapper().registerModule(new CookbookModule()).readValue(
                                        result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                                        Cookbook.class);
                        assertNotNull("Cookbook was null", cookbook);
                        assertEquals("Cookbook", cookbook.getName(), "Name of Cookbook was not default name");
                        assertEquals(2, cookbook.getRecipes().size(), "Cookbook did not have default amout of recipes");
                } catch (Exception e) {
                        fail(e.getMessage());
                }
        }

        @Test
        void addRecipe() throws Exception {
                try {
                        Recipe recipe = new Recipe("recipe3", 2);
                        String json = mapper.writeValueAsString(recipe);
                        // POST-request returns OK-status code and response = true
                        when(cookbookService.addRecipe(recipe)).thenReturn(true);
                        MvcResult result = mvc
                                        .perform(MockMvcRequestBuilders.post("/cookbook/" + recipe.getName())
                                                        .contentType(MediaType.APPLICATION_JSON).content(json))
                                        .andExpect(status().isOk()).andReturn();
                        assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()),
                                        "Response was not true");

                        // GET-request returns updated cookbook
                        cookbook.addRecipe(recipe);
                        when(cookbookService.getCookbook()).thenReturn(cookbook);
                        MvcResult result2 =
                                        mvc.perform(MockMvcRequestBuilders.get(CookbookService.COOKBOOK_SERVICE_PATH))
                                                        .andReturn();
                        Cookbook cookbook = new ObjectMapper().registerModule(new CookbookModule()).readValue(
                                        result2.getResponse().getContentAsString(StandardCharsets.UTF_8),
                                        Cookbook.class);
                        assertTrue(cookbook.getRecipes().stream().anyMatch(r -> r.getName().equals("recipe3")),
                                        "Recipe was not added");

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
                        // PUT-request returns OK-status code
                        when(cookbookService.editRecipe(recipe.getName(), recipe)).thenReturn(true);
                        MvcResult result = mvc
                                        .perform(MockMvcRequestBuilders.put("/cookbook/" + recipe.getName() + "/edit")
                                                        .contentType(MediaType.APPLICATION_JSON).content(json))
                                        .andExpect(status().isOk()).andReturn();
                        assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()),
                                        "Response was not true");

                        // Repeatedly calling PUT-requests will always return the same result
                        MvcResult result2 = mvc
                                        .perform(MockMvcRequestBuilders.put("/cookbook/" + recipe.getName() + "/edit")
                                                        .contentType(MediaType.APPLICATION_JSON).content(json))
                                        .andExpect(status().isOk()).andReturn();
                        assertEquals(result.getResponse().getContentAsString(),
                                        result2.getResponse().getContentAsString(), "Response was not the same");

                        // GET-request returns updated cookbook
                        cookbook.replaceRecipe(recipe.getName(), recipe);
                        when(cookbookService.getCookbook()).thenReturn(cookbook);
                        MvcResult result3 =
                                        mvc.perform(MockMvcRequestBuilders.get(CookbookService.COOKBOOK_SERVICE_PATH))
                                                        .andReturn();
                        Cookbook cookbook = new ObjectMapper().registerModule(new CookbookModule()).readValue(
                                        result3.getResponse().getContentAsString(StandardCharsets.UTF_8),
                                        Cookbook.class);
                        Recipe res = cookbook.getRecipes().stream().filter(r -> r.getName().equals("Cake")).findAny()
                                        .orElse(new Recipe("New", 1));
                        assertEquals(2, res.getPortions(), "Recipe was not edited");

                } catch (Exception e) {
                        fail(e.getMessage());
                }

        }

        @Test
        void removeRecipe() throws Exception {
                try {
                        String name = "Cake";
                        // DELETE-request returns OK-status code and true
                        when(cookbookService.removeRecipe(name)).thenReturn(true);
                        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/cookbook/" + name))
                                        .andExpect(status().isOk()).andReturn();
                        assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()),
                                        "Response was not true");

                        // GET-request returns updated cookbook
                        cookbook.removeRecipe(name);
                        when(cookbookService.getCookbook()).thenReturn(cookbook);
                        MvcResult result2 =
                                        mvc.perform(MockMvcRequestBuilders.get(CookbookService.COOKBOOK_SERVICE_PATH))
                                                        .andReturn();
                        Cookbook cookbook = new ObjectMapper().registerModule(new CookbookModule()).readValue(
                                        result2.getResponse().getContentAsString(StandardCharsets.UTF_8),
                                        Cookbook.class);
                        assertTrue(!cookbook.getRecipes().stream().anyMatch(r -> r.getName().equals("Cake")),
                                        "Recipe was not removed");

                } catch (Exception e) {
                        fail(e.getMessage());
                }

        }
}
