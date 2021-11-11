package restapi;

import org.springframework.stereotype.Service;
import core.Recipe;
import core.Cookbook;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

  private Recipe recipe;
  private Cookbook cookbook;
  private RecipePersistence recipePersistence;

  /**
   * Initializes the service with a specific cookbook.
   *
   * @param cookbook
   */
  public CookbookService(Cookbook cookbook) {
    this.cookbook = cookbook;
    this.recipePersistence = new RecipePersistence();
    recipePersistence.setSaveFile("springbootserver-cookbook.json");
  }

  public CookbookService() {
    this(createDefaultCookbook());
  }

  public Cookbook getCookbook() {
    return this.cookbook;
  }

  public void setCookbook(Cookbook cookbook) {
    this.cookbook = cookbook;
  }

  private static Cookbook createDefaultCookbook() {
    CookbookPersistence cookbookPersistence = new CookbookPersistence();
    URL url = CookbookService.class.getResource("default-cookbook.json");
    if (url != null) {
      try (Reader reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)) {
        return cookbookPersistence.readCookbook(reader);
      } catch (IOException e) {
        System.out.println("Couldn't read default-cookbook.json, so rigging cookbook manually (" + e + ")");
      }
    }
    Cookbook cookbook = new Cookbook();
    cookbook.addRecipe(new Recipe("Recipe1", 1));
    cookbook.addRecipe(new Recipe("Recipe2", 2));
    return cookbook;
  }

  /**
   * Saves the TodoModel to disk. Should be called after each update.
   */
  public void autoSaveTodoModel() {
    if (cookbookPersistence != null) {
      try {
        cookbookPersistence.saveCookbook(this.cookbook);
      } catch (IllegalStateException | IOException e) {
        System.err.println("Couldn't auto-save cookbook: " + e);
      }
    }
  }

}
