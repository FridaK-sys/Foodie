package restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import core.Recipe;
import core.Cookbook;

@RestController
@RequestMapping(CookbookController.COOKBOOK_SERVICE_PATH)
public class CookbookController {

  public static final String COOKBOOK_SERVICE_PATH = "restapi/cookbook";

  @Autowired
  private CookbookService resService;

  @GetMapping
  public Cookbook getCookbook() {
    return resService.getCookbook();
  }

  private void autoSaveCookbook() {
    resService.autoSaveCookbook();
  }

  /**
   * Gets the corresponding recipe.
   *
   * @param name the name of the TodoList
   * @return the corresponding TodoList
   */
  @GetMapping(path = "/{name}")
  public Recipe getRecipe(@PathVariable("name") String name) {
    return getCookbook().getRecipes().stream().filter(r -> r.getName().equals(name)).findFirst()
        .orElseThrow(() -> new IllegalArgumentException("No recipe named \"" + name + "\""));

  }

  /**
   * Replaces or adds a TodoList.
   *
   * @param name     the name of the TodoList
   * @param todoList the todoList to add
   * @return true if it was added, false if it replaced
   */
  @PutMapping(path = "/{name}")
  public boolean putRecipe(@PathVariable("name") String name, @RequestBody Recipe recipe) {
    boolean added = true;
    for (Recipe res : getCookbook().getRecipes()) {
      if (res.getName().equals(recipe.getName())) {
        added = false;

      }
      return added;

    }
    autoSaveCookbook();
    return added;
  }

  /**
   * Renames the Recipe.
   *
   * @param name    the name of the Recipe
   * @param newName the new name
   */
  @PostMapping(path = "/{name}/rename")
  public boolean renameRecipe(@PathVariable("name") String name, @RequestParam("newName") String newName) {
    Recipe res = getCookbook().getRecipes().stream().filter(r -> r.getName().equals(name)).findFirst()
        .orElseThrow(() -> new IllegalArgumentException("No recipe named \"" + name + "\""));

    res.setName(newName);
    autoSaveCookbook();
    return true;

  }

  /**
   * Removes the Recipe.
   *
   * @param name the name of the Recipe
   */
  @DeleteMapping(path = "/{name}")
  public boolean removeRecipe(@PathVariable("name") String name) {
    Cookbook cook = getCookbook();
    cook.removeRecipe(name);
    autoSaveCookbook();
    return true;
  }

}