package foodie.restapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import core.Cookbook;
import core.Recipe;
import json.CookbookPersistence;

/*
 * @AutoConfigureMockMvc
 * 
 * @ContextConfiguration(classes = { CookbookController.class,
 * CookbookService.class, CookbookApplication.class })
 * 
 * @WebMvcTest public class CookbookApplicationTest {
 * 
 * @Autowired private MockMvc mockMvc;
 * 
 * private ObjectMapper objectMapper;
 * 
 * @BeforeEach public void setup() throws Exception { objectMapper =
 * CookbookPersistence.createObjectMapper(); }
 * 
 * private String cookbookUrl(String... segments) { String url = "/" +
 * CookbookController.COOKBOOK_SERVICE_PATH; for (String segment : segments) {
 * url = url + "/" + segment; } return url; }
 * 
 * @Test public void testGet_cookbook() throws Exception { MvcResult result =
 * mockMvc.perform(MockMvcRequestBuilders.get(cookbookUrl()).accept(MediaType.
 * APPLICATION_JSON))
 * .andExpect(MockMvcResultMatchers.status().isOk()).andReturn(); try { Cookbook
 * cookbook = objectMapper.readValue(result.getResponse().getContentAsString(),
 * Cookbook.class); // Iterator<Recipe> it = iterator(); //
 * assertTrue(it.hasNext()); // Recipe recipe1 = it.next(); //
 * assertTrue(it.hasNext()); // Recipe recipe2 = it.next(); //
 * assertFalse(it.hasNext()); // assertEquals("recipe", recipe1.getName()); //
 * assertEquals("recipe2", recipe2.getName()); } catch (JsonProcessingException
 * e) { fail(e.getMessage()); } }
 * 
 * }
 */
