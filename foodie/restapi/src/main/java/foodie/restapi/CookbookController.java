package foodie.restapi;

import core.Cookbook;
import core.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

/**
 * Class responsible for processing incoming RESTAPI-requests (GET,POST,PUT,DELETE).
 */
@RestController
@RequestMapping(CookbookService.COOKBOOK_SERVICE_PATH)
public class CookbookController {

  @Autowired
  private CookbookService cookbookService;

  public CookbookController() throws IllegalStateException, IOException {
    this.cookbookService = new CookbookService();
  }

  @GetMapping
  public Cookbook getCookbook() {
    return cookbookService.getCookbook();
  }

  @PostMapping(path = "/{name}")
  public boolean addRecipe(@PathVariable("name") String name, @RequestBody Recipe recipe) {
    cookbookService.addRecipe(recipe);
    return true;
  }

  @PutMapping(path = "/{name}/edit")
  public boolean editRecipe(@PathVariable("name") String name, @RequestBody Recipe recipe) {
    cookbookService.editRecipe(name, recipe);
    return true;
  }

  @DeleteMapping(path = "/{name}")
  public boolean removeRecipe(@PathVariable("name") String name) {
    cookbookService.removeRecipe(name);
    return true;
  }

}
