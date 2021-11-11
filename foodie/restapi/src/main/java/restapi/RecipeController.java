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
@RequestMapping(RecipeController.RECIPE_SERVICE_PATH)
public class RecipeController {

  private static final String RECIPE_SERVICE_PATH = "restapi/recipe";

  @Autowired
  private RecipeService resService;

  @GetMapping
  public Recipe getRecipe() {
    resService.getRecipe();

  }

  @GetMapping
  public Cookbook getCookbook() {
    resService.getCookBook();
  }

  /*
   * private void autoSaveTodoModel() { todoModelService.autoSaveTodoModel(); }
   */
  private void checkTodoList(Recipe recipe, String name) {
    if (recipe == null) {
      throw new IllegalArgumentException("No recipe named \"" + name + "\"");
    }
  }

  /**
   * Gets the corresponding TodoList.
   *
   * @param name the name of the TodoList
   * @return the corresponding TodoList
   */
  @GetMapping(path = "/{name}")
  public Recipe getRecipe(@PathVariable("name") String name) {
    Recipe res = getRecipe();
    // checkTodoList(todoList, name);
    return res;
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
      if (res.getName() == recipe.getName()) {
        added = false;

      }
      return added;

    }
    // autoSaveTodoModel();
    return added;
  }

  /**
   * Renames the Recipe.
   *
   * @param name    the name of the Recipe
   * @param newName the new name
   */
  @PostMapping(path = "/{name}/rename")
  public boolean renameTodoList(@PathVariable("name") String name, @RequestParam("newName") String newName) {
    for (Recipe res : getCookbook().getRecipes()) {
      if (res.getName() == newName) {
        throw new IllegalArgumentException("A Recipe named \"" + newName + "");
      }

      Recipe newRes = getRecipe();
      newRes.setName(newName);
      // autoSaveTodoModel();
      return true;

    }

  }

  /**
   * Removes the TodoList.
   *
   * @param name the name of the TodoList
   */
  @DeleteMapping(path = "/{name}")
  public boolean removeTodoList(@PathVariable("name") String name) {
    AbstractTodoList todoList = getTodoModel().getTodoList(name);
    checkTodoList(todoList, name);
    getTodoModel().removeTodoList(todoList);
    autoSaveTodoModel();
    return true;
  }

}