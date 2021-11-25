package foodie.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import foodie.core.Cookbook;
import foodie.core.Ingredient;
import foodie.core.Recipe;

/**
 * A Jackson module for configuring JSON serialization of Cookbook instances.
 */
public class CookbookModule extends SimpleModule {

  private static final String NAME = "CookbookModule";

  /**
   * Initializes this CookbookModule with the serializers and deserializers.
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
