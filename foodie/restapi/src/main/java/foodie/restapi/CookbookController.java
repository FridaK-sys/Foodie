package foodie.restapi;

import core.Cookbook;
import core.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CookbookController with a cookbookService
 */
@RestController
@RequestMapping(CookbookService.COOKBOOK_SERVICE_PATH)
public class CookbookController {

  private CookbookService cookbookService;

  /**
   * Constructor for CookbookController
   * 
   * @param cookbookService Initializes cookbookService
   * 
   * 
   */

  @Autowired
  public CookbookController(final CookbookService cookbookService) {
    this.cookbookService = cookbookService;
  }

  /**
   * Gets Cookbook
   *
   * @return the cookbook
   * 
   */

  @GetMapping
  public Cookbook getCookbook() {
    return cookbookService.getCookbook();
  }

  /**
   * Adds recipe
   *
   * @return true if recipe was added
   * 
   */

  @PostMapping(path = "/{name}")
  public boolean addRecipe(@PathVariable("name") String name, @RequestBody Recipe recipe) {
    cookbookService.addRecipe(recipe);
    return true;
  }

  /**
   * Edits recipe
   *
   * @return true if recipe was edited
   * 
   */

  @PutMapping(path = "/{name}/edit")
  public boolean editRecipe(@PathVariable("name") String name, @RequestBody Recipe recipe) {
    cookbookService.editRecipe(name, recipe);
    return true;
  }

  /**
   * Deletes recipe
   *
   * @return true if recipe was deleted
   * 
   */

  @DeleteMapping(path = "/{name}")
  public boolean removeRecipe(@PathVariable("name") String name) {
    cookbookService.removeRecipe(name);
    return true;
  }

}
