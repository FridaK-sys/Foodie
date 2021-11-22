package ui.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.Cookbook;
import core.Recipe;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.List;
import java.net.http.HttpResponse;
import json.CookbookModule;

/**
 * Allows server side persistence. Uses GET, POST, PUT and DELETE methods to modify a recipe.
 */
public class RemoteCookbookAccess implements CookbookAccess {

  private final URI endPoint;
  private final ObjectMapper mapper;
  private Cookbook cookbook;

  /**
   * Constructor for RemoteCookbookAccess initializes endpoint and mapper.
   */

  public RemoteCookbookAccess(URI endPoint) {
    this.endPoint = endPoint;
    this.mapper = new ObjectMapper().registerModule(new CookbookModule());

  }

  /**
   * Gets Cookbook. Sends http get request to remote server.
   *
   * @return the cookbook
   * @throws RuntimeException if IOException or InterruptedException occured
   */
  @Override
  public Cookbook getCookbook() {
    if (cookbook == null) {
      try {
        final HttpRequest req = HttpRequest.newBuilder(endPoint).header("Accept", 
        "application/json").GET().build();
        final HttpResponse<String> res =
            HttpClient.newBuilder().build().send(req, HttpResponse.BodyHandlers.ofString());
        this.cookbook = mapper.readValue(res.body(), Cookbook.class);
      } catch (IOException | InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    return cookbook;
  }

  /**
   * Edit Recipe. Sends http get request to remote server.
   *
   * @param name the name of the recipe will be removed
   * @param recipe an edited version of the recipe that is added
   * @return true if added or false if not added
   * @throws RuntimeException if IOException or InterruptedException occured
   * 
   */

  /**
   * Adds Recipe. Sends http get request to remote server.
   *
   * @param recipe the recipe to add
   * @return true if recipe is added or false if not added
   * @throws RuntimeException if IOException or InterruptedException occured
   */

  @Override
  public boolean addRecipe(Recipe recipe) {
    try {
      String jsonRecipe = mapper.writeValueAsString(recipe);
      final HttpRequest req =
          HttpRequest.newBuilder(URI.create(endPoint + "/" + recipe.getName())).header("Accept", 
          "application/json")
              .header("Content-Type", "application/json").POST(BodyPublishers.ofString(jsonRecipe))
              .build();
      final HttpResponse<String> res = HttpClient.newBuilder().build().send(req, HttpResponse.BodyHandlers.ofString());
      Boolean successfullyAdded = mapper.readValue(res.body(), Boolean.class);
      if (successfullyAdded != null && successfullyAdded) {
        cookbook.addRecipe(recipe);
        return true;
      }
      return false;
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public boolean editRecipe(String name, Recipe recipe) {
    try {
      String jsonRecipe = mapper.writeValueAsString(recipe);
      final HttpRequest req =
          HttpRequest.newBuilder(URI.create(endPoint + "/" + name + "/" + "edit")).header("Accept", "application/json")
              .header("Content-Type", "application/json").PUT(BodyPublishers.ofString(jsonRecipe)).build();
      final HttpResponse<String> res = HttpClient.newBuilder().build().send(req, HttpResponse.BodyHandlers.ofString());
      Boolean successfullyEdit = mapper.readValue(res.body(), Boolean.class);
      if (successfullyEdit != null && successfullyEdit) {
        cookbook.removeRecipe(name);
        cookbook.addRecipe(recipe);
        return true;
      }
      return false;
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }

  }


  /**
   * Deletes recipe. Sends http get request to remote server.
   *
   * @param name the name of the recipe you want to delete
   * @return true if successfully removed or false if not removed
   * 
   * @throws RuntimeException if IOException or InterruptedException occured
   */

  @Override
  public boolean deleteRecipe(String name) {
    try {
      final HttpRequest req = HttpRequest.newBuilder(URI.create(endPoint + "/" + name))
          .header("Accept", "application/json").DELETE().build();
      final HttpResponse<String> res = HttpClient.newBuilder().build().send(req, 
      HttpResponse.BodyHandlers.ofString());
      Boolean successfullyRemoved = mapper.readValue(res.body(), Boolean.class);
      if (successfullyRemoved != null && successfullyRemoved) {
        cookbook.removeRecipe(name);
        return true;
      }
      return false;
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Sets list of recipes
   *
   * @param cookbook the cookbook with recipes
   *
   * @return true if edited
   * 
   * 
   */

  @Override
  public boolean setRecipes(List<Recipe> recipes) {
    // cookbook.setRecipes(recipes);
    // try {
    // persistence.saveCookbook(cookbook);
    // } catch (IllegalStateException e) {
    // e.printStackTrace();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    return true;
  }

}
