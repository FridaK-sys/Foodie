package foodie.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import core.Recipe;
import core.Cookbook;

@RestController
@RequestMapping(CookbookService.COOKBOOK_SERVICE_PATH)
public class CookbookController {

  @Autowired
  private CookbookService cookbookService;

  @GetMapping
  public Cookbook getCookbook() {
    return cookbookService.getCookbook();
  }

  @PostMapping(path = "/{name}")
  public boolean addRecipe(@PathVariable("name") String name, @RequestBody Recipe recipe) {
    cookbookService.addRecipe(recipe);
    return true;
  }

  /*
   * @PostMapping(path = "/{name}/rename") public boolean
   * renameRecipe(@PathVariable("name") String name, @RequestParam("newName")
   * String newName) { cookbookService.renameRecipe(name, newName); return true;
   * 
   * }
   */

  @DeleteMapping(path = "/{name}")
  public boolean removeRecipe(@PathVariable("name") String name) {
    cookbookService.removeRecipe(name);
    return true;
  }

}