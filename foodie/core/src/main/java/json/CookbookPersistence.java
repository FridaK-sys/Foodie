package json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import core.Cookbook;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Wrapper class for JSON serialization, to avoid direct compile dependencies on
 * Jackson for other modules.
 */
public class CookbookPersistence {

  private ObjectMapper mapper;
  private Path saveFilePath = null;

  public CookbookPersistence() {
    mapper = createObjectMapper();
  }

  public static SimpleModule createModule() {
    return new CookbookModule();
  }

  public static ObjectMapper createObjectMapper() {
    return new ObjectMapper().registerModule(createModule());
  }

  public Cookbook readCookbook(Reader reader) throws IOException {
    return mapper.readValue(reader, Cookbook.class);
  }

  public void writeCookbook(Cookbook cookbook, Writer writer) throws IOException {
    mapper.writerWithDefaultPrettyPrinter().writeValue(writer, cookbook);
  }

  public void setSaveFile(String saveFile) {
    this.saveFilePath = Paths.get(System.getProperty("user.home"), saveFile);
  }

  public Path getSaveFilePath() {
    return this.saveFilePath;
  }

  /**
   * Loads a TodoModel from the saved file (saveFilePath) in the user.home folder.
   *
   * @return the loaded TodoModel
   */
  public Cookbook loadCookbook() throws IOException, IllegalStateException {
    if (saveFilePath == null) {
      throw new IllegalStateException("Save file path is not set, yet");
    }
    try (Reader reader = new FileReader(saveFilePath.toFile(), StandardCharsets.UTF_8)) {
      return readCookbook(reader);
    } catch (FileNotFoundException e) {
      Cookbook cookbook = new Cookbook();
      saveCookbook(cookbook);
      return cookbook;
    }

  }

  /**
   * Saves a TodoModel to the saveFilePath in the user.home folder.
   *
   * @param todoModel the TodoModel to save
   */
  public void saveCookbook(Cookbook cookbook) throws IOException, IllegalStateException {
    if (saveFilePath == null) {
      throw new IllegalStateException("Save file path is not set, yet");
    } else if (saveFilePath.toFile().exists()) {
      try (Writer writer = new FileWriter(saveFilePath.toFile(), StandardCharsets.UTF_8)) {
        writeCookbook(cookbook, writer);

      }

    } else {
      try (Writer writer = new FileWriter(new File(saveFilePath.toString()), StandardCharsets.UTF_8)) {
        writeCookbook(cookbook, writer);

      }

    }

  }
  /*
   * public static void main(String[] args) throws IllegalStateException,
   * IOException { Cookbook cookbook = new Cookbook(); CookbookPersistence
   * cookbookPersistence = new CookbookPersistence(); Ingredient ingredient1 = new
   * Ingredient("tomat", 3, "dl"); Ingredient ingredient2 = new Ingredient("eple",
   * 2, "stk"); cookbook.addRecipe(new Recipe("recipe1", "lag", 2,
   * Arrays.asList(ingredient1, ingredient2))); cookbook.addRecipe(new
   * Recipe("recipe2", 2));
   * 
   * cookbookPersistence.setSaveFile("/checkCookbook");
   * cookbookPersistence.saveCookbook(cookbook);
   * 
   * Cookbook cookbook2 = cookbookPersistence.loadCookbook(); Recipe recipe1 =
   * cookbook2.getRecipes().get(0); Ingredient tomat =
   * recipe1.getIngredients().get(0); System.out.println(cookbook2.getName());
   * System.out.println(recipe1.getName()); System.out.println(tomat.getName());
   * System.out.println(tomat.getUnit()); }
   */
}
