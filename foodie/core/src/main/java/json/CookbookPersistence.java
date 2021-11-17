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
import java.nio.file.Paths;

/**
 * Wrapper class for JSON serialization. Uses ObjectMapper with CookbookModule
 * to write and read from file.
 */
public class CookbookPersistence {

  private ObjectMapper mapper;
  private String saveFilePath = null;

  /**
   * Initializes a CookbokPersistence with a mapper registered with a new
   * CookbookModule.
   */
  public CookbookPersistence() {
    this.mapper = new ObjectMapper().registerModule(new CookbookModule());
  }

  /**
   * Reads a cookbook from file using reader
   * 
   * @param reader the reader that reads file
   * 
   * @throws IOException if IOException occured during reading for instance
   *                     FileNotFoundException
   * 
   * @return cookbook from file
   */
  public Cookbook readCookbook(Reader reader) throws IOException {
    return this.mapper.readValue(reader, Cookbook.class);
  }

  /**
   * Writes cookbook to file using writer
   * 
   * @param cookbook the cookbook to write to file
   * @param writer   the writer that writes to file
   * 
   * @throws IOException if IOException occured during writing for instance
   *                     FileNotFoundException
   */
  public void writeCookbook(Cookbook cookbook, Writer writer) throws IOException {
    this.mapper.writerWithDefaultPrettyPrinter().writeValue(writer, cookbook);
  }

  /**
   * Sets saveFile to user.home/saveFile
   */
  public void setSaveFile(String saveFile) {
    this.saveFilePath = System.getProperty("user.home") + saveFile;
  }

  public String getSaveFilePath() {
    return this.saveFilePath;
  }

  /**
   * Loads a Cookbook from the saved file (saveFilePath) in the user.home folder.
   *
   * @throws IllegalStateException if saveFilePath is not set
   * 
   * @throws IOException           if IOException occured during reading file
   * 
   * @return the loaded Cookbook
   */
  public Cookbook loadCookbook() throws IOException, IllegalStateException {
    if (this.saveFilePath == null) {
      throw new IllegalStateException("Save file path is not set, yet");
    }
    try (Reader reader = new FileReader(this.saveFilePath, StandardCharsets.UTF_8)) {
      return readCookbook(reader);
    } catch (FileNotFoundException e) {
      Cookbook cookbook = new Cookbook();
      saveCookbook(cookbook);
      return cookbook;
    }

  }

  /**
   * Saves a cookbook to the saveFilePath in the user.home folder.
   *
   * @param cookbook cookbook to save
   * 
   * @throws IllegalStateException if saveFilePath is not set
   * 
   * @throws IOException           if IOException occured during writing to file
   */
  public void saveCookbook(Cookbook cookbook) throws IOException, IllegalStateException {
    if (saveFilePath == null) {
      throw new IllegalStateException("Save file path is not set, yet");
    } else if (Paths.get(saveFilePath).toFile().exists()) {
      try (Writer writer = new FileWriter(saveFilePath, StandardCharsets.UTF_8)) {
        writeCookbook(cookbook, writer);
      }
    } else {
      try (Writer writer = new FileWriter(new File(saveFilePath), StandardCharsets.UTF_8)) {
        writeCookbook(cookbook, writer);
      }

    }

  }
}
