package json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.junit.Test;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;

public class CookbookPersistenceTest {

  private CookbookPersistence persistence = new CookbookPersistence();
  private String defaultCookbookPath = System.getProperty("user.dir") + File.separator
      + ("/src/test/java/resources/test-cookbook.json");

  private Cookbook createDefaultCookbook() {
    Recipe r1 = new Recipe("Cake", 1);
    r1.setDescription("Recipe for cake");
    r1.setLabel("Breakfast");
    r1.addIngredient(new Ingredient("Flour", 200.0, "g"));
    r1.addIngredient(new Ingredient("Egg", 2.0, "stk"));
    Recipe r2 = new Recipe("Hot chocolate", 1);
    r2.setDescription("Good dessert");
    r2.addIngredient(new Ingredient("Sugar", 1.5, "dl"));
    r2.addIngredient(new Ingredient("Cocoa", 1.0, "dl"));
    Cookbook cookbook = new Cookbook();
    cookbook.addRecipe(r1);
    cookbook.addRecipe(r2);
    return cookbook;
  }

  public void writeCookbook(Cookbook cookbook) {
    try (Writer writer = new FileWriter(new File(persistence.getSaveFilePath()), StandardCharsets.UTF_8)) {
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new CookbookModule());
      mapper.writerWithDefaultPrettyPrinter().writeValue(writer, cookbook);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  private void checkCookbook(Cookbook defaultCookbook, Cookbook testCookbook) {
    assertEquals(defaultCookbook.getName(), testCookbook.getName());
    assertEquals(defaultCookbook.getRecipes().size(), testCookbook.getRecipes().size());
    assertEquals(defaultCookbook.getRecipes().get(0).getDescription(),
        testCookbook.getRecipes().get(0).getDescription());
  }

  @Test
  public void createObjectMapper() {
    ObjectMapper mapper = CookbookPersistence.createObjectMapper();
    assertNotNull(mapper);
    assertEquals(mapper.getRegisteredModuleIds().toString(), "[CookbookModule]");
  }

  @Test
  public void createModule() {
    SimpleModule module = CookbookPersistence.createModule();
    assertNotNull(module);
  }

  @Test
  public void readCookbook() {
    Cookbook cookbook = createDefaultCookbook();

    try (Reader reader = new FileReader(defaultCookbookPath, StandardCharsets.UTF_8)) {
      Cookbook readCookbook = persistence.readCookbook(reader);
      checkCookbook(cookbook, readCookbook);
    } catch (IOException e) {
      fail(e.getMessage());
    }

  }

  @Test
  public void writeCookbook() {
    Cookbook cookbook = createDefaultCookbook();
    persistence.setSaveFile("/cookbook");
    try (Writer writer = new FileWriter(new File(persistence.getSaveFilePath()), StandardCharsets.UTF_8)) {
      persistence.writeCookbook(cookbook, writer);
      String writtenFile = Files.readString(Paths.get(persistence.getSaveFilePath()));
      String defaultFile = Files.readString(Paths.get(defaultCookbookPath));
      assertEquals(defaultFile, writtenFile);

      // cleanup
      Files.delete(Paths.get(persistence.getSaveFilePath()));
    } catch (IOException e) {
      fail(e.getMessage());
    }

  }

  @Test
  public void loadCookbook() {
    // test not allowed to load cookbook without setting path
    boolean thrown = false;
    try {
      persistence.loadCookbook();
    } catch (IllegalStateException e) {
      thrown = true;
    } catch (IOException e) {
      fail(e.getMessage());
    }
    assertTrue("No exception was thrown even though saveFilePath was not set", thrown);

    // test loading cookbook from existing file
    try {
      persistence.setSaveFile("/cookbook");
      writeCookbook(createDefaultCookbook());
      Cookbook testCookbook = persistence.loadCookbook();
      checkCookbook(createDefaultCookbook(), testCookbook);

      // cleanup
      Files.delete(Paths.get(persistence.getSaveFilePath()));
    } catch (IOException e) {
      fail(e.getMessage());
    }

    // test loading cookbook from nonexisting file
    try {
      persistence.setSaveFile("/cookbook");
      Cookbook cookbook = persistence.loadCookbook();
      assertEquals("Cookbook", cookbook.getName());
      assertEquals(0, cookbook.getRecipes().size());

      // cleanup
      Files.delete(Paths.get(persistence.getSaveFilePath()));
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  void testSaveCookbook() {
    Cookbook cookbook = createDefaultCookbook();

    // test not allowed to save cookbook without setting path
    boolean thrown = false;
    try {
      persistence.saveCookbook(cookbook);
    } catch (IllegalStateException e) {
      thrown = true;
    } catch (IOException e) {
      fail(e.getMessage());
    }
    assertTrue("No exception was thrown even though saveFilePath was not set", thrown);

    // test saving cookbook to existing file
    try {
      persistence.setSaveFile("/cookbook");
      writeCookbook();
      cookbook.addRecipe(new Recipe("Popcorn"));
      persistence.saveCookbook(cookbook);
      ObjectMapper mapper = new ObjectMapper().registerModule(new CookbookModule());
      Cookbook newCookbook = mapper.readValue(persistence.getSaveFilePath(), Cookbook.class);
      checkCookbook(cookbook, newCookbook);

      // cleanup
      Files.delete(Paths.get(persistence.getSaveFilePath()));

    } catch (IOException e) {
      fail(e.getMessage());
    }

    // test saving cookbook to new file
    try {
      persistence.setSaveFile("/cookbook");
      persistence.saveCookbook(createDefaultCookbook());
      ObjectMapper mapper = new ObjectMapper().registerModule(new CookbookModule());
      Cookbook newCookbook = mapper.readValue(persistence.getSaveFilePath(), Cookbook.class);
      checkCookbook(createDefaultCookbook(), newCookbook);

      // cleanup
      Files.delete(Paths.get(persistence.getSaveFilePath()));

    } catch (IOException e) {
      fail(e.getMessage());
    }

  }

}
