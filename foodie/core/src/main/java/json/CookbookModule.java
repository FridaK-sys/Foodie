package json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import core.Cookbook;
import core.Ingredient;
import core.Recipe;

/**
 * A Jackson module for configuring JSON serialization of TodoModel instances.
 */
@SuppressWarnings("serial")
public class CookbookModule extends SimpleModule {

  private static final String NAME = "CookbookModule";

  /**
   * Initializes this TodoModule with appropriate serializers and deserializers.
   */
  public CookbookModule() {
    super(NAME, Version.unknownVersion());
    addSerializer(Cookbook.class, new CookbookWriter());
    addDeserializer(Cookbook.class, new CookbookReader());
    addSerializer(Recipe.class, new RecipeWriter());
    addDeserializer(Recipe.class, new RecipeReader());
    addSerializer(Ingredient.class, new IngredientWriter());
    addDeserializer(Ingredient.class, new IngredientReader());
  }
}
