package restapi;

public class RecipeNotFoundException extends RuntimeException {

  RecipeNotFoundException(Long id) {
    super("Could not find employee " + id);
  }

}
