package ui.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import core.Cookbook;
import core.Recipe;
import json.CookbookModule;

public class RemoteCookbookAccess implements CookbookInterface {

  private final URI endPoint;
  private final ObjectMapper mapper;
  private Cookbook cookbook;

  public RemoteCookbookAccess(URI endPoint) {
    this.endPoint = endPoint;
    this.mapper = new ObjectMapper().registerModule(new CookbookModule());

  }

  /**
   * Gets Cookbook. Sends http get request to remote server
   *
   * @return the cookbook
   */
  @Override
  public Cookbook getCookbook() {
    if (cookbook == null) {
      try {
        final HttpRequest req = HttpRequest.newBuilder(endPoint).header("Accept", "application/json").GET().build();
        final HttpResponse<String> res = HttpClient.newBuilder().build().send(req,
            HttpResponse.BodyHandlers.ofString());
        this.cookbook = mapper.readValue(res.body(), Cookbook.class);
      } catch (IOException | InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    return cookbook;
  }

  /**
   * Edits recipe. Sends http get request to remote server
   *
   * @return true if edited
   */

  @Override
  public boolean editRecipe(String name, Recipe recipe) {
    try {
      String jsonVisit = mapper.writeValueAsString(recipe);
      final HttpRequest req = HttpRequest.newBuilder(URI.create(endPoint + "/" + "name" + "/" + "edit"))
          .header("Accept", "application/json").header("Content-Type", "application/json")
          .PUT(BodyPublishers.ofString(jsonVisit)).build();
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
   * Adds Cookbook. Sends http get request to remote server
   *
   * @return true if added
   */

  @Override
  public boolean addRecipe(Recipe recipe) {
    try {
      String jsonVisit = mapper.writeValueAsString(recipe);
      final HttpRequest req = HttpRequest.newBuilder(URI.create(endPoint + "/" + recipe.getName()))
          .header("Accept", "application/json").header("Content-Type", "application/json")
          .POST(BodyPublishers.ofString(jsonVisit)).build();
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

  /**
   * Deletes recipe. Sends http get request to remote server
   *
   * @return the cookbook
   */

  @Override
  public boolean deleteRecipe(String name) {
    try {
      final HttpRequest req = HttpRequest.newBuilder(URI.create(endPoint + "/" + name))
          .header("Accept", "application/json").DELETE().build();
      final HttpResponse<String> res = HttpClient.newBuilder().build().send(req, HttpResponse.BodyHandlers.ofString());
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

}
