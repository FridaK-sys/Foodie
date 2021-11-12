package json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import core.Cookbook;
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
  private Path saveFilePath;

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
    }
    try (Writer writer = new FileWriter(saveFilePath.toFile(), StandardCharsets.UTF_8)) {
      writeCookbook(cookbook, writer);
    }
  }
}
